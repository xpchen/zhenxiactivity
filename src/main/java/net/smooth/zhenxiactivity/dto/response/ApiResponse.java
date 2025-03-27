package net.smooth.zhenxiactivity.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String context_id;
    private boolean success;
    private T data;
    private ErrorResponse error;

    public ApiResponse(T data)
    {
        this.data = data;
        this.success=true;
    }
    public ApiResponse(ErrorResponse error)
    {
        this.error =error;
        this.success=false;
    }
    public ApiResponse()
    {

    }
}
