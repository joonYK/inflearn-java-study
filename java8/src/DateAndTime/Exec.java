package DateAndTime;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Exec {
    public static void main(String[] args) throws InterruptedException {
        //기존의 Date 및 Calendar 의 문제.

        /*
         * 기존의 사용하던 날짜와 시간을 다루는 Date는 직관적으로 보고 사용하기에 불편했다.
         * 이름부터가 날짜를 다루는 Date인데 시간까지 다루게 되고, 그 시간은 우리가 생각하는 시간이 아닌 1970년 1월 1일부터 현재까지의 시간 간격을
         * 밀리초로 나타낸 것으로 사용상에 실수를 유발할 수 있다.
         */
        Date date = new Date();
        long time = date.getTime();
        System.out.println(date);
        System.out.println(time);

        /*
         * mutable 하기 때문에 Thread safe 하지 못하다.
         */
        Thread.sleep(1000 * 3); //쓰레드 3초를 재움
        Date after3Seconds = new Date();
        System.out.println(after3Seconds); //3초가 지난 시간이 찍힘
        after3Seconds.setTime(time); //쓰레드 3초 재우기 전의 시간으로 time 을 세팅 (mutable)
        System.out.println(after3Seconds);

        /*
         * 사용상의 실수를 유발할 수 있는 요소로 인해 버그가 생길 수 있음.
         * month는 0부터 시작하므로 10을 사용하면 11월로 적용됨.
         * 차라리 enum 타입으로 명확하게 받으면 좋겠지만, 전부 int 형으로 받기 때문에 Type safe 하지 못해서 많은 버그를 양산하게 된다.
         */
        Calendar joonyeopBirthDay = new GregorianCalendar(1991, 10, 14);


        // java8의 Time API
        Instant instant = Instant.now();
        System.out.println(instant);
    }
}
