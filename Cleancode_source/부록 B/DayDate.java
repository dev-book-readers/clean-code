/* ========================================================================
 * JCommon : 자바(등록상표) 플랫폼을 위한 범용 클래스 오픈 소스 라이브러리
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
...
 */
package org.jfree.date;

import java.io.Serializable;
import java.util.*;

/**
 * 정밀도가 하루인 불변 날짜를 표현하는 추상 클래스다.
 * 여기서는 특정 고정 기준점에서 시작하는 날짜의 서수를 표현하는 정수에 
 * 개별 날짜를 매핑한다.
 *
 * java.util.Date를 그냥 쓰지 않은 이유는? 사리에 맞다면 그냥 썼을 테다.
 * 종종 java.util.Date는 *너무* 정밀하다. 이 클래스는 1000분의 1초까지
 * 정밀도로 시각을 표현한다(시간대에 따라 날짜가 달라진다).
 * 하루 중 시각, 시간대에 무관하게
 * 종종 특정 날짜만 표현하기를 원한다(예: 2015년 1월 21일).
 * 이게 바로 SerialDate를 재정의한 이유다.
 *
 * 인스턴스를 만들기 위해 DayDateFactory.makeDate를 활용하자.
 *
 * @author 데이비드 길버트
 * @author 로버트 C. 마틴. 마틴은 상당수 리팩터링 작업을 수행했다.
*/

public abstract class DayDate implements Comparable, Serializable {
  public abstract int getOrdinalDay();
  public abstract int getYear();
  public abstract Month getMonth();
  public abstract int getDayOfMonth();

  protected abstract Day getDayOfWeekForOrdinalZero();

  public DayDate plusDays(int days) {
    return DayDateFactory.makeDate(getOrdinalDay() + days);
  }

  public DayDate plusMonths(int months) {
    int thisMonthAsOrdinal = getMonth().toInt() - Month.JANUARY.toInt();
    int thisMonthAndYearAsOrdinal = 12 * getYear() + thisMonthAsOrdinal;
    int resultMonthAndYearAsOrdinal = thisMonthAndYearAsOrdinal + months;
    int resultYear = resultMonthAndYearAsOrdinal / 12;
    int resultMonthAsOrdinal = resultMonthAndYearAsOrdinal % 12 + Month.JANUARY.toInt();
    Month resultMonth = Month.fromInt(resultMonthAsOrdinal);
    int resultDay = correctLastDayOfMonth(getDayOfMonth(), resultMonth, resultYear);
    return DayDateFactory.makeDate(resultDay, resultMonth, resultYear);
  }

  public DayDate plusYears(int years) {
    int resultYear = getYear() + years;
    int resultDay = correctLastDayOfMonth(getDayOfMonth(), getMonth(), resultYear);
    return DayDateFactory.makeDate(resultDay, getMonth(), resultYear);
  }

  private int correctLastDayOfMonth(int day, Month month, int year) {
    int lastDayOfMonth = DateUtil.lastDayOfMonth(month, year);
    if (day > lastDayOfMonth)
      day = lastDayOfMonth;
    return day;
  }

  public DayDate getPreviousDayOfWeek(Day targetDayOfWeek) {
    int offsetToTarget = targetDayOfWeek.toInt() - getDayOfWeek().toInt();
    if (offsetToTarget >= 0)
      offsetToTarget -= 7;
    return plusDays(offsetToTarget);
  }

  public DayDate getFollowingDayOfWeek(Day targetDayOfWeek) {
    int offsetToTarget = targetDayOfWeek.toInt() - getDayOfWeek().toInt();
    if (offsetToTarget <= 0)
      offsetToTarget += 7;
    return plusDays(offsetToTarget);
  }

  public DayDate getNearestDayOfWeek(Day targetDayOfWeek) {
    int offsetToThisWeeksTarget = targetDayOfWeek.toInt() - getDayOfWeek().toInt();
    int offsetToFutureTarget = (offsetToThisWeeksTarget + 7) % 7;
    int offsetToPreviousTarget = offsetToFutureTarget - 7;

    if (offsetToFutureTarget > 3)
      return plusDays(offsetToPreviousTarget);
    else
      return plusDays(offsetToFutureTarget);
  }

  public DayDate getEndOfMonth() {
    Month month = getMonth();
    int year = getYear();
    int lastDay = DateUtil.lastDayOfMonth(month, year);
    return DayDateFactory.makeDate(lastDay, month, year);
  }

  public Date toDate() {
    final Calendar calendar = Calendar.getInstance();
    int ordinalMonth = getMonth().toInt() - Month.JANUARY.toInt();
    calendar.set(getYear(), ordinalMonth, getDayOfMonth(), 0, 0, 0);
    return calendar.getTime();
  }

  public String toString() {
    return String.format("%02d-%s-%d", getDayOfMonth(), getMonth(), getYear());
  }

  public Day getDayOfWeek() {
    Day startingDay = getDayOfWeekForOrdinalZero();
    int startingOffset = startingDay.toInt() - Day.SUNDAY.toInt();
    int ordinalOfDayOfWeek = (getOrdinalDay() + startingOffset) % 7;
    return Day.fromInt(ordinalOfDayOfWeek + Day.SUNDAY.toInt());
  }

  public int daysSince(DayDate date) {
    return getOrdinalDay() - date.getOrdinalDay();
  }

  public boolean isOn(DayDate other) {
    return getOrdinalDay() == other.getOrdinalDay();
  }

  public boolean isBefore(DayDate other) {
    return getOrdinalDay() < other.getOrdinalDay();
  }

  public boolean isOnOrBefore(DayDate other) {
    return getOrdinalDay() <= other.getOrdinalDay();
  }

  public boolean isAfter(DayDate other) {
    return getOrdinalDay() > other.getOrdinalDay();
  }

  public boolean isOnOrAfter(DayDate other) {
    return getOrdinalDay() >= other.getOrdinalDay();
  }

  public boolean isInRange(DayDate d1, DayDate d2) {
    return isInRange(d1, d2, DateInterval.CLOSED);
  }

  public boolean isInRange(DayDate d1, DayDate d2, DateInterval interval) {
    int left = Math.min(d1.getOrdinalDay(), d2.getOrdinalDay());
    int right = Math.max(d1.getOrdinalDay(), d2.getOrdinalDay());
    return interval.isIn(getOrdinalDay(), left, right);
  }
}