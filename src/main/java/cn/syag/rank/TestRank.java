package cn.syag.rank;

/**
 * @Author : lwb  2022/2/21
 * @note :
 */
public class TestRank extends AbstractRank<Long>{
    private int val;

    public static TestRank valueOf(int val,long key){
        TestRank testRank=new TestRank();
        testRank.key=key;
        testRank.val=val;
        return testRank;
    }
    @Override
    protected boolean compareMax(AbstractRank rank) {
        TestRank rank1 =(TestRank)rank;
        if (rank1.val==val){
            if (rank1.sortTime>sortTime){
                return true;
            }
            return key>rank1.key;
        }
        return rank1.val<val;
    }

    @Override
    protected void update(AbstractRank rank) {
        TestRank rank1 =(TestRank)rank;
        this.val=rank1.val;
    }

    @Override
    public String toString() {
        return "rank="+rank+" key="+key+"_"+val;
    }
}
