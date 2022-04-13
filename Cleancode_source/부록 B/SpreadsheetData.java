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
 * [자바는 썬 마이크로시스템의 등록 상표로서,
 * 미국과 다른 국가에서 적용된다.]
 *
 * --------------------
 * SpreadsheetDate.java
 * --------------------
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 *
 * 원래 저자: 데이비드 길버트(Object Refinery Limited);
 * 공헌자:   -;
 *
 * $Id: SpreadsheetDate.java,v 1.8 2005/11/03 09:25:39 mungady Exp $
 *
 * 변경 내역
 * -------
 * 11-Oct-2001 : 버전 1 (DG);
 * 05-Nov-2001 : getDescription()와 setDescription() 메서드를 추가했다 (DG);
 * 12-Nov-2001 : ExcelDate.java에서 SpreadsheetDate.java로 이름을 바꿨다 (DG);
 *               직렬 번호에서 일자, 달, 연도를 계산하는 버그를 수정했다
 *               (DG);
 * 24-Jan-2002 : 일자, 달, 연도에서 직렬 번호를 계산하는 버그를 수정했다
 *               보고해 준 트레버 힐스에게 감사한다 (DG);
 * 29-May-2002 : equals(Object) 메서드를 추가했다(SourceForge ID 558850) (DG);
 * 03-Oct-2002 : Checkstyle이 보고한 오류를 수정했다 (DG);
 * 13-Mar-2003 : Serializable를 구현했다 (DG);
 * 04-Sep-2003 : isInRange() 메서드를 완성했다 (DG);
 * 05-Sep-2003 : Comparable를 구현했다 (DG);
 * 21-Oct-2003 : hashCode() 메서드를 추가했다 (DG);
 *
 */

package org.jfree.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 마이크로소프트 엑셀에서 구현한 방식과 유사하게 정수를 사용해
* 날짜를 표현한다. 지원하는 날짜 범위는
* 1990년 1월 1일부터 9999년 12월 31일까지다.
 * <P>
 * 엑셀에는 실제로 윤년이 아니지만 1900년을 윤년으로 인식하는 고의적인 버그가 
 * 존재한다. 세부사항은 마이크로소프트 웹 사이트에서 214326번 아티클을 
 * 읽어보기 바란다.
 * <P>
 * http://support.microsoft.com/kb/214326
 * <P>
 * 엑셀은 1900년 1월 1일을 1로 취급하는 관례를 사용한다.
 * 이 클래스는 1900년 1월 1일을 2로 취급하는 관례를 사용한다.
 * 이 클래스를 사용할 경우 1900년도 1월과 2월을 계산하면
 * 날짜 번호가 엑셀 계산과 달라진다. 하지만 엑셀은
 * (1900년 2월 29일이 실제로 존재하지 않지만!) 하루를 추가하므로
 * 이 날짜 이후부터 계산한 결과는 일치한다.
 *
 * @author 데이비드 길버트
 */
public class SpreadsheetDate extends SerialDate {

  /** 직렬화를 위해 */
  private static final long serialVersionUID = -2039586705374454461L;

  /**
   * 날짜 번호 (1900년 1월 1일 = 2, 1900년 1월 2일 = 3, ..., 9999년 12월 31일 
   * = 2958465).
   */
  private int serial;

  /** 일자 (1에서 28, 29, 30 또는 31, 달에 따라 달라짐) */
  private int day;

  /** 달 (1월부터 12월까지). */
  private int month;

  /** 연도(1900년부터 9999년까지). */
  private int year;

  /** 날짜를 위한 추가 설명 */
  private String description;

  /**
   * 새로운 date 인스턴스를 만든다.
   *
   * @param day 일자(범위는 1일에서 28/29/30/31일까지)
   * @param month 달(범위는 1월에서 12월까지)
   * @param year 연도(범위는 1900년부터 9999년까지)
   */
  public SpreadsheetDate(final int day, final int month, final int year) {

    if ((year >= 1900) && (year <= 9999)) {
      this.year = year;
    }
    else {
      throw new IllegalArgumentException(
          "The 'year' argument must be in range 1900 to 9999."
      );
    }

    if ((month >= MonthConstants.JANUARY)
        && (month <= MonthConstants.DECEMBER)) {
      this.month = month;
    }
    else {
      throw new IllegalArgumentException(
          "The 'month' argument must be in the range 1 to 12."
      );
    }

    if ((day >= 1) && (day <= SerialDate.lastDayOfMonth(month, year))) {
      this.day = day;
    }
    else {
      throw new IllegalArgumentException("Invalid 'day' argument.");
    }

    // 직렬 번호는 일자-달-연도와 동기화할 필요가 있다.
    this.serial = calcSerial(day, month, year);

    this.description = null;

  }

