package net.smooth.zhenxiactivity.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String msg;
    private SubError sub_error;
}
