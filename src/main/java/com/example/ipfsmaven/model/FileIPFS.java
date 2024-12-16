package com.example.ipfsmaven.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table
public class FileIPFS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String cidv0;
    private String cidv1;
    private String nameFile;
    private Long fileSize;
    private Date unixTime;
    private boolean status;
}
