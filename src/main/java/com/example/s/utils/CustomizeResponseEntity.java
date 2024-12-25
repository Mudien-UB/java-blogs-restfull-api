package com.example.s.utils;



import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomizeResponseEntity {


    public static <T> ResponseEntity<CommonResponse<T>> buildResponse(HttpStatus status, String message, T data) {

        CommonResponse<T> commonResponse = CommonResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(status).body(commonResponse);
    }
}
