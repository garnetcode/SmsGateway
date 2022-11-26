package com.gateway.sms.controllers;
import com.gateway.sms.domain.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/sms")
@AllArgsConstructor
public class SmsController {
    private final Response response;

    @GetMapping
    @RequestMapping(path = "/send")
    public @ResponseBody Response send(){
        response.setSuccess(true);
        response.setMessage("Successful!!");
        response.setData("Sent");
        return response;
    }
}
