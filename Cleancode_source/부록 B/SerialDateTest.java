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
 * SerialDateTests.java
 * --------------------
 * (C) Copyright 2001-2005, by Object Refinery Limited.
 *
 * 원래 저자: 데이비드 길버트(Object Refinery Limited);
 * 공헌자:   -;
 *
 * $Id: SerialDateTests.java,v 1.6 2005/11/16 15:58:40 taqua Exp $
 *
 * 변경 내역
 * -------
 * 15-Nov-2001 : 버전 1 (DG);
 * 25-Jun-2002 : 불필요한 import를 제거했다 (DG);
 * 24-Oct-2002 : Checkstyle이 보고한 오류를 수정했다 (DG);
 * 13-Mar-2003 : 직렬화 테스트를 추가했다 (DG);
 * 05-Jan-2005 : 버그 보고 1096282를 위한 테스트를 추가했다 (DG);
 *
 */

package org.jfree.date.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.date.MonthConstants;
import org.jfree.date.SerialDate;

/**
* {@link SerialDate} 클래스를 위한 몇몇 JUnit 테스트
*/
public class SerialDateTests extends TestCase {

  /** 11월 9일을 표현하는 날짜 */
  private SerialDate nov9Y2001;

  /**
   * 새로운 테스트 케이스를 생성한다.
   *
   * @param name 이름
   */
  public SerialDateTests(final String name) {
    super(name);
  }

  /**
   * JUnit 테스트 러너를 위한 테스트 슈트를 반환한다.
   *
   * @return 테스트 슈트
   */
  public static Test suite() {
    return new TestSuite(SerialDateTests.class);
  }

  /**
   * 문제 설정
   */
  protected void setUp() {
    this.nov9Y2001 = SerialDate.createInstance(9, MonthConstants.NOVEMBER, 2001);
  }

  /**
   * 2001년 11월 9일에 두 달을 더하면 2002년 1월 8일이 되어야 한다.
   */
  public void testAddMonthsTo9Nov2001() {
    final SerialDate jan9Y2002 = SerialDate.addMonths(2, this.nov9Y2001);
    final SerialDate answer = SerialDate.createInstance(9, 1, 2002);
    assertEquals(answer, jan9Y2002);
  }

  /**
   * 보고된 버그를 위한 테스트 케이스로 현재 수정된 상황이다.
   */
  public void testAddMonthsTo5Oct2003() {
    final SerialDate d1 = SerialDate.createInstance(5, MonthConstants.OCTOBER, 2003);
    final SerialDate d2 = SerialDate.addMonths(2, d1);
    assertEquals(d2, SerialDate.createInstance(5, MonthConstants.DECEMBER, 2003));
  }

  /**
   * 보고된 버그를 위한 테스트 케이스로 현재 수정된 상황이다.
   */
  public void testAddMonthsTo1Jan2003() {
    final SerialDate d1 = SerialDate.createInstance(1, MonthConstants.JANUARY, 2003);
    final SerialDate d2 = SerialDate.addMonths(0, d1);
    assertEquals(d2, d1);
  }

  /**
   * 2001년 11월 9일 금요일 앞에 나오는 월요일은 11월 5일이 되어야 한다.
   */
  public void testMondayPrecedingFriday9Nov2001() {
    SerialDate mondayBefore = SerialDate.getPreviousDayOfWeek(
        SerialDate.MONDAY, this.nov9Y2001
    );
    assertEquals(5, mondayBefore.getDayOfMonth());
  }

  /**
   * 2001년 11월 8일 금요일에 뒤에 나오는 월요일은 11월 12일이 되어야 한다.
   */
  public void testMondayFollowingFriday9Nov2001() {
    SerialDate mondayAfter = SerialDate.getFollowingDayOfWeek(
        SerialDate.MONDAY, this.nov9Y2001
    );
    assertEquals(12, mondayAfter.getDayOfMonth());
  }

  /**
   * 2001년 11월 8일 금요일에 가장 가까운 월요일은 11월 12일이 되어야 한다.
   */
  public void testMondayNearestFriday9Nov2001() {
    SerialDate mondayNearest = SerialDate.getNearestDayOfWeek(
        SerialDate.MONDAY, this.nov9Y2001
    );
    assertEquals(12, mondayNearest.getDayOfMonth());
  }

  /**
   * 1970년 1월 22일에 가장 근접한 월요일은 19일이다.
   */
  public void testMondayNearest22Jan1970() {
    SerialDate jan22Y1970 = SerialDate.createInstance(22, MonthConstants.JANUARY, 1970);
    SerialDate mondayNearest=SerialDate.getNearestDayOfWeek(SerialDate.MONDAY, jan22Y1970);
    assertEquals(19, mondayNearest.getDayOfMonth());
  }

  /**
   * 날짜를 문자열로 변환해 올바른 결과를 반환하는지 점검한다.
   * 실제로, 이 결과는 로케일에 의존하므로 수정할 필요가 있다.
   */
  public void testWeekdayCodeToString() {

    final String test = SerialDate.weekdayCodeToString(SerialDate.SATURDAY);
    assertEquals("Saturday", test);

  }

