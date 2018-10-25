package com.secondKill.service;

import com.secondKill.pojo.Product;
import com.secondKill.pojo.Seckill;
import com.secondKill.vo.ProductInformationVo;

import java.util.List;
import java.util.concurrent.locks.Lock;

public interface IProductService {
    List<Seckill> queryProductList();
    Boolean newProduct(Product product);
    Boolean deleteProduct(Long productId);
}
