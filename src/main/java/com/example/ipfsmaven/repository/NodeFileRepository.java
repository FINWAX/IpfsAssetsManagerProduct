package com.example.ipfsmaven.repository;

import com.example.ipfsmaven.model.NodeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFileRepository extends JpaRepository<NodeFile, Long> {
}
