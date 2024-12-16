package com.example.ipfsmaven.service.impl;

import com.example.ipfsmaven.exception.PagginationException;
import com.example.ipfsmaven.model.HistoryIPFS;
import com.example.ipfsmaven.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public List<HistoryIPFS> getAllHistory(Integer offset, Integer count){
        var listHistory = historyRepository.findAll();
        if(offset<listHistory.size()) {
            var offsetListNodes = listHistory.subList(offset, listHistory.size());
            if(count>=offsetListNodes.size()){
                return offsetListNodes;
            }else return offsetListNodes.subList(0, count);
        }else throw new PagginationException();
    }
}
