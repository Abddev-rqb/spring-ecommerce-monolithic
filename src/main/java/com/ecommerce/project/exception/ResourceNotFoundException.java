package com.ecommerce.project.exception;

public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    String field;
    Long fieldId;

    public ResourceNotFoundException(String field, String resourceName, String fieldName) {
        super(String.format("%s not found %s: %s",resourceName,fieldName,field));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }

    public ResourceNotFoundException(String field, String resourceName, Long fieldId) {
        super(String.format("%s not found %s: %d",resourceName,field,fieldId));
        this.fieldId = fieldId;
        this.field = field;
        this.resourceName = resourceName;
    }
}
