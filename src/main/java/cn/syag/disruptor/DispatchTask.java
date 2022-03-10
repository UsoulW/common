package cn.syag.disruptor;

/**
 * @Author : lwb  2022/2/25
 * @note :
 */
public abstract class DispatchTask {
    public abstract int getDispatchType();
    public abstract void run();
}
