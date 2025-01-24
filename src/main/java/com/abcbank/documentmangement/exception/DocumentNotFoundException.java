package com.abcbank.documentmangement.exception;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(Long id) {

        super(String.format("Document with Id %d not found", id));
    }
}
