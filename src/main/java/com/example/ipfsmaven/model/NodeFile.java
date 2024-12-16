package com.example.ipfsmaven.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table
public class NodeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "node_id", nullable = false)
    private ListNodesIPFS node;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileIPFS file;

    @Column(name="update_at")
    private Date update;
}
