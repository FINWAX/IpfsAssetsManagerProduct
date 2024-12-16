package com.example.ipfsmaven.repository;

import com.example.ipfsmaven.model.ListNodesIPFS;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NodesRepository extends JpaRepository<ListNodesIPFS, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ListNodesIPFS p WHERE p.status = :status")
    void deleteListNodesIPFSByStatus(boolean status);

    boolean existsByMultiaddr(String string);

}
