package com.graduate.service.Impl;

import cn.hutool.json.JSONObject;
import com.graduate.bean.Comment;
import com.graduate.bean.CommonResult;
import com.graduate.mapper.CollectionMapper;
import com.graduate.mapper.CommentMapper;
import com.graduate.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CollectionMapper collectionMapper;

    /**
     * 未登录时获取评论
     * @param bookId
     * @return
     */
    @Override
    public CommonResult CollectCommentByBookId(int bookId,int currentPage) {
        CommonResult result = new CommonResult();
        int begin = (currentPage - 1) * 3;
        List<JSONObject> jsonObjects = commentMapper.CollectCommentByBookId(bookId,begin);
        int cnt = commentMapper.CountBookComment(bookId);
        Float avg = commentMapper.AvgBookRate(bookId);
        JSONObject data = new JSONObject();
        if(avg == null){
            data.set("avg",5);
        }else{
            data.set("avg",avg + 5);
        }
        data.set("total",cnt); // 所有评论数
        data.set("comment",jsonObjects); // 所有评论细节
        result.setCode(200);
        result.setMsg("200");
        result.setData(data);
        return result;
    }

    @Override
    public CommonResult CollectCommentByBookIdANDUserId(int userId, int bookId,int currentPage) {
        CommonResult result = new CommonResult();
        int begin = (currentPage - 1) * 3;
        List<JSONObject> jsonObjects = commentMapper.CollectCommentByBookId(bookId,begin);
        int cnt = commentMapper.CountBookComment(bookId);
        Float avg = commentMapper.AvgBookRate(bookId);
        JSONObject collection = collectionMapper.SearchCollectionByUserIdANDBookId(userId, bookId);
        JSONObject data = new JSONObject();
        if(avg == null){
            data.set("avg",5);
        }else{
            data.set("avg",avg + 5);
        }
        data.set("total",cnt);
        data.set("comment",jsonObjects);
        data.set("collection",collection);
        result.setCode(200);
        result.setMsg("200");
        result.setData(data);
        return result;
    }

    @Override
    public CommonResult InsertComment(Comment comment) {
        CommonResult result = new CommonResult();
        commentMapper.InsertComment(comment);
        result.setCode(200);
        result.setMsg("200");
        return result;
    }

    @Override
    public CommonResult DeleteComment(int commentId) {
        CommonResult result = new CommonResult();
        commentMapper.DeleteComment(commentId);
        result.setMsg("200");
        result.setCode(200);
        return result;
    }

    @Override
    public CommonResult LoadCommentByUserId(int userId, int begin, int size) {
        CommonResult result = new CommonResult();
        int cnt = commentMapper.CountCommentByUserId(userId);
        List<JSONObject> jsonObjects = commentMapper.SearchCommentByUserId(userId, begin, size);
        JSONObject entries = new JSONObject();
        entries.set("total",cnt);
        entries.set("comments",jsonObjects);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }

    @Override
    public CommonResult SearchCommentForF3005(String commentDetail, int currentPage, int pageSize) {
        CommonResult result = new CommonResult();
        commentDetail = "%" + commentDetail + "%";
        int begin = (currentPage - 1) * pageSize;
        List<JSONObject> jsonObjects = commentMapper.SearchCommentForF3005(commentDetail, begin, pageSize);
        int cnt = commentMapper.CountCommentForF3005(commentDetail);
        JSONObject entries = new JSONObject();
        entries.set("total",cnt);
        entries.set("comments",jsonObjects);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }
}