  /**
   * 표준 생성자–넘어온 날짜 번호를 표현하는 새로운 날짜 객체를 생성한다.
   * (범위는 2부터 2958465까지)
   *
   * @param serial 날짜에 대한 직렬 번호(범위는 2부터 2958465까지)
   */
  public SpreadsheetDate(final int serial) {

    if ((serial >= SERIAL_LOWER_BOUND) && (serial <= SERIAL_UPPER_BOUND)) {
      this.serial = serial;
    }
    else {
      throw new IllegalArgumentException(
          "SpreadsheetDate: Serial must be in range 2 to 2958465.");
    }

    // 직렬 번호는 일자-달-연도와 동기화할 필요가 있다.
    calcDayMonthYear();

  }

  /**
   * 날짜에 붙어 있는 설명을 반환한다. 날짜에 설명이 붙을 필요는 없다.
   * 하지만 몇몇 애플리케이션에는 이런 설명이 유용하다.
   *
   *
   * @return 날짜에 붙어있는 설명
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * 날짜에 설명을 붙인다.
   *
   * @param description 이 날짜에 붙일 설명
   *                     (<code>null</code>도 허용)
   */
  public void setDescription(final String description) {
    this.description = description;
  }
  
  /**
   * 날짜에 대한 직렬 번호를 반환한다. 여기서 1900년 1월 1일이 2다
   * (마이크로소프트 엑셀 포 윈도와 로터스 1-2-3에서 사용하는
   * 숫자 시스템에 대응한다).
   *
   * @return 날짜에 대한 직렬 번호
   */
  public int toSerial() {
    return this.serial;
  }

  /**
   * 이 날짜와 동일한 <code>java.util.Date</code>를 반환한다.
   *
   * @return 날짜
   */
  public Date toDate() {
    final Calendar calendar = Calendar.getInstance();
    calendar.set(getYYYY(), getMonth() - 1, getDayOfMonth(), 0, 0, 0);
    return calendar.getTime();
  }

  /**
   * 연도를 반환한다(유효한 범위는 1900년부터 9999년까지를 가정한다).
   *
   * @return 연도
   */
  public int getYYYY() {
    return this.year;
  }

  /**
   * 달을 반환한다(1월 = 1, 2월 = 2, 3월 = 3).
   *
   * @return 연 중 달
   */
  public int getMonth() {
    return this.month;
  }

  /**
   * 월 중 일자를 반환한다.
   *
   * @return 월 중 일자
   */
  public int getDayOfMonth() {
    return this.day;
  }

  /**
   * 주 중 일자(를 표현하는 코드를 반환한다.
   * <P>
   * 이 코드는 {@link SerialDate} 클래스에 다음과 같이 정의되어 있다.
   * <code>SUNDAY</code>, <code>MONDAY</code>, <code>TUESDAY</code>,
   * <code>WEDNESDAY</code>, <code>THURSDAY</code>, <code>FRIDAY</code>, 
   * <code>SATURDAY</code>.
   *
   * @return 주 중 일자를 표현하는 코드
   */
  public int getDayOfWeek() {
    return (this.serial + 6) % 7 + 1;
  }

  /**
   * 이 날짜가 임의 객체와 동등한지 비교한다.
   * <P>
   * 객체가 {@link SerialDate} 기초 클래스의 인스턴스인 경우에만
   * true를 반환한다. 객체는 {@link SpreadsheetDate} 식으로
   * 날짜를 표현한다.
   *
   * @param object 비교 대상 객체(<code>null</code>도 허용)
   *
   * @return 부울값
   */
  public boolean equals(final Object object) {

    if (object instanceof SerialDate) {
      final SerialDate s = (SerialDate) object;
      return (s.toSerial() == this.toSerial());
    }
    else {
      return false;
    }

  }

  /**
   * 이 객체 인스턴스에 대한 해시 코드를 반환한다.
   *
   * @return 해시 코드
   */
  public int hashCode() {
    return toSerial();
  }

  /**
   * 이 날짜와 넘어온 '다른' 날짜 사이의 차이를 (날짜 수로) 반환한다.
   *
   *
   * @param other 비교 대상 날짜
   *
   * @return 이 날짜와 넘어온 '다른' 날짜 사이의 차이
   *         (날짜 수)
   */
  public int compare(final SerialDate other) {
    return this.serial - other.toSerial();
  }

  /**
   * Comparable 인터페이스가 요구하는 메서드를 구현한다.
   *
   * @param other 다른 객체(일반적으로 다른 SerialDate).
   *
   * @return 넘어온 객체보다 작으면 음수, 같으면 0, 크면 양수
   *
   */
  public int compareTo(final Object other) {
    return compare((SerialDate) other);
  }

  /**
   * SerialDate가 넘어온 SerialDate와 동일한 경우 true를 반환한다.
   *
*
* @param other 비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate와 동일한 경우
   *         <code>true</code>
   */
  public boolean isOn(final SerialDate other) {
    return (this.serial == other.toSerial());
  }

  /**
   * 이 SerialDate가 넘어온 SerialDate보다 앞설 경우 true를 반환한다.
   *
   *
   * @param other 비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate보다 앞설 경우
   *         <code>true</code>
   */
  public boolean isBefore(final SerialDate other) {
    return (this.serial < other.toSerial());
  }

