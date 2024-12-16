package com.example.ipfsmaven.repository;

import com.example.ipfsmaven.model.HistoryIPFS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryIPFS, Long> {

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "SELECT file_package FROM historyipfs WHERE unix_time =(SELECT MAX(unix_time) FROM historyipfs)")
    Optional<Long> findLastPackage();
}
