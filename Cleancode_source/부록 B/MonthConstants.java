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
 * -------------------
 * MonthConstants.java
 * -------------------
 * (C) Copyright 2002, 2003, by Object Refinery Limited.
 *
 * 원래 저자: 데이비드 길버트(Object Refinery Limited);
 * 공헌자:   -;
 *
 * $Id: MonthConstants.java,v 1.4 2005/11/16 15:58:40 taqua Exp $
 *
 * 변경 내역
 * -------
 * 29-May-2002 : 버전 1 (SerialDate 클래스에서 옮겨온 코드) (DG);
 *
 */

package org.jfree.date;

/**
 * 달을 위한 유용한 상수. java.util.Calendar에 정의된 상수와 동일하지 않음에
 * 주의하자(java.util.Calendar에서는 JANUARY=0이며 DECEMBER=11이다).
 * <P>
 * SerialDate와 RegularTimePeriod가 사용한다.
 *
 * @author 데이비드 길버트
 */
public interface MonthConstants {

  /** 1월을 위한 상수 */
  public static final int JANUARY = 1;

  /** 2월을 위한 상수 */
  public static final int FEBRUARY = 2;

  /** 3월을 위한 상수 */
  public static final int MARCH = 3;

  /** 4월을 위한 상수 */
  public static final int APRIL = 4;

  /** 5월을 위한 상수 */
  public static final int MAY = 5;

  /** 6월을 위한 상수 */
  public static final int JUNE = 6;

  /** 7월을 위한 상수 */
  public static final int JULY = 7;

  /** 8월을 위한 상수 */
  public static final int AUGUST = 8;

  /** 9월을 위한 상수 */
  public static final int SEPTEMBER = 9;

  /** 10월을 위한 상수 */
  public static final int OCTOBER = 10;

  /** 11월을 위한 상수 */
  public static final int NOVEMBER = 11;

  /** 12월을 위한 상수 */
  public static final int DECEMBER = 12;

}