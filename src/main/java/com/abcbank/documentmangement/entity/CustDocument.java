package com.abcbank.documentmangement.entity;

import jakarta.persistence.*;

@Entity
public class CustDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private byte[] content;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String title) { this.name = title; }
    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }
}