  /**
   * 이 SerialDate가 넘어온 SerialDate와 같거나 앞설 경우 true를 반환한다.
   *
   *
   * @param other  비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate과 같거나 앞설 경우
   *         <code>true<code>
   */
  public boolean isOnOrBefore(final SerialDate other) {
    return (this.serial <= other.toSerial());
  }

  /**
   * 이 SerialDate가 넘어온 SerialDate보다 뒤질 경우 true를 반환한다.
   *
   *
   * @param other  비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate보다 뒤질 경우
   *      <code>true</code>
   */
  public boolean isAfter(final SerialDate other) {
    return (this.serial > other.toSerial());
  }

  /**
   * 이 SerialDate가 넘어온 SerialDate와 같거나 뒤질 경우 true를 반환한다.
   *
   *
   * @param other  비교 대상 날짜
   *
   * @return 이 SerialDate가 넘어온 SerialDate와 같거나 뒤질 경우
   *         <code>true</code>
   */
  public boolean isOnOrAfter(final SerialDate other) {
    return (this.serial >= other.toSerial());
  }

  /**
   * 이 {@link SerialDate}가 넘어온 범위 내에 들면
   * <code>true</code>를 반환한다(경계 값 포함).
   * d1과 d2 날짜 순서는 중요하지 않다.
   *
   * @param d1 범위를 결정하기 위한 경계 날짜
   * @param d2 범위를 결정하기 위한 또 다른 경계 날짜
   *
   * @return 부울값
   */
  public boolean isInRange(final SerialDate d1, final SerialDate d2) {
    return isInRange(d1, d2, SerialDate.INCLUDE_BOTH);
  }

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
   * @return 이 SerialDate가 지정된 범위 내에 들어오면
   *         <code>true</code>
   */
  public boolean isInRange(final SerialDate d1, final SerialDate d2,
                           final int include) {
    final int s1 = d1.toSerial();
    final int s2 = d2.toSerial();
    final int start = Math.min(s1, s2);
    final int end = Math.max(s1, s2);

    final int s = toSerial();
    if (include == SerialDate.INCLUDE_BOTH) {
      return (s >= start && s <= end);
    }
    else if (include == SerialDate.INCLUDE_FIRST) {
      return (s >= start && s < end);
    }
    else if (include == SerialDate.INCLUDE_SECOND) {
      return (s > start && s <= end);
    }
    else {
      return (s > start && s < end);
    }
  }

  /**
   * 일자, 달, 연도에서 직렬 번호를 계산한다.
   * <P>
   * 1-Jan-1900 = 2.
   *
   * @param d  일자
   * @param m  달
   * @param y  연도
   *
   * @return 일자, 달, 연도에서 계산한 직렬 번호
   */
  private int calcSerial(final int d, final int m, final int y) {
    final int yy = ((y - 1900) * 365) + SerialDate.leapYearCount(y - 1);
    int mm = SerialDate.AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[m];
    if (m > MonthConstants.FEBRUARY) {
      if (SerialDate.isLeapYear(y)) {
        mm = mm + 1;
      }
    }
    final int dd = d;
    return yy + mm + dd + 1;
  }

  /**
   * 직렬 번호에서 일자, 달, 연도를 계산한다.
   */
  private void calcDayMonthYear() {

    // 직렬 날짜에서 연도를 얻는다.
    final int days = this.serial - SERIAL_LOWER_BOUND;
    // 윤년을 무시했기에 과대 계산되었다.
    final int overestimatedYYYY = 1900 + (days / 365);
    final int leaps = SerialDate.leapYearCount(overestimatedYYYY);
    final int nonleapdays = days - leaps;
    // 연도를 과대 계산했기에 과소 계산되었다.
    int underestimatedYYYY = 1900 + (nonleapdays / 365);

    if (underestimatedYYYY == overestimatedYYYY) {
      this.year = underestimatedYYYY;
    }
    else {
      int ss1 = calcSerial(1, 1, underestimatedYYYY);
      while (ss1 <= this.serial) {
        underestimatedYYYY = underestimatedYYYY + 1;
        ss1 = calcSerial(1, 1, underestimatedYYYY);
      }
      this.year = underestimatedYYYY - 1;
    }

    final int ss2 = calcSerial(1, 1, this.year);

    int[] daysToEndOfPrecedingMonth
      = AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH;

    if (isLeapYear(this.year)) {
      daysToEndOfPrecedingMonth
        = LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH;
    }

      // 직렬 날짜에서 달을 얻는다.
    int mm = 1;
    int sss = ss2 + daysToEndOfPrecedingMonth[mm] - 1;
    while (sss < this.serial) {
      mm = mm + 1;
      sss = ss2 + daysToEndOfPrecedingMonth[mm] - 1;
    }
    this.month = mm - 1;

      // 남은 결과는 d(+1);
    this.day = this.serial - ss2
               - daysToEndOfPrecedingMonth[this.month] + 1;

  }

}