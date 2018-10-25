package com.secondKill.service;

import com.secondKill.commom.OrderResponse;
import com.secondKill.commom.Response;
import com.secondKill.pojo.Seckill;

public interface ISecondKillService {
    /**
     * 商品加入秒杀
     * @param seckill
     * @return
     */
    Boolean addToSeckill(Seckill seckill);

    /**
     * 秒杀操作
     * @param productId
     * @return
     */
    OrderResponse secondKill(Long productId, Integer userId);
}
