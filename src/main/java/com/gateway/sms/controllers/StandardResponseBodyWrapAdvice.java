package com.gateway.sms.controllers;

import com.gateway.sms.domain.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;

@ControllerAdvice @Slf4j
@RequiredArgsConstructor
public class StandardResponseBodyWrapAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return (AnnotationUtils.findAnnotation(returnType.getContainingClass(), ResponseBody.class) != null ||
                returnType.getMethodAnnotation(ResponseBody.class) != null);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ApiResponse resp = (ApiResponse) body;
        response.setStatusCode(resp.getStatus());
        resp.setPath(URI.create(request.getURI().getPath()));
        return body;
    }
}