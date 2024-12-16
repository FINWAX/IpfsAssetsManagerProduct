package com.example.ipfsmaven.repository;

import com.example.ipfsmaven.model.FileIPFS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileIPFS, Long> {


    FileIPFS findFileIPFSByCidv0(String cid0);

    boolean existsByCidv0(String cid);
}
