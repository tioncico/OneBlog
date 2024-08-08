package com.zyd.blog.business.service;


import com.github.pagehelper.PageInfo;
import com.zyd.blog.business.entity.Comment;
import com.zyd.blog.business.vo.CommentConditionVO;
import com.zyd.blog.framework.exception.ZhydCommentException;
import com.zyd.blog.framework.mysql.DBRead;
import com.zyd.blog.framework.mysql.DBWrite;
import com.zyd.blog.framework.object.AbstractService;

import java.util.List;
import java.util.Map;

/**
 * 评论
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://docs.zhyd.me
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@DBWrite
public interface BizCommentService extends AbstractService<Comment, Long> {
    @DBRead
    PageInfo<Comment> findPageBreakByCondition(CommentConditionVO vo);

    @DBRead
    Map<String, Object> list(CommentConditionVO vo);

    /**
     * admin发表评论
     *
     * @param comment
     * @return
     */
    void commentForAdmin(Comment comment) throws ZhydCommentException;

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    @DBWrite
    Comment comment(Comment comment) throws ZhydCommentException;

    /**
     * 查询近期评论
     *
     * @param pageSize
     * @return
     */
    @DBRead
    List<Comment> listRecentComment(int pageSize);

    /**
     * 查询未审核的评论
     *
     * @param pageSize
     * @return
     */
    @DBRead
    List<Comment> listVerifying(int pageSize);

    /**
     * 点赞
     *
     * @param id
     */
    @DBWrite
    void doSupport(Long id);

    /**
     * 点踩
     *
     * @param id
     */
    @DBWrite
    void doOppose(Long id);
}
