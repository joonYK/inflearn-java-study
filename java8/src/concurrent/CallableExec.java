package concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Runnable과는 다르게 결과를 return 가능.
 */
public class CallableExec {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(2000L);
            return "Hello";
        };

        //Callable이 return하는 타입의 Future를 받을 수 있음.
        Future<String> helloFuture1 = executorService.submit(hello);

        //아래의 submit.get()을 만나기 전까지 쭉 실행.
        System.out.println("helloFuture1 Started!");

        //작업이 완료되고 결과값을 가져오기 전까지 기다림. blocking call
        helloFuture1.get();

        System.out.println("helloFuture1 End!!");



        System.out.println();

        //작업이 완료 되었는지 상태값 체크
        Future<String> helloFuture2 = executorService.submit(hello);
        System.out.println(helloFuture2.isDone());
        System.out.println("helloFuture2 Started!");

        helloFuture2.get();

        System.out.println(helloFuture2.isDone());
        System.out.println("helloFuture2 End!!");



        System.out.println();

        //진행중인 작업을 취소
        Future<String> helloFuture3 = executorService.submit(hello);
        System.out.println("helloFuture3 Started!");

        //true는 작업을 interrupt를 하면서 종료하고 false는 기다림.
        //취소시에는 get을 해서 가져올 수 없음.
        helloFuture3.cancel(false);

        //true가 출력되지만 get으로 값을 가져올 수 없음. cancel로 작업이 종료되었다는 뜻의 true임.
        System.out.println(helloFuture3.isDone());
        System.out.println("helloFuture3 End!!");



        System.out.println();

        Callable<String> java = () -> {
            Thread.sleep(3000L);
            return "Java";
        };

        Callable<String> joonyeop = () -> {
            Thread.sleep(1000L);
            return "Joonyeop";
        };

        /* 여러개의 callable을 한번에 보냄.
         * 모든 작업이 다 끝날때까지 기다림.
         */
        ExecutorService executorThreadPool = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = executorThreadPool.invokeAll(Arrays.asList(hello, java, joonyeop));
        for (Future<String> future : futures) {
            System.out.println("invokeAll - " + future.get());
        }

        /*
         * 여러 작업중에 제일 먼저 끝나는 작업의 결과 반환.
         * ex) 3개의 서버에 같은 파일이 존재. 3개의 서버에 요청을 보내서 제일 먼저 도착하는 서버의 파일을 반환.
         * 만약 싱글쓰레드이거나 pool이 2개인 경우 제일 시간이 짧은 joonyeop은 할당받지 못해서 hello가 반환됨.
         */
        String s = executorThreadPool.invokeAny(Arrays.asList(hello, java, joonyeop));
        System.out.println("invokeAny - " + s);

        executorService.shutdown();
        executorThreadPool.shutdown();
    }
}
