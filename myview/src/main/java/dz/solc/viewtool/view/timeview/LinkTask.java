package dz.solc.viewtool.view.timeview;

import android.os.Handler;
import android.os.Message;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2018/7/8
 * create_time: 10:14
 * describe 创建一个任务队列，定时从队列中取出任务,不建议使用在过长的时间定时中
 **/

@SuppressWarnings("all")
public class LinkTask<E> {

    /**
     * 构建一个默认的任务列表
     */
    private Queue<E> queue = new LinkedList<>();

    /**
     * 定时任务
     */
    private TaskHandler taskHandler;

    /**
     * 任务是否在执行的标识
     */
    private boolean taskRun = false;

    /**
     * 队列长度，实例化类的时候指定
     */
    private int queueSize;

    /**
     * 当前任务下标
     */
    private int currentTask = 0;

    public LinkTask() {

    }

    public LinkTask(int queueSize) {
        this.queueSize = queueSize;
    }

    /**
     * 使用外部传入的任务列表
     *
     * @param queue
     */
    public void setQueue(Queue<E> queue) {
        this.queue = queue;
    }

    /**
     * 开启任务
     */
    public void startTask() {
        start(0);
    }

    public void startTask(long delayMillis) {
        start(delayMillis);
    }

    private void start(long delayMillis) {
        if (null == taskHandler) {
            taskHandler = new TaskHandler();
        }
        setTaskRun(true);
        currentTask = 0;
        taskHandler.setDelayMillis(delayMillis);
        taskHandler.sendEmptyMessageDelayed(1, delayMillis);
    }

    public void setCurrentTask(int index) {
        this.currentTask = index;
    }

    public int getCrrentTask() {
        return currentTask;
    }

    public void setTaskRun(boolean taskRun) {
        this.taskRun = taskRun;
    }

    public boolean getTaskRun() {
        return taskRun;
    }

    public class TaskHandler extends Handler {

        private long delayMillis = 0;

        private LinkedList<E> wrapTask = new LinkedList<>();

        public TaskHandler() {
            wrapTask.clear();
            wrapTask.addAll(queue);
        }

        private void setDelayMillis(long delayMillis) {
            this.delayMillis = delayMillis;
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (taskRun && msg.what == 1) {
                if (queue.size() > 0 && currentTask < queue.size()) {
                    if (null != actionTaskListener) {
                        actionTaskListener.callBackTask(wrapTask.get(currentTask));
                    }
                    this.sendEmptyMessageDelayed(1, delayMillis);
                    currentTask++;
                } else {
                    if (null != actionTaskListener) {
                        actionTaskListener.endTask();
                    }
                    setTaskRun(false);
                }
            }
        }
    }

    /**
     * 任务的监听器
     */
    private ActionTaskListener actionTaskListener;

    public void setActionTaskListener(ActionTaskListener actionTaskListener) {
        this.actionTaskListener = actionTaskListener;
    }

    public interface ActionTaskListener<E> {

        void callBackTask(E task);

        void endTask();
    }

    /**
     * 入队
     */
    public boolean offer(E e) {
        if (queue.size() >= queueSize) {
            //如果超出长度,入队时,先出队
            queue.poll();
        }
        return queue.offer(e);
    }

    /**
     * 出队
     */
    public E poll() {
        return queue.poll();
    }

    /**
     * 获取队列
     */
    public Queue<E> getQueue() {
        return queue;
    }

    /**
     * 获取限制大小
     */
    public int getQueueSize() {
        return queueSize;
    }

    public boolean add(E e) {
        return queue.add(e);
    }

    public E element() {
        return queue.element();
    }

    public E peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.size() == 0 ? true : false;
    }

    public int size() {
        return queue.size();
    }

    public E remove() {
        return queue.remove();
    }

    public boolean addAll(Collection<? extends E> c) {
        return queue.addAll(c);
    }

    public void clear() {
        queue.clear();
    }

    public boolean contains(Object o) {
        return queue.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    public Iterator<E> iterator() {
        return queue.iterator();
    }

    public boolean remove(Object o) {
        return queue.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    public Object[] toArray() {
        return queue.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

}
