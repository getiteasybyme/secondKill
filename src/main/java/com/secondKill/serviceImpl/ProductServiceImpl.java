package com.secondKill.serviceImpl;

import com.secondKill.dao.ProductMapper;
import com.secondKill.dao.SeckillMapper;
import com.secondKill.ormex.dao.ProductUserDefinedMapper;
import com.secondKill.pojo.Product;
import com.secondKill.pojo.ProductExample;
import com.secondKill.pojo.Seckill;
import com.secondKill.pojo.SeckillExample;
import com.secondKill.service.IProductService;
import com.secondKill.vo.ProductInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private ProductUserDefinedMapper productUserDefinedMapper;

    @Override
    public List<Seckill> queryProductList() {
        return productUserDefinedMapper.selectVoList();
    }

    @Override
    public Boolean newProduct(Product product) {
        Integer resultValue = productMapper.insert(product);
        if (resultValue <= 0){
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteProduct(Long productId) {
        try {
            Integer resultValue = productMapper.deleteByPrimaryKey(productId);
            SeckillExample seckillExample = new SeckillExample();
            SeckillExample.Criteria criteria = seckillExample.createCriteria();
            criteria.andProductIdEqualTo(productId);
            Integer result = seckillMapper.deleteByExample(seckillExample);
            if (resultValue <= 0 || result == 0){
                return false;
            }

            return true;
        }catch (Exception e){
            return false;
        }
    }


}
