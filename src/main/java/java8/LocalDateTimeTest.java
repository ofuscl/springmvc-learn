package java8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;

/**
 * 1、获取任意格式的日期、时间、年月日时分秒
 * 2、加减年月日时分秒得到新日期
 * 3、修改日期的一部分如只修改年份
 *
 * Created by YScredit on 2018/11/20.
 */
public class LocalDateTimeTest {

    public static void main(String[] args) {
        // 获取当前的日期时间
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        System.out.println("当前时间: " + currentTime);
        System.out.println("当前日期: " + currentTime.toLocalDate());
        System.out.println("具体时间： 【月: " + currentTime.getMonthValue() +", 日: " + currentTime.getDayOfMonth() +", 秒: " + currentTime.getSecond()+"】");
        System.out.println("获取本月第二天："+currentTime.withDayOfMonth(2));
        System.out.println("获取本月最后一天："+currentTime.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("日期类型转换："+currentTime.format(DateTimeFormatter.ISO_DATE));
        System.out.println("日期类型转换："+currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        System.out.println(currentDate.toEpochDay()+":"+currentDate.plusDays(5).toEpochDay());

        betweenMonthsAndYear();
        betweenDays();
        betweenDays2();//推荐
    }

    /**
     * 相差月份和年份
     * 注：Period 年月日是分别计算差值的，所以不适合跨月计算天数
     * @return
     */
    private static void betweenMonthsAndYear(){
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(LocalDate.of(2018,10,11),currentDate);
        System.out.println("相差天数： "+period.getDays());
        System.out.println("相差月份： "+period.getMonths());
    }

    /**
     * 相差天数
     * 注：toEpochDay 最适合计算天数，不用考虑跨月跨年等问题，但不支持月份计算
     * @return
     */
    private static void betweenDays(){
        LocalDate currentDate = LocalDate.now();
        System.out.println("相差天数： "+ String.valueOf(LocalDate.of(2018,10,11).toEpochDay()-currentDate.toEpochDay()));
    }

    /**
     * 相差天数  ---推荐
     * 注：toEpochDay 最适合计算天数，不用考虑跨月跨年等问题，但不支持月份计算
     * @return
     */
    private static void betweenDays2(){
        LocalDate currentDate = LocalDate.now();
        System.out.println("相差天数： "+ ChronoUnit.DAYS.between(LocalDate.of(2018,10,11),currentDate));
        System.out.println("相差周数： "+ ChronoUnit.WEEKS.between(LocalDate.of(2018,10,11),currentDate));
        System.out.println("相差月数： "+ ChronoUnit.MONTHS.between(LocalDate.of(2018,10,11),currentDate));
    }
}
