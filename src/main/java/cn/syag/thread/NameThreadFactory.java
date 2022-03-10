package cn.syag.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : lwb  2022/3/4
 * @note :
 */
public class NameThreadFactory implements ThreadFactory {
    private ThreadGroup threadGroup;
    private AtomicInteger seq=new AtomicInteger(0);

    public NameThreadFactory(String groupName){
        threadGroup=new ThreadGroup(groupName);
    }
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,threadGroup.getName()+"_"+seq.incrementAndGet());
    }
}
