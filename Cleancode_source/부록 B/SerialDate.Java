/* ========================================================================
 * JCommon : 자바(등록상표) 플랫폼을 위한 범용 클래스 오픈 소스 라이브러리
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * 프로젝트 정보:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [자바는 썬 마이크로시스템즈의 등록 상표로서, 
 * 미국과 다른 국가에서 적용된다.]
 *
 * ---------------
 * SerialDate.java
 * ---------------
 * (C) Copyright 2001-2005, by Object Refinery Limited.
 *
 * 원래 저자: 데이비드 길버트(Object Refinery Limited);
 * 공헌자:   -;
 *
 * $Id: SerialDate.java,v 1.7 2005/11/03 09:25:17 mungady Exp $
 *
* 변경 내역 (11-Oct-2001부터)
* --------------------------
 * 11-Oct-2001 : 클래스를 다시 정리하고 새로운 패키지인
 *               com.jrefinery.date로 옮겼다 (DG);
 * 05-Nov-2001 : getDescription() 메서드를 추가했으며
 *               NotableDate class를 제거했다(DG);
 * 12-Nov-2001 : IBD가 setDescription() 메서드를 요구한다. NotableDate
 *               클래스를 없앴다 (DG); getPreviousDayOfWeek(),
 *               getFollowingDayOfWeek(), getNearestDayOfWeek()를 변경해
 *               버그를 수정했다(DG);
 * 05-Dec-2001 : SpreadsheetDate 클래스에 존재하는 버그를 수정했다 (DG);
 * 29-May-2002 : month 상수를 독자적인 인터페이스로 옮겼다
 *               (MonthConstants) (DG);
 * 27-Aug-2002 : addMonths() 메서드에 있는 버그를 수정했다. N???levka Petr 덕분이다 (DG);
 * 03-Oct-2002 : Checkstyle이 보고한 오류를 수정했다 (DG);
 * 13-Mar-2003 : Serializable를 구현했다 (DG);
 * 29-May-2003 : addMonths 메서드에 있는 버그를 수정했다 (DG);
 * 04-Sep-2003 : Comparable을 구현했다. isInRange Javadocs를 갱신했다 (DG);
 * 05-Jan-2005 : addYears() 메서드에 있는 버그를 수정했다 (1096282) (DG);
 * 
 */

package org.jfree.date;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  날짜를 처리하기 위해 우리 요구 사항을 정의하는 추상 클래스로
 *  특정 구현에 얽매이지 않는다.
 *  <P>
 *  요구 사항 1 : 최소한 엑셀에서 제공하는 날짜 기능은 맞춰야 한다.
 *  요구 사항 2 : 클래스는 불변이다.
 *  <P>
 *  java.util.Date를 그냥 쓰지 않은 이유는? 사리에 맞다면 그냥 썼을 테다.
 *  종종 java.util.Date는 *너무* 정밀하다. 이 클래스는 1000분의 1초까지
 *  정밀도로 시각을 표현한다(시간대에 따라 날짜가 달라진다).
 *  하루 중 시각, 시간대에 무관하게 
 *  종종 특정 날짜만 표현하기를 원한다(예: 2015년 1월 21일).
 *  이게 바로 SerialDate를 재정의한 이유다.
 *  <P>
 *  정확한 구현을 걱정하지 않고서도 getInstance()를 호출해,
 * SerialDate의 구체화된 하위 클래스를 얻을 수 있다.
 *
 * @author 데이비드 길버트
 */
