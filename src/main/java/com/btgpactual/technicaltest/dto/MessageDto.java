package com.btgpactual.technicaltest.dto;

import lombok.Data;

@Data
public class MessageDto {

    private String msg;

    public MessageDto(String msg) {
        this.msg = msg;
    };
  
};