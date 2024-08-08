package com.zyd.blog.business.service;


import com.zyd.blog.business.entity.ArticleLook;
import com.zyd.blog.framework.mysql.DBWrite;

/**
 * 文章浏览记录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://docs.zhyd.me
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@DBWrite
public interface BizArticleLookService {

    ArticleLook insert(ArticleLook articleLook);
}
