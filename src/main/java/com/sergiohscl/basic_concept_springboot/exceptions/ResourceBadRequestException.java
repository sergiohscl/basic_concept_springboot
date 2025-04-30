package com.sergiohscl.basic_concept_springboot.exceptions;

public class ResourceBadRequestException extends RuntimeException {

    public ResourceBadRequestException(String mensagem) {
        super(mensagem);
    }
}