  /**
   * 문자열을 상수(주)로 변환하는 테스트를 수행한다. 이 테스트는 기본 로케일이
   * 영어 요일 이름을 사용하지 않을 경우 실패한다. 더 나은 테스트가 필요하다!
   */
  public void testStringToWeekday() {

    int weekday = SerialDate.stringToWeekdayCode("Wednesday");
    assertEquals(SerialDate.WEDNESDAY, weekday);

    weekday = SerialDate.stringToWeekdayCode(" Wednesday ");
    assertEquals(SerialDate.WEDNESDAY, weekday);

    weekday = SerialDate.stringToWeekdayCode("Wed");
    assertEquals(SerialDate.WEDNESDAY, weekday);

  }

  /**
   * 문자열을 상수(월)로 변환하는 테스트를 수행한다. 이 테스트는 기본 로케일이
   * 영어 월 이름을 사용하지 않을 경우 실패한다. 더 나은 테스트가 필요하다!
   */
  public void testStringToMonthCode() {

    int m = SerialDate.stringToMonthCode("January");
    assertEquals(MonthConstants.JANUARY, m);

    m = SerialDate.stringToMonthCode(" January ");
    assertEquals(MonthConstants.JANUARY, m);

    m = SerialDate.stringToMonthCode("Jan");
    assertEquals(MonthConstants.JANUARY, m);

  }

  /**
   * 월 상수를 문자열로 바꾸는 테스트를 수행한다.
   */
  public void testMonthCodeToStringCode() {

    final String test = SerialDate.monthCodeToString(MonthConstants.DECEMBER);
    assertEquals("December", test);

  }

  /**
   * 1900년은 윤년이 아니다.
   */
  public void testIsNotLeapYear1900() {
    assertTrue(!SerialDate.isLeapYear(1900));
  }

  /**
   * 2000년은 윤년이다.
   */
  public void testIsLeapYear2000() {
    assertTrue(SerialDate.isLeapYear(2000));
  }

  /**
   * 1900년부터 1899년(포함)까지 윤년 횟수는 0이다.
   */
  public void testLeapYearCount1899() {
    assertEquals(SerialDate.leapYearCount(1899), 0);
  }

  /**
   * 1900년부터 1903년(포함)까지 윤년 횟수는 0이다.
   */
  public void testLeapYearCount1903() {
    assertEquals(SerialDate.leapYearCount(1903), 0);
  }

  /**
   * 1900년부터 1904년(포함)까지 윤년 횟수는 1이다.
*/
  public void testLeapYearCount1904() {
    assertEquals(SerialDate.leapYearCount(1904), 1);
  }

  /**
   * 1900년부터 1999년(포함)까지 윤년 횟수는 24이다.
   */
  public void testLeapYearCount1999() {
    assertEquals(SerialDate.leapYearCount(1999), 24);
  }

  /**
   * 1900년부터 2000년(포함)까지 윤년 횟수는 25이다.
   */
  public void testLeapYearCount2000() {
    assertEquals(SerialDate.leapYearCount(2000), 25);
  }

  /**
   * 인스턴스를 직렬화해 복원하고 인스턴스가 같은지 점검한다.
   */
  public void testSerialization() {

    SerialDate d1 = SerialDate.createInstance(15, 4, 2000);
    SerialDate d2 = null;

    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      ObjectOutput out = new ObjectOutputStream(buffer);
      out.writeObject(d1);
      out.close();

      ObjectInput in = new ObjectInputStream(
          new ByteArrayInputStream(buffer.toByteArray()));
      d2 = (SerialDate) in.readObject();
      in.close();
    }
    catch (Exception e) {
      System.out.println(e.toString());
    }
    assertEquals(d1, d2);

  }
  
  /**
   * 버그 보고서 1096282를 위한 테스트(현재 수정된 상황)
   */
  public void test1096282() {
    SerialDate d = SerialDate.createInstance(29, 2, 2004);
    d = SerialDate.addYears(1, d);
    SerialDate expected = SerialDate.createInstance(28, 2, 2005);
    assertTrue(d.isOn(expected));
  }

  /**
   * addMonths()를 위한 기타 테스트
   */
  public void testAddMonths() {
    SerialDate d1 = SerialDate.createInstance(31, 5, 2004);

    SerialDate d2 = SerialDate.addMonths(1, d1);
    assertEquals(30, d2.getDayOfMonth());
    assertEquals(6, d2.getMonth());
    assertEquals(2004, d2.getYYYY());

    SerialDate d3 = SerialDate.addMonths(2, d1);
    assertEquals(31, d3.getDayOfMonth());
    assertEquals(7, d3.getMonth());
    assertEquals(2004, d3.getYYYY());

    SerialDate d4 = SerialDate.addMonths(1, SerialDate.addMonths(1, d1));
    assertEquals(30, d4.getDayOfMonth());
    assertEquals(7, d4.getMonth());
    assertEquals(2004, d4.getYYYY());
  }
}