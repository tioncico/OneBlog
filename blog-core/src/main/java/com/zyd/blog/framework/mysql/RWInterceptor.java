package com.zyd.blog.framework.mysql;


import com.zyd.blog.framework.holder.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Description
 *
 * @author fxb
 * @date 2018-08-31
 */
@Aspect
@Component
public class RWInterceptor implements Ordered {
    private static final Logger log= LoggerFactory.getLogger(RWInterceptor.class);

    @Around("@annotation(DBRead)")
    public Object setRead(ProceedingJoinPoint joinPoint, DBRead DBRead) throws Throwable{
        try{
            DataSourceContextHolder.setDbType(DataSourceContextHolder.READ);
            return joinPoint.proceed();
        }finally {
            DataSourceContextHolder.clearDbType();
            log.info("清除threadLocal");
        }
    }

    @Around("@annotation(DBWrite)")
    public Object setRead(ProceedingJoinPoint joinPoint, DBWrite DBWrite) throws Throwable{
        try{
            DataSourceContextHolder.setDbType(DataSourceContextHolder.WRITE);
            return joinPoint.proceed();
        }finally {
            DataSourceContextHolder.clearDbType();
            log.info("清除threadLocal");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
