package owo.pigeon.utils;

public class TimerExample {

    public static long startTime = 0;  // 计时器开始时间
    public static boolean isTimerRunning = false;  // 是否计时
    public static long elapsedTime = 0;  // 记录已经过去的时间（毫秒）

    // 计时器线程，每毫秒增加1
    public static Thread timerThread;

    // 计时器线程的逻辑
    public static void startTimer() {
        if (!isTimerRunning) {
            isTimerRunning = true;
            startTime = System.currentTimeMillis();
            elapsedTime = 0;

            // 创建并启动计时器线程
            timerThread = new Thread(() -> {
                while (isTimerRunning) {
                    try {
                        // 每毫秒更新一次时间
                        Thread.sleep(1);
                        elapsedTime = System.currentTimeMillis() - startTime;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            timerThread.start();  // 启动线程
        }
    }

    // 停止计时器
    public static void stopTimer() {
        isTimerRunning = false;  // 停止计时
        if (timerThread != null) {
            timerThread.interrupt();  // 中断计时器线程
        }
    }
}
