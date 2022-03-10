package cn.syag.rank2;

/**
 * @Author : lwb  2022/2/24
 * @note :
 */
public class Test {
    public static void main(String[] args) {
        MapRank<Integer,MyRank> rank=new MapRank<>(100);
        rank.addToRank(MyRank.valOf(1001,11));
        rank.addToRank(MyRank.valOf(1005,13));

        rank.addToRank(MyRank.valOf(1002,10));

        rank.addToRank(MyRank.valOf(1003,14));
        rank.addToRank(MyRank.valOf(1004,12));


        for (MyRank myRank : rank.findAll()) {
            System.err.println(myRank.rank+"_"+myRank.getVal());
        }
    }
}
