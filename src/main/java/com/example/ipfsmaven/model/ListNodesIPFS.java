package com.example.ipfsmaven.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class ListNodesIPFS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Column(name = "address", nullable = false)
    String multiaddr;
    @Column(name="status", nullable = false)
    boolean status;
}
