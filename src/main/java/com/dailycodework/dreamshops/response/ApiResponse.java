package com.dailycodework.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse extends Throwable {
    private  String message;
    private  Object data;
}
