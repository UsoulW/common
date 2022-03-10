package cn.syag.disruptor;

/**
 * @Author : lwb  2022/3/4
 * @note :
 */
public class DispatchEvent {
    private DispatchTask task;

    public void setTask(DispatchTask task) {
        this.task = task;
    }

    public void run(){
        task.run();
    }
}