public abstract class SerialDate implements Comparable, 
                                            Serializable, 
                                            MonthConstants {

  /** 직렬화를 위해 */
  private static final long serialVersionUID = -293716040467423637L;
  
  /** 자료 형식 심벌 */
  public static final DateFormatSymbols
      DATE_FORMAT_SYMBOLS = new SimpleDateFormat().getDateFormatSymbols();

  /** 1900년 1월 1일에서 시작하는 직렬 번호 */
  public static final int SERIAL_LOWER_BOUND = 2;

  /** 9999년 12월 31일에 끝나는 직렬 번호 */
  public static final int SERIAL_UPPER_BOUND = 2958465;

  /** 이 클래스 형식이 지원하는 첫 해 */
  public static final int MINIMUM_YEAR_SUPPORTED = 1900;

  /** 이 클래스 형식이 지원하는 마지막 해 */
  public static final int MAXIMUM_YEAR_SUPPORTED = 9999;

  /** 월요일을 위한 상수로 java.util.Calendar.MONDAY와 동일 */
  public static final int MONDAY = Calendar.MONDAY;

  /** 
   * 화요일을 위한 상수로 java.util.Calendar.TUESDAY와 동일
   */
  public static final int TUESDAY = Calendar.TUESDAY;

  /** 
   * 수요일을 위한 상수로
   * java.util.Calendar.WEDNESDAY와 동일
   */
  public static final int WEDNESDAY = Calendar.WEDNESDAY;

  /** 
   * 목요일을 위한 상수로 java.util.Calendar.THURSDAY와 동일
   */
  public static final int THURSDAY = Calendar.THURSDAY;

  /** 금요일을 위한 상수로 java.util.Calendar.FRIDAY와 동일 */
  public static final int FRIDAY = Calendar.FRIDAY;

  /** 
   * 토요일을 위한 상수로 java.util.Calendar.SATURDAY와 동일
   */
  public static final int SATURDAY = Calendar.SATURDAY;

  /** 일요일을 위한 상수로 java.util.Calendar.SUNDAY와 동일 */
  public static final int SUNDAY = Calendar.SUNDAY;

  /** 윤년이 아닌 경우에 달마다 마지막 일자 */
  static final int[] LAST_DAY_OF_MONTH =
    {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  /** 윤년이 아닌 경우에 달마다 마지막 날의 연간 누적 일자 */
  static final int[] AGGREGATE_DAYS_TO_END_OF_MONTH =
    {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};

  /** 윤년이 아닌 경우에 직전 달마다 마지막 날의 연간 누적 일자 */
  static final int[] AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
      {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};

  /** 윤년인 경우에 달마다 마지막 날의 연간 누적 일자 */
  static final int[] LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_MONTH =
    {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

  /** 
   * 윤년인 경우에 직전 달마다 마지막 날의 연간 누적 일자
   */
  static final int[] 
    LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
      {0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

  /** 달에서 첫 주를 참조하는 상수 */
  public static final int FIRST_WEEK_IN_MONTH = 1;

  /** 달에서 둘째 주를 참조하는 상수 */
  public static final int SECOND_WEEK_IN_MONTH = 2;

  /** 달에서 셋째 주를 참조하는 상수 */
  public static final int THIRD_WEEK_IN_MONTH = 3;

  /** 달에서 넷째 주를 참조하는 상수 */
  public static final int FOURTH_WEEK_IN_MONTH = 4;

  /** 달에서 마지막 주를 참조하는 상수 */
  public static final int LAST_WEEK_IN_MONTH = 0;

  /** 범위 상수 */
  public static final int INCLUDE_NONE = 0;

  /** 범위 상수 */
  public static final int INCLUDE_FIRST = 1;

  /** 범위 상수 */
  public static final int INCLUDE_SECOND = 2;

  /** 범위 상수 */
  public static final int INCLUDE_BOTH = 3;

  /** 
   * 고정된 날짜를 기준으로 상대적으로 주 중 일자를 명세하기 위한 상수
   * 
   */
  public static final int PRECEDING = -1;

  /** 
   * 고정된 날짜를 기준으로 상대적으로 주 중 일자를 명세하기 위한 상수
   * 
   */
  public static final int NEAREST = 0;

  /** 
   * 고정된 날짜를 기준으로 상대적으로 주 중 일자를 명세하기 위한 상수
   * 
   */
  public static final int FOLLOWING = 1;

  /** 날짜에 대한 설명 */
  private String description;

  /**
   * 기본 생성자
   */
  protected SerialDate() {
  }

  /**
   * 넘어온 정수 코드가 유효한 주 중 일자를 표현하면 <code>true</code>를,
   * 그렇지 않으면<code>false</code>를 반환한다.
   *
   * @param code  유효성 검사 대상
   *
   * @return 넘어온 정수 코드가 유효한 주 중 일자를 표현하면
   *         <code>true</code>, 그렇지 않으면 <code>false</code>
   */
  public static boolean isValidWeekdayCode(final int code) {

    switch(code) {
      case SUNDAY:
      case MONDAY:
      case TUESDAY:
      case WEDNESDAY:
      case THURSDAY:
      case FRIDAY:
      case SATURDAY:
      return true;
      default:
      return false;
    }

  }

  /**
   * 넘어온 문자열을 주 중 일자로 변환한다.
   *
   * @param s 주 중 일자를 표현하는 문자열
   *
   * @return 변환 불가능일 경우 <code>-1</code>, 그렇지 않으면
   *         주 중 일자
   */
  public static int stringToWeekdayCode(String s) {

    final String[] shortWeekdayNames 
        = DATE_FORMAT_SYMBOLS.getShortWeekdays();
    final String[] weekDayNames = DATE_FORMAT_SYMBOLS.getWeekdays();

    int result = -1;
    s = s.trim();
    for (int i = 0; i < weekDayNames.length; i++) {
      if (s.equals(shortWeekdayNames[i])) {
        result = i;
        break;
      }
      if (s.equals(weekDayNames[i])) {
        result = i;
        break;
      }
    }
    return result;

  }

  /**
   * 넘어온 주 중 일자를 표현하는 문자열을 반환한다.
   * <P>
   * 좀 더 개선된 방법이 필요하다.
   *
   * @param weekday  주 중 일자
   *
   * @return 넘어온 주 중 일자를 표현하는 문자열
   */
  public static String weekdayCodeToString(final int weekday) {

    final String[] weekdays = DATE_FORMAT_SYMBOLS.getWeekdays();
    return weekdays[weekday];

  }

  /**
  * 달 이름을 담은 배열을 반환한다.
   *
   * @return 달 이름을 담은 배열
   */
  public static String[] getMonths() {

    return getMonths(false);

  }

  /**
   * 달 이름을 담은 배열을 반환한다.
   *
   * @param shortened 줄인 달 이름을 반환해야 함을 지시하는
   *                   플래그
   *
   * @return 달 이름을 담은 배열
   */
  public static String[] getMonths(final boolean shortened) {

    if (shortened) {
        return DATE_FORMAT_SYMBOLS.getShortMonths();
    }
    else {
        return DATE_FORMAT_SYMBOLS.getMonths();
    }

  }

  /**
   * 넘어온 정수 코드가 유효한 달일 경우 true를 반환한다.
   *
   * @param code 유효성 검사 대상
   *
   * @return 넘어온 정수 코드가 유효한 달을 표현하면
   *         <code>true</code>
   */
  public static boolean isValidMonthCode(final int code) {

    switch(code) {
      case JANUARY:
      case FEBRUARY:
      case MARCH:
      case APRIL:
      case MAY:
      case JUNE:
      case JULY:
      case AUGUST:
      case SEPTEMBER:
      case OCTOBER:
      case NOVEMBER:
      case DECEMBER:
        return true;
      default:
        return false;
    }

  }

  /**
   * 넘어온 달이 속한 분기를 반환한다.
   *
   * @param code 달(1-12).
   *
   * @return 달이 속한 분기
   * @throws java.lang.IllegalArgumentException
   */
  public static int monthCodeToQuarter(final int code) {

    switch(code) {
      case JANUARY:
      case FEBRUARY:
      case MARCH: return 1;
      case APRIL:
      case MAY:
      case JUNE: return 2;
      case JULY:
      case AUGUST:
      case SEPTEMBER: return 3;
      case OCTOBER:
      case NOVEMBER:
      case DECEMBER: return 4;
      default: throw new IllegalArgumentException(
        "SerialDate.monthCodeToQuarter: invalid month code.");
    }

  }

  /**
   * 넘어온 달을 표현하는 문자열을 반환한다.
   * <P>
   * 반환되는 문자열은 기본 로케일에서 얻은
   * 온전한 달 이름 형태다.
   *
   * @param month 달
   *
   * @return 넘어온 달을 표현하는 문자열
   */
  public static String monthCodeToString(final int month) {

    return monthCodeToString(month, false);

  }

  /**
   * 넘어온 달을 표현하는 문자열을 반환한다.
   * <P>
   * 반환되는 문자열은 기본 로케일에서 얻은
   * 온전하거나 줄인 달 이름 형태다.
   *
   * @param month 달
   * @param shortened if <code>true</code>이면
   *                   줄인 달 형태를 반환한다.
   *
   * @return 넘어온 달을 표현하는 문자열
   * @throws java.lang.IllegalArgumentException
   */
  public static String monthCodeToString(final int month, 
                                         final boolean shortened) {

    // 인수 점검...
    if (!isValidMonthCode(month)) {
      throw new IllegalArgumentException(
        "SerialDate.monthCodeToString: month outside valid range.");
    }

    final String[] months;

    if (shortened) {
      months = DATE_FORMAT_SYMBOLS.getShortMonths();
    }
    else {
      months = DATE_FORMAT_SYMBOLS.getMonths();
    }

    return months[month - 1];

  }

  /**
   * 문자열을 달 코드로 변환한다.
   * <P>
   * 이 메서드는 문자열에 대응하는
   * JANUARY, FEBRUARY,..., DECEMBER 중 하나를 반환한다.
   * 문자열을 인식하지 못하면 이 메서드는 –1을 반환한다.
   *
   * @param s 해석할 문자열
   *
   * @return 문자열을 해석하지 못하면 <code>-1</code>,
   *         해석하면 달 상수
   */
  public static int stringToMonthCode(String s) {

    final String[] shortMonthNames = DATE_FORMAT_SYMBOLS.getShortMonths();
    final String[] monthNames = DATE_FORMAT_SYMBOLS.getMonths();

    int result = -1;
    s = s.trim();

    // 먼저 정수(1-12)로 문자열을 해석하려고 시도한다.
    try {
      result = Integer.parseInt(s);
    }
    catch (NumberFormatException e) {
      // suppress
    }

    // 다음으로 달 이름을 탐색한다.
    if ((result < 1) || (result > 12)) {
      for (int i = 0; i < monthNames.length; i++) {
        if (s.equals(shortMonthNames[i])) {
          result = i + 1;
          break;
        }
        if (s.equals(monthNames[i])) {
          result = i + 1;
          break;
        }
      }
    }

    return result;

  }

  /**
   * 달 중 유효한 주를 표현하는 정수 코드가 넘어오면 true를,
   * 그렇지 않으면 false를 반환한다.
   *
   * @param code 유효성 검사 대상 코드
   * @return 넘어온 정수 코드가 달 중 유효한 주를 표현하면
   *         <code>true</code>
   */
  public static boolean isValidWeekInMonthCode(final int code) {

    switch(code) {
      case FIRST_WEEK_IN_MONTH:
      case SECOND_WEEK_IN_MONTH:
      case THIRD_WEEK_IN_MONTH:
      case FOURTH_WEEK_IN_MONTH:
      case LAST_WEEK_IN_MONTH: return true;
      default: return false;
    }

  }

  /**
   * 넘어온 연도가 윤년인지 아닌지를 판단한다.
   *
   * @param yyyy 연도(범위는 1900년부터 9999년까지)
   *
   * @return 넘어온 연도가 윤년일 경우 <code>true</code>
   */
  public static boolean isLeapYear(final int yyyy) {

    if ((yyyy % 4) != 0) {
      return false;
    }
    else if ((yyyy % 400) == 0) {
      return true;
    }
    else if ((yyyy % 100) == 0) {
      return false;
    }
    else {
      return true;
    }

  }

  /**
   * 1900년부터 넘어온 연도까지 윤년 횟수를 반환한다.
   * 넘어온 연도도 포함
   * <P>
   * 1900년은 윤년이 아님에 주의하자.
   *
   * @param yyyy 연도(범위는 1900년부터 9999년까지)
   *
   * @return 1900년부터 넘어온 연도까지 윤년 횟수
   */
  public static int leapYearCount(final int yyyy) {

    final int leap4 = (yyyy - 1896) / 4;
    final int leap100 = (yyyy - 1800) / 100;
    final int leap400 = (yyyy - 1600) / 400;
    return leap4 - leap100 + leap400;

  }

  /**
   * 윤년을 고려해 달의 마지막 일자를 반환한다.
   *
   *
   * @param month 달
   * @param yyyy 연도(범위는 1900년부터 9999년까지)
   *
   * @return 달의 마지막 일자
   */
  public static int lastDayOfMonth(final int month, final int yyyy) {

    final int result = LAST_DAY_OF_MONTH[month];
    if (month != FEBRUARY) {
      return result;
    }
    else if (isLeapYear(yyyy)) {
      return result + 1;
    }
    else {
      return result;
    }

  }

  /**
   * 넘어온 기준 날짜에 넘어온 일자를 더해 새로운 날짜를 만든다.
   *
   *
   * @param days 더할 일자(음수도 가능)
   * @param base 기준 날짜
   *
   * @return 새로운 날짜
   */
  public static SerialDate addDays(final int days, final SerialDate base) {

    final int serialDayNumber = base.toSerial() + days;
    return SerialDate.createInstance(serialDayNumber);

  }

  /**
   * 기준 날짜에 넘어온 달을 더해 새로운 날짜를 만든다.
   *
   * <P>
   * 기준 날짜가 월말에 가깝다면, 결과가 약간 조정될지도 모른다.
   * 예:  5월 31일 + 1 달 = 6월 30일
   *
   * @param months 더할 달(음수도 가능)
   * @param base 기준 날짜
   *
   * @return 새로운 날짜
   */
  public static SerialDate addMonths(final int months, 
                                     final SerialDate base) {

    final int yy = (12 * base.getYYYY() + base.getMonth() + months - 1) 
                   / 12;
    final int mm = (12 * base.getYYYY() + base.getMonth() + months - 1) 
                   % 12 + 1;
    final int dd = Math.min(
        base.getDayOfMonth(), SerialDate.lastDayOfMonth(mm, yy)
    );
    return SerialDate.createInstance(dd, mm, yy);

  }

  /**
   * 기준 날짜에 넘어온 연을 더해 새로운 날짜를 만든다.
   *
   *
   * @param years 더할 연(음수도 가능)
   * @param base 기준 날짜
   *
   * @return 새로운 날짜
   */
  public static SerialDate addYears(final int years, final SerialDate base) {

    final int baseY = base.getYYYY();
    final int baseM = base.getMonth();
    final int baseD = base.getDayOfMonth();

    final int targetY = baseY + years;
    final int targetD = Math.min(
        baseD, SerialDate.lastDayOfMonth(baseM, targetY)
    );

    return SerialDate.createInstance(targetD, baseM, targetY);

  }

  /**
   * 넘어온 주 중 일자 범위에 해당하면서
   * 기준 날짜보다 빠른 마지막 날짜를 반환한다.
   *
   * @param targetWeekday 목표로 삼은 주 중 일자
   * @param base 기준 날짜
   *
   * @return 넘어온 주 중 일자 범위에 해당하면서
   *         기준 날짜보다 앞선 마지막 날짜
   */
  public static SerialDate getPreviousDayOfWeek(final int targetWeekday, 
                                                final SerialDate base) {

    // 인수 점검...
    if (!SerialDate.isValidWeekdayCode(targetWeekday)) {
      throw new IllegalArgumentException(
        "Invalid day-of-the-week code."
        );
    }

    // 날짜 탐색...
    final int adjust;
    final int baseDOW = base.getDayOfWeek();
    if (baseDOW > targetWeekday) {
      adjust = Math.min(0, targetWeekday - baseDOW);
    }
    else {
      adjust = -7 + Math.max(0, targetWeekday - baseDOW);
    }

    return SerialDate.addDays(adjust, base);

  }

  /**
   * 넘어온 주 중 일자 범위에 해당하면서
   * 기준 날짜보다 늦은 첫 날짜를 반환한다.
   *
   * @param targetWeekday 목표로 삼은 주 중 일자
   * @param base 기준 날짜
   *
   * @return 넘어온 주 중 일자 범위에 해당하면서
   *         기준 날짜보다 늦은 첫 날짜
   */
  public static SerialDate getFollowingDayOfWeek(final int targetWeekday, 
                                                 final SerialDate base) {

    // 인수 점검...
    if (!SerialDate.isValidWeekdayCode(targetWeekday)) {
      throw new IllegalArgumentException(
          "Invalid day-of-the-week code."
    );
    }

    // 날짜 탐색...
    final int adjust;
    final int baseDOW = base.getDayOfWeek();
    if (baseDOW > targetWeekday) {
      adjust = 7 + Math.min(0, targetWeekday - baseDOW);
    }
    else {
      adjust = Math.max(0, targetWeekday - baseDOW);
    }

    return SerialDate.addDays(adjust, base);
  }

  /**
   * 넘어온 주 중 일자 범위에 해당하면서
   * 기준 날짜에 가장 근접한 날짜를 반환한다.
   *
   * @param targetDOW 목표로 삼은 주 중 일자
   * @param base 기준 날짜
   *
   * @return 넘어온 주 중 일자 범위에 해당하면서
   *         기준 날짜에 가장 근접한 날짜
   */
  public static SerialDate getNearestDayOfWeek(final int targetDOW,  
                                               final SerialDate base) {

    // 인수 점검...
    if (!SerialDate.isValidWeekdayCode(targetDOW)) {
      throw new IllegalArgumentException(
          "Invalid day-of-the-week code."
      );
    }

    // 날짜 탐색...
    final int baseDOW = base.getDayOfWeek();
    int adjust = -Math.abs(targetDOW - baseDOW);
    if (adjust >= 4) {
      adjust = 7 - adjust;
    }
    if (adjust <= -4) {
      adjust = 7 + adjust;
    }
    return SerialDate.addDays(adjust, base);

  }

  /**
   * 현재 달의 마지막 일자를 얻는다.
   *
   * @param base 기준 날짜
   *
   * @return 새로운 직렬 날짜
   */
  public SerialDate getEndOfCurrentMonth(final SerialDate base) {
    final int last = SerialDate.lastDayOfMonth(
        base.getMonth(), base.getYYYY()
    );
    return SerialDate.createInstance(last, base.getMonth(), base.getYYYY());
  }

  /**
   * 월 중 주에 대응하는 문자열을 반환한다.
   * <P>
   * 좀 더 개선된 방법이 필요하다.
   *
   * @param count 월 중 주를 표현하는 정수 코드
   *
   * @return 월 중 주에 대응하는 문자열
   */
  public static String weekInMonthToString(final int count) {

    switch (count) {
      case SerialDate.FIRST_WEEK_IN_MONTH : return "First";
      case SerialDate.SECOND_WEEK_IN_MONTH : return "Second";
      case SerialDate.THIRD_WEEK_IN_MONTH : return "Third";
      case SerialDate.FOURTH_WEEK_IN_MONTH : return "Fourth";
      case SerialDate.LAST_WEEK_IN_MONTH : return "Last";
      default :
      return "SerialDate.weekInMonthToString(): invalid code.";
    }

  }

  /**
   * 넘어온 ‘상대’ 개념을 표현하는 문자열을 반환한다.
   * <P>
   * 좀 더 개선된 방법이 필요하다.
   *
   * @param relative ‘상대’ 개념을 표현하는 상수
   *
   * @return 넘어온 ‘상대’ 개념을 표현하는 문자열
   */
  public static String relativeToString(final int relative) {

    switch (relative) {
      case SerialDate.PRECEDING : return "Preceding";
      case SerialDate.NEAREST : return "Nearest";
      case SerialDate.FOLLOWING : return "Following";
      default : return "ERROR : Relative To String";
    }

  }

  /**
   * 다음 구체화된 하위 클래스의 인스턴스를 반환하는 팩토리 메서드
   * {@link SerialDate}
   *
   * @param day 일자(1-31).
   * @param month 달(1-12).
   * @param yyyy 연도(범위는 1900년부터 9999년까지)
   *
   * @return {@link SerialDate}의 인스턴스
   */
  public static SerialDate createInstance(final int day, final int month, 
                                          final int yyyy) {
    return new SpreadsheetDate(day, month, yyyy);
  }

  /**
   * 다음 구체화된 하위 클래스의 인스턴스를 반환하는 팩토리 메서드
   * {@link SerialDate}.
   *
   * @param serial 날짜를 표현하는 직렬 번호(1900년 1월 1일 = 2)
   *
   * @return SerialDate의 인스턴스
   */
  public static SerialDate createInstance(final int serial) {
    return new SpreadsheetDate(serial);
  }

  /**
   * SerialDate의 하위 클래스 인스턴스를 반환하는 펙토리 메서드
   *
   * @param date 자바 날짜 객체
   *
   * @return SerialDate의 인스턴스
   */
  public static SerialDate createInstance(final java.util.Date date) {

    final GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return new SpreadsheetDate(calendar.get(Calendar.DATE),
                               calendar.get(Calendar.MONTH) + 1,
                               calendar.get(Calendar.YEAR));

  }

  /**
   * 날짜에 대한 직렬 번호를 반환한다. 여기서 1900년 1월 1일이 2로서
   * 마이크로소프트 엑셀 포 윈도와 로터스 1-2-3에서 사용하는
   * 숫자 시스템에 대응한다).
   *
   * @return 날짜에 대한 직렬 번호
   */
  public abstract int toSerial();

  /**
   * java.util.Date를 반환한다. java.util.Date가 SerialDate보다 좀 더
   * 정밀하므로 ‘하루 중 특정 시각’을 위한 관례를 정의할 필요가 있다.
   *
   * @return <code>java.util.Date</code>와 동일
   */
  public abstract java.util.Date toDate();

  /**
   * 날짜에 대한 설명을 반환한다.
   *
   * @return 날짜에 대한 설명
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * 날짜 설명을 설정한다.
   *
   * @param description 날짜에 대한 새로운 설명
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * 날짜를 문자열로 변환한다.
   *
   * @return 날짜를 표현하는 문자열
   */
  public String toString() {
    return getDayOfMonth() + "-" + SerialDate.monthCodeToString(getMonth())
                           + "-" + getYYYY();
  }

  /**
   * 연도를 반환한다(유효한 범위는 1900년부터 9999년까지를 가정한다).
   *
   * @return 연도
   */
  public abstract int getYYYY();

  /**
   * 달을 반환한다(1월 = 1, 2월 = 2, 3월 = 3).
   *
   * @return 연 중 달
   */
  public abstract int getMonth();

  /**
   * 월 중 일자를 반환한다.
   *
   * @return 월 중 일자
   */
  public abstract int getDayOfMonth();

  /**
   * 주 중 일자를 반환한다.
   *
   * @return 주 중 일자
   */
  public abstract int getDayOfWeek();

  /**
   * 이 날짜와 넘어온 ‘다른’ 날짜 사이의 차이를 일수로 반환한다.
   *
   * <P>
   * 이 날짜가 ‘다른’ 날짜보다 뒤에 나올 경우 양수,
   * 이 날짜가 ‘다른’ 날짜보다 앞에 나올 경우 음수
   *
   * @param other 비교할 다른 날짜
   *
   * @return 이 날짜와 다른 날짜 사이의 차이
   */
  public abstract int compare(SerialDate other);

  /**
   * 이 SerialDate가 넘어온 SerialDate와 동일한 경우 true를 반환한다.
   *
   *
   * @param other 비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate와 동일한 경우
   *         <code>true</code>
   */
  public abstract boolean isOn(SerialDate other);

  /**
   * 이 SerialDate가 넘어온 SerialDate보다 앞설 경우 true를 반환한다.
   *
   *
   * @param other 비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate보다 앞설 경우
   *         <code>true</code>
   */
  public abstract boolean isBefore(SerialDate other);

  /**
   * 이 SerialDate가 넘어온 SerialDate와 같거나 앞설 경우 true를 반환한다.
   *
   *
   * @param other 비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate과 같거나 앞설 경우
   *         <code>true<code>
   */
  public abstract boolean isOnOrBefore(SerialDate other);

  /**
   * 이 SerialDate가 넘어온 SerialDate보다 뒤질 경우 true를 반환한다.
   *
   *
   * @param other 비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate보다 뒤질 경우
   *         <code>true</code>
   */
  public abstract boolean isAfter(SerialDate other);

  /**
   * 이 SerialDate가 넘어온 SerialDate와 같거나 뒤질 경우 true를 반환한다.
   *
   *
   * @param other 비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate와 같거나 뒤질 경우
   *         <code>true</code>
   */
  public abstract boolean isOnOrAfter(SerialDate other);

  /**
   * 이 {@link SerialDate}가 넘어온 범위 내에 들면
   * <code>true</code>를 반환한다(경계값 포함).
   * d1과 d2 날짜 순서는 중요하지 않다.
   *
   * @param d1 범위를 결정하기 위한 경계 날짜
   * @param d2 범위를 결정하기 위한 또 다른 경계 날짜
   *
   * @return 부울값
   */
  public abstract boolean isInRange(SerialDate d1, SerialDate d2);

  /**
   * 이 {@link SerialDate}가 넘어온 범위 내에 들면
   * <code>true</code>를 반환한다(호출자가 경계 포함 유무를 명세한다).
   * d1과 d2 날짜 순서는 중요하지 않다.
   *
   * @param d1 범위를 결정하기 위한 경계 날짜
   * @param d2 범위를 결정하기 위한 또 다른 경계 날짜
   * @param include 시작과 끝 날짜를 범위 내에 포함할지를 제어하는
   *                 코드
   *
   * @return 부울값
   */
  public abstract boolean isInRange(SerialDate d1, SerialDate d2, 
                                    int include);

  /**
   * 넘어온 주 중 일자 범위에 해당하면서
   * 이 날짜보다 앞선 마지막 날짜를 반환한다.
   *
   * @param targetDOW 목표로 삼은 주 중 일자
   *
   * @return 넘어온 주 중 일자 범위에 해당하면서
   *         이 날짜보다 앞선 마지막 날짜
   */
  public SerialDate getPreviousDayOfWeek(final int targetDOW) {
    return getPreviousDayOfWeek(targetDOW, this);
  }

  /**
   * 넘어온 주 중 일자 범위에 해당하면서
   * 이 날짜보다 늦은 첫 날짜를 반환한다.
   *
   * @param targetDOW 목표로 삼은 주 중 일자
   *
   * @return 넘어온 주 중 일자 범위에 해당하면서
   *         이 날짜보다 늦은 첫 날짜
   */
  public SerialDate getFollowingDayOfWeek(final int targetDOW) {
    return getFollowingDayOfWeek(targetDOW, this);
  }

  /**
   * 넘어온 주 중 일자 범위에 해당하면서 이 날짜에 가장 근접한 날짜를 반환한다.
   *
   * @param targetDOW 목표로 삼은 주 중 일자
   *
   * @return 넘어온 주 중 일자 범위에 해당하면서 이 날짜에 가장 근접한 날짜
   */
  public SerialDate getNearestDayOfWeek(final int targetDOW) {
    return getNearestDayOfWeek(targetDOW, this);
  }

}