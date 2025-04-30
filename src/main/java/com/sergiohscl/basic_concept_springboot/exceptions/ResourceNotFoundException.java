package com.sergiohscl.basic_concept_springboot.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }

}
