package com.springboot.exception;

import lombok.Data;

@Data
public class ResourceErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
