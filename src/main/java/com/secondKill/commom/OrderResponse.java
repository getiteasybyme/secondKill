package com.secondKill.commom;

import org.springframework.stereotype.Component;

public class OrderResponse {
    private Response response;
    private Integer userId;
    private Long productId;

    public OrderResponse(){}

    public OrderResponse(Response response, Integer userId, Long productId) {
        this.response = response;
        this.userId = userId;
        this.productId = productId;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
