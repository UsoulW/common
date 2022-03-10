package cn.syag.rank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @Author : lwb  2022/2/18
 * @note : 榜单
 */
public class ConcurrentRankCache<K,V extends AbstractRank<K>> {

    private ConcurrentHashMap<K,V> rankMap = new ConcurrentHashMap<>();
    private K headKey;
    private K tailKey;

    private AtomicInteger lastRank=new AtomicInteger(Integer.MAX_VALUE);

    protected ReentrantLock lock = new ReentrantLock(false);

    public static ConcurrentRankCache init(int maxRank){
        ConcurrentRankCache cache=new ConcurrentRankCache();
        return cache;
    }

    public void addSort(V rank){
        try {
            lock.lock();
            //空榜：直接入榜
            if (headKey==null){
                headKey= rank.key;
                tailKey= rank.key;
                rank.rank=1;
                rankMap.put(rank.key, rank);
                lastRank.decrementAndGet();
                return;
            }
            //不是空榜
            K key = rank.getKey();
            Optional<V> oldOp = findByKey(key);
            if (oldOp.isPresent()){
                //存在
                V old = oldOp.get();
                if (old.getSortTime()> rank.getSortTime()){
                    return;
                }
                old.update(rank);
                rank=old;
            }else {
                V v = rankMap.get(tailKey);
                v.next=rank;
                rank.pre=v;
                rank.rank=v.rank+1;
                lastRank.decrementAndGet();
                rankMap.put(rank.key, rank);
                tailKey=rank.key;
            }
            //更新名次
            if (rank.riseRank()){
                //上升调整
                if (tailKey== rank.key && rank.pre!=null){
                    tailKey= (K) rank.pre.key;
                }
                AbstractRank pre=null;
                while ((pre=rank.pre)!=null && rank.compareMax(pre)){
                    int temp = rank.rank;
                    rank.rank= pre.rank;
                    pre.rank=temp;

                    rank.pre=pre.pre;
                    if (pre.pre!=null){
                        pre.pre.next=rank;
                    }

                    pre.pre=rank;
                    pre.next=rank.next;

                    rank.next=pre;
                    if (pre.next!=null)
                        pre.next.pre=pre;
                }
                if (rank.rank==1){
                    headKey= rank.key;
                }
            }else {
                //下降调整
                AbstractRank next=null;
                if (headKey== rank.key && rank.next!=null ){
                    headKey= (K) rank.next.key;
                }
                while ((next=rank.next)!=null && next.compareMax(rank)){
                    int temp = rank.rank;
                    rank.rank= next.rank;
                    next.rank=temp;

                    rank.next=next.next;
                    next.next.pre=rank;

                    rank.pre.next=next;
                    next.pre=rank.pre;

                    rank.pre=next;
                    next.next=rank;
                }
                if (rank.next==null){
                    tailKey= rank.key;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public Optional<V> findByKey(K key){
        V v = rankMap.get(key);
        return Optional.ofNullable(v);
    }

    public Optional<V> findByRank(int rank){
        return rankMap.values().stream().filter(p->p.getRank()==rank).findAny();
    }

    public List<V> findList(int startRank,int endRank){
        V v = rankMap.get(startRank);
        List<V> list=new ArrayList<>();
        if (v!=null){
            list.add(v);
            while (v.hasNext() && v.next.getRank()<=endRank){
                list.add((V) v.next);
            }
        }
        return list;
    }

    public List<V> findAll(){
        return rankMap.values().stream().sorted(Comparator.comparing(V::getRank)).collect(Collectors.toList());
    }

    public void remove(K key){
        try {
            lock.lock();
            Optional<V> optional = findByKey(key);
            if (!optional.isPresent()){
                return;
            }
            V v = optional.get();
            if (v.getRank()==1) {
                headKey=null;
            }
            if (v.hasPre()){
                v.pre.next=v.next;
            }
            if (v.hasNext()){
                headKey= (K) v.next.getKey();
                AbstractRank next = null;
                while ((next=v.next)!=null){
                    next.rank--;
                }
            }else {
                tailKey=v.hasPre()? (K) v.pre.key :null;
            }
            rankMap.remove(key);
            lastRank.incrementAndGet();
        }finally {
            lock.unlock();
        }
    }
}
