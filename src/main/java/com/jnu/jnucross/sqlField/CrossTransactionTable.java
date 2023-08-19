package com.jnu.jnucross.sqlField;

/**
 * 事务表字段
 */
public interface CrossTransactionTable {

    String ID = "id";
    String TYPE = "type";
    String CHAINS = "chains";
    String STATE = "state";
    String INITIATE_ACCOUNT = "initiate_account";
    String PARTICIPATE_ACCOUNTS = "participate_accounts";
    String SMART_CONTRACTS = "smart_contracts";
    String ADDRESSING_STRATEGY = "addressing_strategy";
    String CREATOR_ID = "creator_id";
    String END_TIME = "end_time";
    String CREATE_TIME = "create_time";
    String UPDATE_TIME = "update_time";

    /**
     * CREATE TABLE `cross_transaction` (
     *   `id` bigint(20) NOT NULL COMMENT '事务id',
     *   `type` tinyint(4) NOT NULL COMMENT '事务类型：0，跨链转账；1，合约调用；2，原子交换；',
     *   `chains` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '涉及区块链ids：{0,1,...}',
     *   `state` tinyint(4) DEFAULT NULL COMMENT '事务状态：0，执行中；1，已完成；2，已中止；3，已回滚；',
     *   `initiate_account` int(11) DEFAULT NULL COMMENT '发起账户',
     *   `participate_accounts` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '参与账户ids：{0,1,...}',
     *   `smart_contracts` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '所使用的合约ids：{0,1,...}',
     *   `addressing_strategy` int(11) DEFAULT NULL COMMENT '寻址策略：0，默认策略；',
     *   `creator_id` int(11) DEFAULT NULL COMMENT '创建用户id',
     *   `end_time` datetime DEFAULT NULL COMMENT '结束时间',
     *   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     *   `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     *   PRIMARY KEY (`id`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='跨链事务表'
     */
}
