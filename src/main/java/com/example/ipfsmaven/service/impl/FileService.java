package com.example.ipfsmaven.service.impl;

import com.example.ipfsmaven.config.Config;
import com.example.ipfsmaven.dto.CIDResponseDTO;
import com.example.ipfsmaven.dto.PackageCountDto;
import com.example.ipfsmaven.model.FileIPFS;
import com.example.ipfsmaven.model.HistoryIPFS;
import com.example.ipfsmaven.repository.FileRepository;
import com.example.ipfsmaven.repository.HistoryRepository;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.cid.Cid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository repository;
    private final PackageCountDto packageCountDto;
    private final HistoryRepository historyRepository;
    private final Config config;

    public CIDResponseDTO getCidAndSaveFile(MultipartFile file) throws IOException {
        Date dateUploadFile = new Date();
        FileIPFS fileIPFS = new FileIPFS();
        HistoryIPFS historyIPFS = new HistoryIPFS();
        IPFS ipfs = new IPFS(config.getEnvLocalIpfsNode());
        NamedStreamable.ByteArrayWrapper wrapFile = new NamedStreamable
                .ByteArrayWrapper(Optional.ofNullable(file.getOriginalFilename()), file.getBytes());
        MerkleNode addResult = ipfs.add(wrapFile).get(0);
        ipfs.pin.add(addResult.hash);

        fileIPFS.setCidv0(addResult.hash.toBase58());
        fileIPFS.setCidv1(new Cid(1L, Cid.Codec.DagProtobuf, addResult.hash.getType(), addResult.hash.getHash()).toString());
        fileIPFS.setNameFile(file.getOriginalFilename());
        fileIPFS.setFileSize(file.getSize());
        fileIPFS.setUnixTime(dateUploadFile);
        fileIPFS.setStatus(false);
        if(!repository.existsByCidv0(addResult.hash.toBase58())) {
            repository.save(fileIPFS);
        }
        historyIPFS.setFilePackage(packageCountDto.getCountPackage());
        historyIPFS.setFileId(repository.findFileIPFSByCidv0(addResult.hash.toBase58()).getId());
        historyIPFS.setUnixTime(dateUploadFile);
        historyRepository.save(historyIPFS);

        return new CIDResponseDTO(fileIPFS.getCidv0(), fileIPFS.getCidv1());
    }


    public void setPackageCount() {
        final Optional<Long> countPackage = historyRepository.findLastPackage();
        if(countPackage.isEmpty()){
            packageCountDto.setCountPackage(1L);
        }else packageCountDto.setCountPackage(countPackage.get()+1);
    }



}
