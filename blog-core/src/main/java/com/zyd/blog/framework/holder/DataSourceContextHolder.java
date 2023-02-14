package com.zyd.blog.framework.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 利用ThreadLocal封装的保存数据源上线的上下文context
 */
public class DataSourceContextHolder {
    private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    public static final String WRITE = "master";
    public static final String READ = "salver";

    private static ThreadLocal<String> contextHolder= new ThreadLocal<>();

    public static void setDbType(String dbType) {
        if (dbType == null) {
            log.error("dbType为空");
            throw new NullPointerException();
        }
        log.info("设置dbType为：{}",dbType);
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get() == null ? WRITE : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
