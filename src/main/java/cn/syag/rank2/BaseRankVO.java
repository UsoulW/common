package cn.syag.rank2;

/**
 * @Author : lwb  2022/2/24
 * @note :
 */
public abstract class BaseRankVO<K,V> {
    protected K key;
    protected long updateTime=System.currentTimeMillis();
    protected V pre;
    protected V next;
    protected int rank;

    protected abstract void copyValue(V newVal);

    protected abstract boolean compareIsMax(V compareVal);

    public int getRank(){
        return rank;
    }
}
