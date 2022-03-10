package cn.syag.rank2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @Author : lwb  2022/2/24
 * @note :
 */
public class MapRank<K,V extends BaseRankVO<K,V>> {
    private Map<Integer,V> rankDataMap=new ConcurrentHashMap<>();
    private Map<K,Integer> keyRankMap=new ConcurrentHashMap<>();
    private int maxLimit;
    protected ReentrantLock lock = new ReentrantLock(false);
    public MapRank(int maxLimit){
        this.maxLimit=maxLimit;
    }
    /**
     * 入榜
     * @param v
     */
    public void addToRank(V v){
        try {
            lock.lock();


        }finally {
            lock.unlock();
        }
        K key = v.key;
        Integer rank = keyRankMap.getOrDefault(key, keyRankMap.size() + 1);
        V rankVO = rankDataMap.get(rank);
        if (rankVO==null){
            v.rank=rank;
            v.pre=rankDataMap.get(rank-1);
            if (v.pre!=null){
                v.pre.next=v;
            }
            rankDataMap.put(rank,v);
            keyRankMap.put(v.key,rank);
            rankVO=v;
        }else {
            if (rankVO.updateTime>v.updateTime){
                return;
            }
            rankVO.copyValue(v);
            rankVO.updateTime=System.currentTimeMillis();
        }
        if (rankVO.pre!=null && rankVO.compareIsMax(rankVO.pre)){
            //名次上调
            V pre=null;
            while ((pre=rankVO.pre)!=null){
                if (!rankVO.compareIsMax(pre)){
                    break;
                }
                pre.next=rankVO.next;
                if (pre.next!=null){
                    pre.next.pre=pre;
                }
                rankVO.pre=pre.pre;
                if (rankVO.pre!=null){
                    rankVO.next=rankVO;
                }
                rankVO.next=pre;
                pre.pre=rankVO;
                int temp=rankVO.rank;
                rankVO.rank=pre.rank;
                pre.rank=temp;
                keyRankMap.put(rankVO.key, rankVO.rank);
                keyRankMap.put(pre.key, pre.rank);
                rankDataMap.put(rankVO.rank,rankVO);
                rankDataMap.put(pre.rank,pre);
            }
        }else {
            //名次下调
            V next=null;
            while ((next=rankVO.next)!=null){
                if (next.compareIsMax(rankVO)){
                    break;
                }
                next.pre=rankVO.pre;
                if (next.pre!=null){
                    next.pre.next=next;
                }

                rankVO.next=next.next;
                if (rankVO.next!=null){
                    rankVO.next.pre=rankVO;
                }
                rankVO.pre=next;
                next.next=rankVO;

                int temp=rankVO.rank;
                rankVO.rank=next.rank;
                next.rank=temp;
                keyRankMap.put(rankVO.key, rankVO.rank);
                keyRankMap.put(next.key, next.rank);
                rankDataMap.put(rankVO.rank,rankVO);
                rankDataMap.put(next.rank,next);
            }
        }
        int overflow=keyRankMap.size()-maxLimit;
        if (overflow>0){
            for (int i = 0; i < overflow; i++) {
                V over = rankDataMap.get((maxLimit + i));
                if (over!=null){
                    rankDataMap.remove(over);
                    keyRankMap.remove(over.key);
                    if (over.pre!=null){
                        over.pre.next=null;
                    }
                }
            }
        }
    }

    public List<V> findAll(){
        return rankDataMap.values().stream().sorted(Comparator.comparing(V::getRank)).collect(Collectors.toList());
    }
}
