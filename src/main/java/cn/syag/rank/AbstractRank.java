package cn.syag.rank;

/**
 * @Author : lwb  2022/2/18
 * @note :
 */
public abstract class AbstractRank<K> {
    /**
     * 排名的key
     */
    protected K key;
    /**
     * 名次
     */
    protected int rank;
    /**
     * 创建时间
     */
    protected long sortTime=System.currentTimeMillis();
    /**
     * 前一名
     */
    protected AbstractRank pre;
    /**
     * 后一名
     */
    protected AbstractRank next;

    public K getKey() {
        return key;
    }

    public long getSortTime() {
        return sortTime;
    }

    public int getRank() {
        return rank;
    }

    protected boolean hasNext() {
        return next!=null;
    }

    protected boolean hasPre() {
        return pre!=null;
    }

    protected abstract boolean compareMax(AbstractRank rank);

    protected abstract void update(AbstractRank rank);

    protected boolean riseRank(){
        return pre!=null && compareMax(pre);
    }
}
