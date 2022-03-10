package cn.syag.rank2;

/**
 * @Author : lwb  2022/2/24
 * @note :
 */
public class MyRank extends BaseRankVO<Integer,MyRank>{

    private int val;

    public static MyRank valOf(int key,int v){
        MyRank myRank=new MyRank();
        myRank.key=key;
        myRank.val=v;
        return myRank;
    }

    @Override
    protected void copyValue(MyRank newVal) {
        val=newVal.val;
    }

    @Override
    protected boolean compareIsMax(MyRank compareVal) {
        return val>compareVal.val;
    }

    public int getVal() {
        return val;
    }
}
