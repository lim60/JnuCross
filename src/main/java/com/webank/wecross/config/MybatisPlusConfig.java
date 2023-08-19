package com.webank.wecross.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.moandjiezana.toml.Toml;
import com.webank.wecross.exception.WeCrossException;
import com.webank.wecross.utils.ConfigUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan("com.webank.wecross.mapper")
public class MybatisPlusConfig {

    @Resource
    Toml toml;

    @Bean
    public DataSource newDataSource() throws Exception {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        // 配置数据源相关信息，如数据库连接URL、用户名、密码等
//        dataSource.setUrl(ConfigUtils.parseString(toml,"db.url"));
//        dataSource.setUsername(ConfigUtils.parseString(toml,"db.username"));
//        dataSource.setPassword(ConfigUtils.parseString(toml,"db.password"));
//        dataSource.setDriverClassName(ConfigUtils.parseString(toml,"db.driver"));
//        return dataSource;
        Map<String, String> map = new HashMap();
        map.put("url", ConfigUtils.parseString(toml,"db.url"));
        map.put("username", ConfigUtils.parseString(toml,"db.username"));
        map.put("password", ConfigUtils.parseString(toml,"db.password"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(map);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory newSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(newDataSource());
        // 配置其他MyBatis Plus相关的配置，如插件、类型别名等
        return sessionFactory.getObject();
    }
    @Bean
    public SqlSessionTemplate newSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(newSqlSessionFactory());
    }

}
