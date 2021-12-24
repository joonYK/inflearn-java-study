package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 쓰레드를 만들고 관리하는 작업을 고수준의 API에게 위임.
 * Executor을 상속받는 ExecutorService interface과 그 구현체들을 사용.
 */
public class ExecutorExec {

    public static void main(String[] args) {
        //쓰레드를 하나만 사용하는 Executor
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //execute, submit로 실행
        //작업을 실행하고 나면 다음 작업이 들어올때까지 대기하기 때문에 프로세스가 죽지 않음.
        //명시적인 shutdown이 필요.
        executorService.submit(getRunnable("Thread"));
        executorService.shutdown();


        //쓰레드풀을 설정해서 생성. (2개 설정)
        ExecutorService executorTwoThreadPool = Executors.newFixedThreadPool(2);

        /*
         * 쓰레드 풀이 2개라 나머지 3개의 작업은 ThreadPoolExecutor 내부의 BlockingQueue에 쌓아놓고 대기시킴
         * 쓰레드의 작업이 끝나면 Queue에 있는 작업에 쓰레드를 할당
         */
        executorTwoThreadPool.submit(getRunnable("threadPool2 - Hello"));
        executorTwoThreadPool.submit(getRunnable("threadPool2 - joonyeop"));
        executorTwoThreadPool.submit(getRunnable("threadPool2 - The"));
        executorTwoThreadPool.submit(getRunnable("threadPool2 - Java"));
        executorTwoThreadPool.submit(getRunnable("threadPool2 - Thread"));

        executorTwoThreadPool.shutdown();

        //주기적으로 또는 특정 시간을 딜레이 시켜서 작업을 시킬때 사용.
        //스케쥴링 executor.
        ScheduledExecutorService scheduledExecutorService1 = Executors.newSingleThreadScheduledExecutor();
        //3초 뒤에 실행
        scheduledExecutorService1.schedule(getRunnable("scheduled - delay 3 seconds"), 3, TimeUnit.SECONDS);
        scheduledExecutorService1.shutdown();


        //1초 뒤에 2초 주기로 실행.
        ScheduledExecutorService scheduledExecutorService2 = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService2.scheduleAtFixedRate(getRunnable("scheduled - schedule period 2 seconds"), 1, 2, TimeUnit.SECONDS);
        //스케쥴링을 위해선 shutdown을 하면 안 됨.
        scheduledExecutorService2.shutdown();
    }

    private static Runnable getRunnable(String message) {
        return () -> {
            System.out.println(message + " " + Thread.currentThread().getName());
        };
    }
}
