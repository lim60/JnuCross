package com.webank.wecross.network.rpc.handler;

import com.jnu.jnucross.util.ResultUtil;
import com.webank.wecross.account.UserContext;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Objects;

/**
 * GET /test/getMyUser
 */
@Data
public class MyUserURIHandler implements URIHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyUserURIHandler.class);

    private TransactionTemplate transactionTemplate;

    @Override
    public void handle(
            UserContext userContext, String uri, String method, String content, Callback callback) {

        try {
            transactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    try {
                        // 在这里进行需要事务管理的操作
//                        List<Student> students = studentMapper.selectList(null);
//                        int a = 10/0;
//                    User czg = userTableJPA.findByUsername("czg");
//                        callback.onResponse(ResultUtil.success(students));
                        // 如果出现异常，事务将会回滚
                        // 如果没有异常，事务将会提交
                        callback.onResponse(ResultUtil.success("测试成功"));
                    } catch (Exception e) {
                        logger.error("事务出现异常：",e);
                        status.setRollbackOnly();
                        callback.onResponse("事务出现异常");
                        throw e;
                    }
                    return null;
                }
            });
        }catch (Exception e){
            logger.error("外层方法出现错误：",e);
            callback.onResponse("外层方法出现错误");
        }

    }
}
