package com.zyd.blog.framework.config;

import com.zyd.blog.framework.holder.DataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Random;

public class DataSourceRouter extends AbstractRoutingDataSource {


    @Value("${spring.datasource.num}")
    private int num;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getDbType();
        if (typeKey == DataSourceContextHolder.WRITE) {
            System.out.println("使用了写库");
            System.out.println(typeKey);
            log.info("使用了写库");
            return typeKey;
        }
        //随机1-num的随机数
        Random rand = new Random();
        int randomNumber = rand.nextInt(num) + 1;
        //使用随机数决定使用哪个读库
        log.info("使用了读库" + randomNumber);
        System.out.println("使用了读库" + randomNumber);
        return DataSourceContextHolder.READ + randomNumber;
    }
}
