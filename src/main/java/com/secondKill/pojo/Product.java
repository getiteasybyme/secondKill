package com.secondKill.pojo;

import java.math.BigDecimal;

public class Product {
    private Long productId;

    private String productName;

    private BigDecimal price;

    private Integer inventory;

    public Product(Long productId,Integer inventory){
        this.productId = productId;
        this.inventory = inventory;
    }

    public Product(Long productId, String productName, BigDecimal price, Integer inventory) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.inventory = inventory;
    }

    public Product() {
        super();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
}