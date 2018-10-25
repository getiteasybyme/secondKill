package com.secondKill.ormex.dao;

import com.secondKill.vo.OrderInformationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderUserDefinedMapper {
    List<OrderInformationVo> selectVoInfo(@Param("userId") Integer userId);
}
