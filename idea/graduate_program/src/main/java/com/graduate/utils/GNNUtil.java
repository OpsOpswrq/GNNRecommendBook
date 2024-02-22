package com.graduate.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.graduate.bean.Cnt;
import com.graduate.bean.Recommend;
import com.graduate.service.HistoryService;
import com.graduate.service.RecommendService;
import com.graduate.service.UserService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 生成推荐序列  大数据计算
 * 定时生成推荐序列 每日一算
 */
@Component
public class GNNUtil {
    public void GNN1(UserService userService,HistoryService historyService,RecommendService recommendService){
        int[] s1 = new int[]{1,2,3,4,5,6};
        Cnt[] newS1 = new Cnt[6];
        int[] s2 = new int[5];
        int[] userIds = userService.CollectId();
        for(int i = 0;i < userIds.length;i++ ){
            for(int j = 0;j < s1.length;j++){
                JSONObject entries = historyService.CountByBClass1(s1[j], userIds[i]);
                int cnt = Integer.parseInt(entries.get("cnt").toString());
                Cnt cntOb = new Cnt();
                cntOb.setCnt(cnt);
                cntOb.setPrimaryKey(s1[j]);
                newS1[j] = cntOb;
            }
            int[] cnts1 = sortCnt(newS1); // 获取第一类排序
            for(int j = 0;j < s1.length;j++){
                s2 = GNN2(historyService,s1[j], userIds[i]);
                Recommend recommend = new Recommend();
                recommend.setUserId(userIds[i]);
                recommend.setDate(DateUtil.date());
                recommend.setBclass1(s1[j]);
                recommend.setSequence1(ArrayUtils.toString(cnts1, ","));
                recommend.setSequence2(ArrayUtils.toString(s2, ","));  // 插入类型二
                recommendService.InsertRecommend(recommend);
            }
        }
    }

    /**
     * 生成序列二
     * @param BClass1
     * @param userId
     * @return
     */
    public int[] GNN2(HistoryService historyService,int BClass1,int userId){
        int[] s2 = new int[]{1,2,3,4,5};
        Cnt[] newS2 = new Cnt[5];
        for(int j = 0;j < s2.length;j++){
            JSONObject entries = historyService.CountByBClass1ANDBClass2(BClass1, s2[j], userId);
            int cnt = Integer.parseInt(entries.get("cnt").toString());
            Cnt cntOb = new Cnt();
            cntOb.setPrimaryKey(s2[j]);
            cntOb.setCnt(cnt);
            newS2[j] = cntOb;
        }
        return sortCnt(newS2);
    }

    /**
     * 排序数组
     * @param cnts
     * @return
     */
    public int[] sortCnt(Cnt[] cnts){
        Arrays.sort(cnts, new Comparator<Cnt>() {
            @Override
            public int compare(Cnt o1, Cnt o2) {
                return o2.getCnt() - o1.getCnt();
            }
        });
        int[] s1 = new int[cnts.length];
        for(int i = 0;i < cnts.length;i++){
            s1[i] = cnts[i].getPrimaryKey();
        }
        return s1;
    }
}
