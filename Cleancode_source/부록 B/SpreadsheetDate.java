/* ========================================================================
 * JCommon : 자바(등록상표) 플랫폼을 위한 범용 클래스 오픈 소스 라이브러리
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
...
 *
 */

package org.jfree.date;

import static org.jfree.date.Month.FEBRUARY;

import java.util.*;

/**
 * 마이크로소프트 엑셀에서 구현한 방식과 유사하게 정수를 사용해
 * 날짜를 표현한다. 지원하는 날짜 범위는
 * 1900년 1월 1일부터 9999년 12월 31일까지다.
 * <p/>
 * 엑셀에는 실제로 윤년이 아니지만 1900년을 윤년으로 인식하는 고의적인 버그가 
 * 존재한다. 세부사항은 마이크로소프트 웹 사이트에서 214326번 아티클을 
 * 읽어보기 바란다.
 * <p/>
 * http://support.microsoft.com/kb/214326
 * <p/>
 * 엑셀은 1900년 1월 1일을 1로 취급하는 관례를 사용한다.
 * 이 클래스는 1900년 1월 1일을 2로 취급하는 관례를 사용한다.
 * 이 클래스를 사용할 경우 1900년도 1월과 2월을 계산하면
 * 날짜 번호가 엑셀 계산과 달라진다. 하지만 엑셀은
 * (1900년 2월 29일이 실제로 존재하지 않지만!) 하루를 추가하므로
 * 이 날짜 이후부터 계산한 결과는 일치한다.
 *
 * @author 데이비드 길버트
 */
public class SpreadsheetDate extends DayDate {
  public static final int EARLIEST_DATE_ORDINAL = 2;     // 1900년 1월 1일
  public static final int LATEST_DATE_ORDINAL = 2958465; // 1999년 12월 31일
  public static final int MINIMUM_YEAR_SUPPORTED = 1900;
  public static final int MAXIMUM_YEAR_SUPPORTED = 9999;
  static final int[] AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
  {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
  static final int[] LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
  {0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

  private int ordinalDay;
  private int day;
  private Month month;
  private int year;

  public SpreadsheetDate(int day, Month month, int year) {
    if (year < MINIMUM_YEAR_SUPPORTED || year > MAXIMUM_YEAR_SUPPORTED)
      throw new IllegalArgumentException(
        "The 'year' argument must be in range " +
        MINIMUM_YEAR_SUPPORTED + " to " + MAXIMUM_YEAR_SUPPORTED + ".");
    if (day < 1 || day > DateUtil.lastDayOfMonth(month, year))
      throw new IllegalArgumentException("Invalid 'day' argument.");

    this.year = year;
    this.month = month;
    this.day = day;
    ordinalDay = calcOrdinal(day, month, year);
  }

  public SpreadsheetDate(int day, int month, int year) {
    this(day, Month.fromInt(month), year);
  }

  public SpreadsheetDate(int serial) {
    if (serial < EARLIEST_DATE_ORDINAL || serial > LATEST_DATE_ORDINAL)
      throw new IllegalArgumentException(
        "SpreadsheetDate: Serial must be in range 2 to 2958465.");

    ordinalDay = serial;
    calcDayMonthYear();
  }

  public int getOrdinalDay() {
    return ordinalDay;
  }

  public int getYear() {
    return year;
  }

  public Month getMonth() {
    return month;
  }

  public int getDayOfMonth() {
    return day;
  }

  protected Day getDayOfWeekForOrdinalZero() {return Day.SATURDAY;}

  public boolean equals(Object object) {
    if (!(object instanceof DayDate))
      return false;

    DayDate date = (DayDate) object;
    return date.getOrdinalDay() == getOrdinalDay();
  }

  public int hashCode() {
    return getOrdinalDay();
  }

  public int compareTo(Object other) {
    return daysSince((DayDate) other);
  }

  private int calcOrdinal(int day, Month month, int year) {
    int leapDaysForYear = DateUtil.leapYearCount(year - 1);
    int daysUpToYear = (year - MINIMUM_YEAR_SUPPORTED) * 365 + leapDaysForYear;
    int daysUpToMonth = AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[month.toInt()];
    if (DateUtil.isLeapYear(year) && month.toInt() > FEBRUARY.toInt())
      daysUpToMonth++;
    int daysInMonth = day - 1;
    return daysUpToYear + daysUpToMonth + daysInMonth + EARLIEST_DATE_ORDINAL;
  }
  private void calcDayMonthYear() {
    int days = ordinalDay - EARLIEST_DATE_ORDINAL;
    int overestimatedYear = MINIMUM_YEAR_SUPPORTED + days / 365;
    int nonleapdays = days - DateUtil.leapYearCount(overestimatedYear);
    int underestimatedYear = MINIMUM_YEAR_SUPPORTED + nonleapdays / 365;

    year = huntForYearContaining(ordinalDay, underestimatedYear);
    int firstOrdinalOfYear = firstOrdinalOfYear(year);
    month = huntForMonthContaining(ordinalDay, firstOrdinalOfYear);
    day = ordinalDay - firstOrdinalOfYear - daysBeforeThisMonth(month.toInt());
  }

  private Month huntForMonthContaining(int anOrdinal, int firstOrdinalOfYear) {
    int daysIntoThisYear = anOrdinal - firstOrdinalOfYear;
    int aMonth = 1;
    while (daysBeforeThisMonth(aMonth) < daysIntoThisYear)
      aMonth++;

    return Month.fromInt(aMonth - 1);
  }

  private int daysBeforeThisMonth(int aMonth) {
    if (DateUtil.isLeapYear(year))
      return LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[aMonth] - 1;
    else
      return AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[aMonth] - 1;
  }

  private int huntForYearContaining(int anOrdinalDay, int startingYear) {
    int aYear = startingYear;
    while (firstOrdinalOfYear(aYear) <= anOrdinalDay)
      aYear++;

    return aYear - 1;
  }

  private int firstOrdinalOfYear(int year) {
    return calcOrdinal(1, Month.JANUARY, year);
  }

  public static DayDate createInstance(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return new SpreadsheetDate(calendar.get(Calendar.DATE),
      Month.fromInt(calendar.get(Calendar.MONTH) + 1),
      calendar.get(Calendar.YEAR));
    
  }
}