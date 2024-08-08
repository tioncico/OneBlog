package com.zyd.blog.framework.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.zyd.blog.framework.holder.DataSourceContextHolder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @author fxb
 * @date 2018-09-03
 */
@Configuration
@MapperScan(basePackages = "com.zyd.blog.persistence.mapper", sqlSessionFactoryRef= "sqlSessionFactory")
public class DatabaseConfig {
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Value("${mybatis.config-location}")
    private String configLocation;


    /**
     * 写数据源
     *
     * @Primary 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。
     * 多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource writeDataSource() {
        return new DruidDataSource();
    }

    /**
     * 读数据源
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slaver")
    public DataSource read() {
        return new DruidDataSource();
    }


    /**
     * 多数据源需要自己设置sqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(routingDataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 实体类对应的位置
        bean.setTypeAliasesPackage(typeAliasesPackage);
        // mybatis的XML的配置
        bean.setMapperLocations(resolver.getResources(mapperLocation));
        bean.setConfigLocation(resolver.getResource(configLocation));
        return bean.getObject();
    }

    /**
     * 设置事务，事务需要知道当前使用的是哪个数据源才能进行事务处理
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(routingDataSource());
    }

    /**
     * 设置数据源路由，通过该类中的determineCurrentLookupKey决定使用哪个数据源
     */
    @Bean
    public AbstractRoutingDataSource routingDataSource() {
        DataSourceRouter proxy = new DataSourceRouter();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceContextHolder.WRITE, writeDataSource());
        targetDataSources.put(DataSourceContextHolder.READ, read());
        proxy.setDefaultTargetDataSource(writeDataSource());
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }


}
