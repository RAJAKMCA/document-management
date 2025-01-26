package com.abcbank.documentmangement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class CustDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private byte[] content;
    private String userId;
    private String postId;
    private LocalDateTime createdDateTime;
    private Long size;
    private String contentType;
    private String description;

}
