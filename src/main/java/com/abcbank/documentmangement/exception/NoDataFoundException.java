package com.abcbank.documentmangement.exception;

public class NoDataFoundException extends RuntimeException{
    public NoDataFoundException() {
        super("No Documents or Data found");
    }
}
