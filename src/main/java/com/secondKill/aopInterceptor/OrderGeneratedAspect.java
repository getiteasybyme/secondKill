package com.secondKill.aopInterceptor;

import com.secondKill.commom.OrderResponse;
import com.secondKill.dao.OrderMapper;
import com.secondKill.pojo.Order;
import com.secondKill.util.ThreadPoolUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Aspect
@Component
public class OrderGeneratedAspect {

    private final static ThreadPoolUtil.ThreadPoolProxy threadPoolProxy = ThreadPoolUtil.getThreadPoolProxy();

    @Autowired
    private OrderMapper orderMapper;

    @AfterReturning(pointcut = "@annotation(com.secondKill.aopInterceptor.OrderGenerated)", returning = "rvt")
    public void execute(JoinPoint joinPoint, Object rvt) throws Throwable {
        final OrderResponse orderResponse = (OrderResponse) rvt;
        System.out.println(orderResponse.getResponse().getMeta().isSuccess());
        if (orderResponse.getResponse().getMeta().isSuccess() == true) {
            threadPoolProxy.execute(new Runnable() {
                @Override
                public void run() {
                    Order order = new Order(orderResponse.getUserId(), orderResponse.getProductId(), new BigDecimal("1"));
                    orderMapper.insert(order);
                }
            });
        }
    }
}
