package com.secondKill.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long orderId;

    private Integer userId;

    private Long productId;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    public Order(Integer userId, Long productId, BigDecimal price) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
    }

    public Order(Long orderId, Integer userId, Long productId, BigDecimal price, Date createTime, Date updateTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Order() {
        super();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}