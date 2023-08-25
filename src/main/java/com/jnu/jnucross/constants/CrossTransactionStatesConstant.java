package com.jnu.jnucross.constants;

//事务状态：0，执行中；1，已完成；2，已中止；3，已回滚；
public interface CrossTransactionStatesConstant {

    Integer EXECUTING  = 0;
    Integer DONE = 1;
    Integer STOP = 2;
    Integer ROLL_BACK = 3;

}
