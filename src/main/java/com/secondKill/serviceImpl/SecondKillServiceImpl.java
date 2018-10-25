package com.secondKill.serviceImpl;

import com.secondKill.aopInterceptor.OrderGenerated;
import com.secondKill.commom.OrderResponse;
import com.secondKill.commom.Response;
import com.secondKill.dao.ProductMapper;
import com.secondKill.dao.SeckillMapper;
import com.secondKill.ormex.dao.SeckillUserDefinedMapper;
import com.secondKill.pojo.Product;
import com.secondKill.pojo.Seckill;
import com.secondKill.service.ISecondKillService;
import com.secondKill.util.JsonUtil;
import com.secondKill.util.RedisPoolUtil;
import com.secondKill.util.ThreadPoolUtil;
import com.secondKill.vo.SeckillInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.concurrent.atomic.AtomicInteger;

@Service("iSecondKillService")
public class SecondKillServiceImpl implements ISecondKillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SeckillUserDefinedMapper seckillUserDefinedMapper;

    @Autowired
    private ProductMapper productMapper;

    private final static ThreadPoolUtil.ThreadPoolProxy threadPoolProxy = ThreadPoolUtil.getThreadPoolProxy();
    /**
     * 管理员将商品加入秒杀清单
     * @param seckill
     * @return
     */
    @Override
    public Boolean addToSeckill(Seckill seckill) {
        Integer resultValue = seckillMapper.insert(seckill);
        if (resultValue <= 0){
            return false;
        }
        HandlerMapping
        return true;
    }

    /**
     * 秒杀功能
     * @param productId
     * @return
     */
    @OrderGenerated
    public OrderResponse secondKill(final Long productId,Integer userId) {
        /**
         * 查看redis中是否有数据
         * 无数据则查询数据库，获取相关数据并进行时间校验
         * 双检锁锁住Redis工具类 保证对象数据的创建过程不被重复
         */
        String id = String.valueOf(productId);
        String  object = RedisPoolUtil.get(id);
        if (object != null){
            SeckillInformationVo seck = JsonUtil.string2Obj(object,SeckillInformationVo.class);
            if (seck.getInventory() >= 0) {
                //时间为毫秒级
                String  result = RedisPoolUtil.setexObject(productId, seck,
                        seck.getEndTime().getTime() - System.currentTimeMillis());
                if (result.equals("-1")){
                    return new OrderResponse(new Response().failure("商品已售罄，秒杀结束"),userId,productId);
                }
                //开线程后台执行数据库操作
                threadPoolProxy.execute(new Runnable() {
                    @Override
                    public void run() {
                        String id = String.valueOf(productId);
                        String  object = RedisPoolUtil.get(id);
                        Product product = new Product(productId,JsonUtil.string2Obj(object,SeckillInformationVo.class).getInventory());
                        productMapper.updateByPrimaryKeySelective(product);
                    }
                });
                return new OrderResponse(new Response().success(),userId,productId);
            }
        }
        //查询时间库存及当前任务
        SeckillInformationVo seckill = seckillUserDefinedMapper.selectByProductId(productId);
        //获取当前时间
        Long timeBegin = seckill.getBeginTime().getTime();
        Long timeEnd = seckill.getEndTime().getTime();
        Long nowTime = System.currentTimeMillis();
        if (nowTime < timeBegin){
            return new OrderResponse(new Response().failure("秒杀还未开始"),userId,productId);
        }

        if (nowTime > timeEnd){
            return new OrderResponse(new Response().failure("秒杀已结束"),userId,productId);
        }
        try {
            //双检锁
            if (RedisPoolUtil.get(id) == null) {
                synchronized (RedisPoolUtil.class) {
                    if (RedisPoolUtil.get(id) == null) {
                        String  result = RedisPoolUtil.setexObject(productId, seckill,
                                timeEnd-System.currentTimeMillis());
                        if (result.equals("-1")){
                            return new OrderResponse(new Response().failure("商品已售罄，秒杀结束"),userId,productId);
                        }
                        final Integer inv = seckill.getInventory();
                        //开线程后台执行数据库操作
                        threadPoolProxy.execute(new Runnable() {
                            @Override
                            public void run() {
                                AtomicInteger integer = new AtomicInteger(inv);
                                integer.decrementAndGet();
                                Product product = new Product(productId,integer.intValue());
                                productMapper.updateByPrimaryKeySelective(product);
                            }
                        });
                        return new OrderResponse(new Response().success(),userId,productId);
                    }
                }
            }
        }catch (Exception e){
            return new OrderResponse(new Response().failure("抢购失败"),userId,productId);
        }

        String v1 = RedisPoolUtil.get(id);
        SeckillInformationVo seckillInformationVo = JsonUtil.string2Obj(v1,SeckillInformationVo.class);
        final Integer inv = seckillInformationVo.getInventory();
        String result = RedisPoolUtil.setexObject(productId, seckillInformationVo,
                timeEnd-System.currentTimeMillis());
        if (result.equals("-1")){
            return new OrderResponse(new Response().failure("商品已售罄，秒杀结束"),userId,productId);
        }
        //开线程后台执行数据库操作
        threadPoolProxy.execute(new Runnable() {
            @Override
            public void run() {
                AtomicInteger integer = new AtomicInteger(inv);
                integer.decrementAndGet();
                Product product = new Product(productId,integer.intValue());
                productMapper.updateByPrimaryKeySelective(product);
            }
        });
        return new OrderResponse(new Response().success(),userId,productId);
    }
}
