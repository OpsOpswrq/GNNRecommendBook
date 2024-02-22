package com.graduate.service;

import com.graduate.bean.Comment;
import com.graduate.bean.CommonResult;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommonResult CollectCommentByBookId(int bookId,int currentPage);
    CommonResult CollectCommentByBookIdANDUserId(int userId,int bookId,int currentPage);
    CommonResult InsertComment(Comment comment);
    CommonResult DeleteComment(int commentId);
    CommonResult LoadCommentByUserId(int userId,int begin,int size);
    CommonResult SearchCommentForF3005(String commentDetail,int currentPage,int pageSize);
}
