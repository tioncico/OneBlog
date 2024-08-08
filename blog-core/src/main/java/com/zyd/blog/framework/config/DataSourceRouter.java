package com.zyd.blog.framework.config;

import com.zyd.blog.framework.holder.DataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource {


    @Value("${spring.datasource.num}")
    private int num;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getDbType();
        if (typeKey == DataSourceContextHolder.WRITE) {
//            System.out.println("使用了写库");
            log.info("使用了写库");
            return typeKey;
        }
        //使用随机数决定使用哪个读库
        log.info("使用了读库");
//        System.out.println("使用了读库");
        return DataSourceContextHolder.READ;
    }
}
