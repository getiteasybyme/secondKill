package com.secondKill.service;

import com.secondKill.pojo.Order;
import com.secondKill.vo.OrderInformationVo;

import java.util.List;

public interface IOrderService {
    List<OrderInformationVo> getOrder(Integer userId);
}
