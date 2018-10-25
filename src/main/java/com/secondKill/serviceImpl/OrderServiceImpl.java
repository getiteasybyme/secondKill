package com.secondKill.serviceImpl;

import com.secondKill.dao.OrderMapper;
import com.secondKill.dao.ProductMapper;
import com.secondKill.ormex.dao.OrderUserDefinedMapper;
import com.secondKill.service.IOrderService;
import com.secondKill.vo.OrderInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IOrderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderUserDefinedMapper orderUserDefinedMapper;

    /**
     * 查询该用户的所有订单
     * @param userId
     * @return
     */
    @Override
    public List<OrderInformationVo> getOrder(Integer userId) {
         return  orderUserDefinedMapper.selectVoInfo(userId);
    }
}
