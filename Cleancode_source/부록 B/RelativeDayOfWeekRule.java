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
 * --------------------------
 * RelativeDayOfWeekRule.java
 * --------------------------
 * (C) Copyright 2000-2003, by Object Refinery Limited and Contributors.
 *
 * 원래 저자: 데이비드 길버트(Object Refinery Limited);
 * 공헌자:   -;
 *
 * $Id: RelativeDayOfWeekRule.java,v 1.6 2005/11/16 15:58:40 taqua Exp $
 *
 * 변경 내역(26-Oct-2001부터)
 * --------------------------
 * 26-Oct-2001 : 패키지 이름을 com.jrefinery.date.*으로 변경했다;
 * 03-Oct-2002 : Checkstyle이 보고한 오류를 수정했다 (DG);
 *
 */

package org.jfree.date;

/**
 * 매년 날짜를 반환하는 연간 날짜 규칙: 다음에 기반한다.
 * (a) 참조 규칙 (b) 주 중 일자, (c) 선택한 매개변수
 * (SerialDate.PRECEDING, SerialDate.NEAREST, SerialDate.FOLLOWING).
 * <P>
 * 예를 들어, 성 금요일('Good Friday')는 부활절 전의 금요일로
 * ('Friday PRECEDING EasterSunday') 명세가 가능하다.
 *
 * @author 데이비드 길버트
 */
public class RelativeDayOfWeekRule extends AnnualDateRule {

  /** 이 규칙이 기반하는 연간 날짜 규칙에 대한 참조 */
  private AnnualDateRule subrule;

  /**
   * 주 중 일자(SerialDate.MONDAY, SerialDate.TUESDAY 등등).
   */
  private int dayOfWeek;

  /** 주중 어떤 일자인지 명세(PRECEDING, NEAREST or FOLLOWING). */
  private int relative;

  /**
   * 기본 생성자–1월 1일에 따라오는 월요일을 위한 규칙을 생성한다.
   */
  public RelativeDayOfWeekRule() {
    this(new DayAndMonthRule(), SerialDate.MONDAY, SerialDate.FOLLOWING);
  }

  /**
   * 표준 생성자–넘어온 하위 규칙에 기반해서 규칙을 생성한다.
   *
   * @param subrule 참조 날짜를 결정하는 규칙
   * @param dayOfWeek 참조 날짜에 상대적인 주 중 일자
   * @param relative 주중 어떤 일자인지 지정(SerialDate.PRECEDING,
   *                  SerialDate.NEAREST, SerialDate.FOLLOWING).
   */
  public RelativeDayOfWeekRule(final AnnualDateRule subrule,
                               final int dayOfWeek, final int relative) {
    this.subrule = subrule;
    this.dayOfWeek = dayOfWeek;
    this.relative = relative;
  }

  /**
   * 하위 규칙을 반환한다(참조 규칙이라고도 한다).
   *
   * @return 이 규칙에 대한 참조 날짜를 결정하는 연간 날짜 규칙
   *
   */
  public AnnualDateRule getSubrule() {
    return this.subrule;
  }

  /**
   * 하위 규칙을 설정한다.
   *
   * @param subrule 이 규칙에 대한 참조 날짜를 결정하는 연간 날짜 규칙
   *
   */
  public void setSubrule(final AnnualDateRule subrule) {
    this.subrule = subrule;
  }

  /**
   * 이 규칙에 대한 주 중 일자를 반환한다.
   *
   * @return 이 규칙에 대한 주 중 일자
   */
  public int getDayOfWeek() {
    return this.dayOfWeek;
  }

  /**
   * 이 규칙에 대한 주 중 일자를 설정한다.
   *
   * @param dayOfWeek 주 중 일자 (SerialDate.MONDAY,
   *                   SerialDate.TUESDAY 등등).
   */
  public void setDayOfWeek(final int dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  /**
   * '상대' 속성을 반환한다. 이 속성은 관심 있는 주 중 일자를 결정한다
   * (SerialDate.PRECEDING,
   * SerialDate.NEAREST, SerialDate.FOLLOWING).
   *
   * @return '상대' 속성
   */
  public int getRelative() {
    return this.relative;
  }

  /**
   * '상대' 속성을 설정한다(SerialDate.PRECEDING, SerialDate.NEAREST,
   * SerialDate.FOLLOWING).
   *
   * @param relative 이 규칙이 선택한 주 중 일자가 무엇인지 결정한다.
   *
   */
  public void setRelative(final int relative) {
    this.relative = relative;
  }

  /**
   * 이 규칙의 복제물을 생성한다.
   *
   * @return 이 규칙의 복제물
   *
   * @throws CloneNotSupportedException 이 예외는 결코 발생해서는 안 된다.
   */
  public Object clone() throws CloneNotSupportedException {
    final RelativeDayOfWeekRule duplicate
      = (RelativeDayOfWeekRule) super.clone();
    duplicate.subrule = (AnnualDateRule) duplicate.getSubrule().clone();
    return duplicate;
  }

  /**
   * 지정된 연도에 맞춰 이 규칙이 생성한 날짜를 반환한다.
   *
   * @param year 연도(1900 &lt;= year &lt;= 9999).
   *
   * @return 지정된 연도에 맞춰 생성한 날짜 (
   *         <code>null</code>도 가능한 값이다)
   */
  public SerialDate getDate(final int year) {

    // 인수 점검...
    if ((year < SerialDate.MINIMUM_YEAR_SUPPORTED)
        || (year > SerialDate.MAXIMUM_YEAR_SUPPORTED)) {
      throw new IllegalArgumentException(
          "RelativeDayOfWeekRule.getDate(): year outside valid range.");
  }

    // 날짜 계산...
    SerialDate result = null;
    final SerialDate base = this.subrule.getDate(year);
    
    if (base != null) {
      switch (this.relative) {
        case(SerialDate.PRECEDING):
          result = SerialDate.getPreviousDayOfWeek(this.dayOfWeek,
                                                   base);
        break;
        case(SerialDate.NEAREST):
          result = SerialDate.getNearestDayOfWeek(this.dayOfWeek,
                                                  base);
        break;
        case(SerialDate.FOLLOWING):
          result = SerialDate.getFollowingDayOfWeek(this.dayOfWeek,
                                                    base);
        break;
        default:
          break;
      }
    }
    return result;

  }

}