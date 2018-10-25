package com.secondKill.vo;

import com.secondKill.pojo.Order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInformationVo {

    private Long orderId;

    private Long productId;

    private BigDecimal price;

    private String productName;

    private Date createTime;

    private Date updateTime;

    public OrderInformationVo(){}

    public OrderInformationVo(Order order){
        this.orderId = order.getOrderId();
        this.productId = order.getProductId();
        this.price = order.getPrice();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
