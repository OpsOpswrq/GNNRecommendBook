package com.graduate.utils;

import com.graduate.bean.Book;
import com.graduate.bean.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pearson_Recommend {
    private Map<Double, String> computeNearestNeighbor(String username, List<User> users) {
        Map<Double, String> distances = new TreeMap<>();

        User u1 = new User();
        for (User user:users) {
            if (username.equals(user.getName())) {
                u1 = user;
            }
        }

        for (int i = 0; i < users.size(); i++) {
            User u2 = users.get(i);

            if (!u2.getName().equals(username)) {
                double distance = pearson_dis(new ArrayList<>(), new ArrayList<>());
                distances.put(distance, u2.getName());
            }

        }
        return distances;
    }


    /**
     * 计算2个打分序列间的pearson距离  皮尔逊系数计算
     * 选择公式四进行计算
     * @param rating1
     * @param rating2
     * @return
     */
    private double pearson_dis(List<Book> rating1, List<Book> rating2) {
        int n=rating1.size();
        List<Integer> rating1ScoreCollect = rating1.stream().map(A -> A.getRate()).collect(Collectors.toList());
        List<Integer> rating2ScoreCollect = rating2.stream().map(A -> A.getRate()).collect(Collectors.toList());
        // 流计算
        double Ex= rating1ScoreCollect.stream().mapToDouble(x->x).sum();
        double Ey= rating2ScoreCollect.stream().mapToDouble(y->y).sum();
        double Ex2=rating1ScoreCollect.stream().mapToDouble(x->Math.pow(x,2)).sum();
        double Ey2=rating2ScoreCollect.stream().mapToDouble(y->Math.pow(y,2)).sum();
        double Exy= IntStream.range(0,n).mapToDouble(i->rating1ScoreCollect.get(i)*rating2ScoreCollect.get(i)).sum();
        double numerator=Exy-Ex*Ey/n;
        double denominator=Math.sqrt((Ex2-Math.pow(Ex,2)/n)*(Ey2-Math.pow(Ey,2)/n));
        if (denominator==0) return 0.0;
        return numerator/denominator;
    }
}
