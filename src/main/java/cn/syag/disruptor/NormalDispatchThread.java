package cn.syag.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;

/**
 * @Author : lwb  2022/3/4
 * @note :
 */
@Slf4j
public class NormalDispatchThread implements DispatchThread{
    private Disruptor<DispatchEvent> disruptor;
    private static final int ringBufferSize=1024;
    private RingBuffer ringBuffer;

    private NormalDispatchThread(ThreadFactory factory,WaitStrategy waitStrategy){
        disruptor=new Disruptor<>(DispatchEvent::new,ringBufferSize,factory, ProducerType.MULTI,waitStrategy);
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            try {
                event.run();
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        });
        disruptor.start();
        ringBuffer=disruptor.getRingBuffer();
    }

    public static NormalDispatchThread instance(ThreadFactory factory){
        return new NormalDispatchThread(factory,new BlockingWaitStrategy());
    }

    @Override
    public void submit(DispatchTask dispatchTask) {
        try{
            long next = ringBuffer.next();
            DispatchEvent event = disruptor.get(next);
            event.setTask(dispatchTask);
            ringBuffer.publish(next);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
