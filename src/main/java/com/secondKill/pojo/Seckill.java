package com.secondKill.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Seckill {
    private Integer seckillId;

    private String productName;

    private Long productId;

    private BigDecimal seckillPrice;

    private Date beginTime;

    private Date endTime;

    public Seckill(Integer seckillId, String productName, Long productId, BigDecimal seckillPrice, Date beginTime, Date endTime) {
        this.seckillId = seckillId;
        this.productName = productName;
        this.productId = productId;
        this.seckillPrice = seckillPrice;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Seckill() {
        super();
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}