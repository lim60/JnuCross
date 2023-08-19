package com.webank.wecross.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webank.wecross.entity.CrossTransaction;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13292
* @description 针对表【cross_transaction(跨链事务表)】的数据库操作Mapper
* @createDate 2023-08-19 21:15:28
* @Entity generator.domain.CrossTransaction
*/
@Mapper
public interface CrossTransactionMapper extends BaseMapper<CrossTransaction> {

}




