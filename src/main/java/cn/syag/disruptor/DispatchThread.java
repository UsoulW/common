package cn.syag.disruptor;

/**
 * @Author : lwb  2022/3/4
 * @note :
 */
public interface DispatchThread {

    void submit(DispatchTask dispatchTask);
}
