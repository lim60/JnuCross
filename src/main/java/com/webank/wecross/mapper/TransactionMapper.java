package com.webank.wecross.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webank.wecross.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13292
* @description 针对表【transaction(交易表)】的数据库操作Mapper
* @createDate 2023-08-19 21:15:28
* @Entity generator.domain.Transaction
*/
@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {

}




