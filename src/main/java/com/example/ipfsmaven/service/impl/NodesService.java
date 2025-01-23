package com.example.ipfsmaven.service.impl;

import com.example.ipfsmaven.config.Config;
import com.example.ipfsmaven.dto.NodeDto;
import com.example.ipfsmaven.exception.NodeUpperException;
import com.example.ipfsmaven.exception.PagginationException;
import com.example.ipfsmaven.model.FileIPFS;
import com.example.ipfsmaven.model.ListNodesIPFS;
import com.example.ipfsmaven.model.NodeFile;
import com.example.ipfsmaven.repository.FileRepository;
import com.example.ipfsmaven.repository.NodeFileRepository;
import com.example.ipfsmaven.repository.NodesRepository;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NodesService {
    private final NodesRepository nodesRepository;
    private final FileRepository fileRepository;
    private final NodeFileRepository nodeFileRepository;
    private final Config config;

    public void addNewNode(NodeDto nodeDto) {

        if (!nodesRepository.existsByMultiaddr(nodeDto.getMultiaddr())) {
            ListNodesIPFS node = new ListNodesIPFS();
            node.setName(nodeDto.getName());
            node.setMultiaddr(nodeDto.getMultiaddr());
            node.setStatus(pingStatusNode(nodeDto.getMultiaddr()));
            nodesRepository.save(node);
        } else throw new NodeUpperException();

    }

    public boolean pingStatusNode(String multiaddr) {
        MultipartAddr listFormat = new MultipartAddr(multiaddr);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Map> future = executor.submit(() -> {
            IPFS ipfs = new IPFS(listFormat.getHost(), listFormat.getPort(), "/api/v0/", listFormat.getProtocol().equals("https"));
            return ipfs.id();
        });

        try {
            var node = future.get(config.getEnvTimeoutRequestFromNode(), TimeUnit.SECONDS);
            return true;
        } catch (TimeoutException e) {
            future.cancel(true);
            return false;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown(); //
        }
        return false;
    }

    public List<ListNodesIPFS> getAllNodes(Integer offset, Integer count) {

        var listNodes = nodesRepository.findAll();
        if (offset <= listNodes.size()) {
            var offsetListNodes = listNodes.subList(offset, listNodes.size());
            if (count >= offsetListNodes.size()) {
                return offsetListNodes;
            } else return offsetListNodes.subList(0, count);
        } else throw new PagginationException();
    }

    public void deleteById(Long id) {
        nodesRepository.deleteById(id);
    }


    public void SchedulerUpdateStatuses() {
        List<ListNodesIPFS> ipfsNodes = nodesRepository.findAll();
        ipfsNodes.stream()
                .peek(nodeDto -> {
                    nodeDto.setStatus(pingStatusNode(nodeDto.getMultiaddr()));
                })
                .forEach(nodesRepository::save
                );
    }


    public void removeUnavailableNodes() {
        nodesRepository.deleteListNodesIPFSByStatus(false);
    }

    public void NodesUpdate() {
        List<FileIPFS> listFiles = fileRepository.findAll().stream().filter(list -> !list.isStatus()).toList();
        for (FileIPFS file : listFiles) {
            if (nodesRepository.findAll().isEmpty()) {
                return;
            }
            file.setStatus(LoadOnNode(file));
            fileRepository.save(file);
        }
    }

    private boolean LoadOnNode(FileIPFS file) {
        List<ListNodesIPFS> path = nodesRepository.findAll().stream().filter(ListNodesIPFS::isStatus).toList();
        for (ListNodesIPFS s : path) {
            try {
                MultipartAddr listFormat = new MultipartAddr(s.getMultiaddr());
                IPFS ipfs = new IPFS(listFormat.getHost(), listFormat.getPort(), "/api/v0/", listFormat.getProtocol().equals("https"));
                config.refresh();
                IPFS ipfs1 = new IPFS(config.getEnvLocalIpfsNode());
                Multihash filePointer = Multihash.fromBase58(file.getCidv0());
                byte[] fileContent = ipfs1.cat(filePointer);
                MerkleNode addResult = ipfs.add(new NamedStreamable.ByteArrayWrapper(file.getNameFile(), fileContent)).get(0);
                ipfs.pin.add(addResult.hash);

                NodeFile nodeFile = new NodeFile();
                nodeFile.setNode(s);
                nodeFile.setFile(file);
                nodeFile.setUpdate(new Date());

                nodeFileRepository.save(nodeFile);

                return true;
            } catch (Exception e) {
                log.info("Ошибка выгрузки файлов на ноды");
                return false;
            }
        }

        return false;
    }


}
