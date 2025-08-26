package owo.pigeon.utils;

public class Timer {
    private long startTime;         // 计时器开始时间
    public volatile boolean isRunning; // 是否在运行
    public long elapsedTime;       // 已经过时间（毫秒）
    private Thread timerThread;     // 独立计时线程

    // 构造函数初始化
    public Timer() {
        this.startTime = 0;
        this.isRunning = false;
        this.elapsedTime = 0;
    }

    // 开始计时
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();
            elapsedTime = 0;

            // 创建并启动计时器线程
            timerThread = new Thread(() -> {
                while (isRunning) {
                    try {
                        Thread.sleep(1); // 每毫秒更新一次
                        elapsedTime = System.currentTimeMillis() - startTime;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // 恢复中断状态
                    }
                }
            });
            timerThread.start(); // 启动线程
        }
    }

    // 停止计时
    public synchronized void stop() {
        if (isRunning) {
            isRunning = false; // 停止计时
            if (timerThread != null) {
                timerThread.interrupt(); // 中断线程
            }
        }
    }

    // 获取已经经过的时间（毫秒）
    public synchronized long getElapsedTime() {
        if (isRunning) {
            return System.currentTimeMillis() - startTime;
        }
        return elapsedTime; // 如果已经停止，返回停止时的时间
    }

    // 重置计时器
    public synchronized void reset() {
        stop();
        startTime = 0;
        elapsedTime = 0;
    }
}
