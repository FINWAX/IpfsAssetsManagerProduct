package com.example.ipfsmaven.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table
public class HistoryIPFS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fileId;
    private Long filePackage;
    private Date unixTime;
}
