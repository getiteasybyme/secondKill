package com.secondKill.ormex.dao;

import com.secondKill.pojo.Seckill;
import com.secondKill.vo.ProductInformationVo;

import java.util.List;

public interface ProductUserDefinedMapper {
    List<Seckill> selectVoList();
}
