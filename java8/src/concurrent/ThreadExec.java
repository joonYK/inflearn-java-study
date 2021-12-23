package concurrent;

//자바의 기본적인 멀티쓰레딩 방법
//쓰레드가 수백개인 경우 개발자가 관리 불가.. 그래서 Executor가 나옴
public class ThreadExec {
    public static void main(String[] args) throws InterruptedException {
        //사용방법 1. 상속 (불편함)
        MyThread thread1 = new MyThread();
        thread1.start();

        //사용방법 2. 생성자에 Runnable or Lambda
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread2 : " + Thread.currentThread().getName());
        });
        thread2.start();

        //sleep 을 사용해서 재울 수 있음
        Thread thread3 = new Thread(() -> {
            try {
                System.out.println("Thread3 sleep 1 second : " + Thread.currentThread().getName());
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread3 after sleep: " + Thread.currentThread().getName());
        });
        thread3.start();

        //sleep 된 쓰레드를 interrupt 로 깨움
        Thread thread4 = new Thread(() -> {
            while (true) {
                System.out.println("Thread4 sleep 1 second : " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    //자는 동안에 누가 깨우면 이 곳 실행
                    System.out.println("Thread4 interrupted! " + Thread.currentThread().getName());
                    //return 해야 실질적으로 종료
                    return;
                }
            }
        });
        thread4.start();

        //sleep 가 끝날때까지 join으로 기다려줄 수 있음
        Thread thread5 = new Thread(() -> {
            System.out.println("Thread5 sleep 5 seconds : " + Thread.currentThread().getName());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
        thread5.start();

        //Hello가 먼저 출력될수도 있고 아닐수도 있음. 쓰레드의 동작 순서를 보장 못함
        System.out.println("Hello" + Thread.currentThread().getName());

        //메인 쓰레드 재웠다가 thread4 깨움
        Thread.sleep(3000L);
        thread4.interrupt();

        //메인 쓰레드에서 thread5 쓰레드가 끝날 때까지 기다림
        try {
            thread5.join();
        } catch (InterruptedException e) {
            //join을 해서 다른 쓰레드를 기다리는 도중에 누가 메인 쓰레드를 interrupt 하면 이 곳이 실행됨.
            e.printStackTrace();
        }
        System.out.println(thread5 + " is finished");
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread1 : " + Thread.currentThread().getName());
        }
    }
}
