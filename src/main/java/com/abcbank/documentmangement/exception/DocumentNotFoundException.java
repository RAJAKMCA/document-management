package com.abcbank.documentmangement.exception;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(Long id) {
        super(String.format("Document not found with id:%d", id));
    }
}
