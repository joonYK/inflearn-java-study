package DateAndTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
//        Thread.sleep(1000); //쓰레드 3초를 재움
//        Date after3Seconds = new Date();
//        System.out.println(after3Seconds); //3초가 지난 시간이 찍힘
//        after3Seconds.setTime(time); //쓰레드 3초 재우기 전의 시간으로 time 을 세팅 (mutable)
//        System.out.println(after3Seconds);

        /*
         * 사용상의 실수를 유발할 수 있는 요소로 인해 버그가 생길 수 있음.
         * month는 0부터 시작하므로 10을 사용하면 11월로 적용됨.
         * 차라리 enum 타입으로 명확하게 받으면 좋겠지만, 전부 int 형으로 받기 때문에 Type safe 하지 못해서 많은 버그를 양산하게 된다.
         */
        Calendar joonyeopBirthDay = new GregorianCalendar(1991, 10, 14);

        System.out.println();

        // java8의 Time API

        //지금 이 순간을 기계 시간(1970년 1월 1일 이후부터 지금까지의 시간에 대한 밀리세컨드)으로 구함
        Instant instant = Instant.now(); //기준시 UTC (GMT 기반으로 영국 그리니치 시 시간 기준)
        System.out.println(instant);
        System.out.println(instant.atZone(ZoneId.of("UTC")));

        //시간을 시스템 기준 시점으로 구함. (서울 기준)
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);

        //사람이 알아볼 용도로 사용 (사람을 위한 시간)
        LocalDateTime now = LocalDateTime.now(); //현재 시스템 zone 정보 참고해서 local 시간 참고
        System.out.println(now);

        LocalDateTime birthDay = LocalDateTime.of(1991, Month.OCTOBER, 14, 0, 0, 0);
        System.out.println(birthDay);
        ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul")); //보고 싶은 zone의 시간 구함
        System.out.println(nowInKorea);

        //Instant와 ZonedDateTime, LocalDateTime 스위칭 가능
        Instant nowInstant = Instant.now();
        ZonedDateTime zonedDateTime1 = nowInstant.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println(zonedDateTime1);
        Instant instant1 = zonedDateTime1.toInstant();
        System.out.println(instant1);

        System.out.println();

        //날짜 기간 (사람용)
        LocalDate today = LocalDate.now();
        LocalDate christmas = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 25);
        Period period = Period.between(today, christmas);
        System.out.println(period.getDays());

        Period until = today.until(christmas);
        System.out.println(until.get(ChronoUnit.DAYS));

        System.out.println();

        //날짜 시간 (기계용)
        Instant now1 = Instant.now();
        Instant plus = now1.plus(10, ChronoUnit.SECONDS);
        Duration duration = Duration.between(now1, plus);
        System.out.println(duration.getSeconds());

        //포매팅
        LocalDateTime now2 = LocalDateTime.now();
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(now2.format(MMddyyyy));

        //파싱
        LocalDate parse = LocalDate.parse("10/14/1991", MMddyyyy);
        System.out.println(parse);

        //레거시 지원
        Date date1 = new Date();
        Instant instant2 = date.toInstant();
        Date newDate = Date.from(instant2);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ZonedDateTime zonedDateTime2 = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime2.toLocalDateTime();
        GregorianCalendar from = GregorianCalendar.from(zonedDateTime2);
    }
}
