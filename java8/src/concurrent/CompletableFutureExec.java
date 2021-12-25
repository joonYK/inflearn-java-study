package concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 기존 Future로 하기 힘들었던 작업들
 * 1. 외부에서 완료 시킬 수 없음. (취소하거나 get에 타임타웃 설정 불가)
 * 2. 블로킹 코드(get())를 사용하지 앟으면 작업이 끝났을 때 콜백 처리 실행 불가.
 * 3. 여러 Future 조합 불가. ex) Event 정보 가져온 다음 Event에 참석하는 회원 목록 가져오기
 * 4. 예외 처리용 API 제공 안 함.
 *
 * CompletableFuture는 위 단점들을 보완
 *
 * CompletableFuture는 ForkJoinPool을 사용 (별도로 ThreadPool을 생성하지 않아도 ForkJoinPool에 있는 common Pool을 사용)
 * ForkJoinPool도 Executor의 구현체로 deque를 사용해서 Thread가 작업이 없으면 Thread가 직접 deque에서 작업을 가져와 실행
 * 작업 단위를 자기가 파생시킨 세부적인 서브 작업들을 다른 쓰레드에 분산시켜 처리하고 모아서(join) 결과값을 가져옴
 *
 */
public class CompletableFutureExec {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Executors 를 사용해서 만들 필요 없음. CompletableFuture 만으로 비동기 작업 가능.
        CompletableFuture<String> future1 = new CompletableFuture<>();
        //future 의 기본값을 명시적으로 정해줌.
        future1.complete("future1");
        System.out.println(future1.get());

        //static 메소드로 생성 가능
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("future2");
        System.out.println(future2.get());

        System.out.println();

        //return 이 없는 작업 : runAsync
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() ->
                System.out.println("runAsync : " + Thread.currentThread().getName()));
        //future 만 정의한 것으로 get 이나 join 을 해야 작업이 실행.
        voidCompletableFuture.get();

        System.out.println();

        //return 이 있는 작업 : supplyAsync
        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync : " + Thread.currentThread().getName());
            return "supplyAsync done";
        });
        System.out.println(stringCompletableFuture1.get());

        System.out.println();

        //supplyAsync의 return 값으로 콜백 작업 처리.
        CompletableFuture<String> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync2 : " + Thread.currentThread().getName());
            return "supplyAsync done";
        }).thenApply(s -> {
            System.out.println("supplyAsync2 thenAsync : " + Thread.currentThread().getName());
            return s.toUpperCase();
        });
        // return 타입이 없는경우(Void) thenAccept 사용.
        //.thenAccept(s -> System.out.println(s));
        // return 을 받을 필요조차 없으면 thenRun 사용.
        //.thenRun(() -> System.out.println(s));

        //이전 Future 와의 차이점으로 get 호출 이전에 callback 정의 가능
        System.out.println(stringCompletableFuture2.get());

        System.out.println();

        //ForkJoinPool 을 사용하지 않고 명시적으로 쓰레드 풀 지정 가능.
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture
                .runAsync(() -> System.out.println("explicit Thread Pool runAsync : " + Thread.currentThread().getName()), executorService)
                //callback 작업도 다른 쓰레드 풀에서 실행 가능. (async가 붙은 메소드 사용)
                .thenRunAsync(() -> System.out.println("explicit Thread Pool thenRunAsync  : " + Thread.currentThread().getName()), executorService);

        voidCompletableFuture1.get();
        executorService.shutdown();

        System.out.println();

        // 여러 future를 조합

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello : " + Thread.currentThread().getName());
            return "Hello";
        });
        //thenCompose로 future를 조합
        //아래는 future간의 의존성이 있는 경우 사용법. 앞의 작업이 끝난 이후에 뒤의 작업을 이어서 해야하는 경우.
        CompletableFuture<String> composeFuture = hello.thenCompose(s -> getWorld(s));
        System.out.println(composeFuture.get());

        System.out.println();

        //서로 의존성이 없는 작업들간의 조합
        CompletableFuture<String> hello1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hello : " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("World : " + Thread.currentThread().getName());
            return "World";
        });

        //thenCombie을 사용해서 hello의 결과와 world의 결과를 각각 받아서 처리(BiFunction).
        CompletableFuture<String> combineFuture = hello1.thenCombine(world, (h, w) -> h + " " + w);
        System.out.println(combineFuture.get());

    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("World : " + Thread.currentThread().getName());
            return message + " World";
        });
    }
}
