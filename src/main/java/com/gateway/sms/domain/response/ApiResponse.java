package com.gateway.sms.domain.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDateTime;


@Component @Getter @Setter @ToString
@NoArgsConstructor @EqualsAndHashCode
public class ApiResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message = "Message";
    private Boolean success;

    @NonNull
    private HttpStatus status;
    private Object data;
    private URI path;

    public void isSuccessful(){
        this.setSuccess(true);
        this.setStatus(HttpStatus.OK);
        this.setMessage("Success");
        this.setData(null);
    }

    public void failed(){
        this.setSuccess(false);
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.setMessage("Failed");
        this.setData(null);
    }
}
