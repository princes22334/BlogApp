package com.springboot.exception;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;
    private int status;

}
