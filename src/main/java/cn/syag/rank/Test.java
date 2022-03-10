package cn.syag.rank;

import java.util.List;

/**
 * @Author : lwb  2022/2/18
 * @note :
 */
public class Test {
    public static void main(String[] args) {
        ConcurrentRankCache<Long,TestRank> rankCache=new ConcurrentRankCache<>();
        for (int i = 0; i < 5; i++) {
            rankCache.addSort(TestRank.valueOf((int) (Math.random()*100),10000+i));
        }

        List<TestRank> all = rankCache.findAll();
        for (TestRank rank : all) {
            System.err.println(rank.toString());
        }
    }
}
