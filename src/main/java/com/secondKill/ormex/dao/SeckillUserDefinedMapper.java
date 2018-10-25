package com.secondKill.ormex.dao;

import com.secondKill.pojo.Seckill;
import com.secondKill.vo.SeckillInformationVo;
import org.apache.ibatis.annotations.Param;

public interface SeckillUserDefinedMapper {
    SeckillInformationVo selectByProductId(@Param("productId") Long productId);
}
