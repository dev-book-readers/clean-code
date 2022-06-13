# 주석
### C1: 부적절한 정보

### C2: 쓸모 없는 주석

### C3: 중복된 주석
코드만으로 충분한데 구구절절 설명하는 주석

### C4: 성의 없는 주석

### C5: 주석 처리된 코드




# 환경

### E1: 여러 단계로 빌드해야한다
한 명령으로 전체를 체크아웃해서 한 명 명령으로 빌드할 수 있어야한다.

### E2: 여러 단계로 테스트해야한다
모든 단위 테스트는 한 명령으로 돌아야한다.




# 함수

### F1: 너무 많은 인수

### F2: 출력 인수

### F3: 플래그 인수
boolean 인수

### F4: 죽은 함수
아무도 호출하지 않는 함수




# 일반

### G1: 한 소스 파일에 여러 언어를 사용한다.

### G2: 당연한 동작을 구현하지 않는다.
독자가 이름을 통해 예상할 수 있는 동작을 구현하라

### G3: 경계를 올바로 처리하지 않는다.
모든 경계와 구석진 코드를 테스트하는 테스트 게이스를 작성하라.

### G4: 안전 절차 무시
컴파일러 경고를 끄지 마라.
실페하는 테스트를 제껴두지 마라.

### G5: 중복
DRY : Don't Repeat Yourself

### G6: 추상화 수준이 올바르지 못하다
모든 저차원 개념은 파생 클래스에 넣고, 모든 고차원 개념은 기초 글래스에 넣는다.  
ex. 세부 구현과 관련한 상수, 변수, 유틸리티 함수는 기초 클래스에 넣으면 안된다. 기초 클래스는 구현 정보에 무지해야마땅하다.  

### G7: 기초 클래스가 파생 클래스에 의존하다.

### G8: 과도한 정보
잘 정의된 인터페이스는 많은 함수를 제공하지 않는다.  
클래스나 모듈 인터페이스가 제공할 함수를 제한해야한다.  
글래스의 인스턴스 변수도 적을수록 좋다.  

### G9: 죽은 코드

### G10: 수직 분리
변수와 함수는 사용되는 위치에 가깝게 정의한다.

### G11: 일관성 부족
어떤 개념을 특정 방식으로 구현했다면, 유사한 개념도 같은 방식으로 구현한다.  
The Principle of least Surprise  

### G12: 잡동사니
아무도 사용하지 않는 변수, 함수, 정보를 제공하지 못하는 주석...

### G13: 인위적인 결합
서로 무관한 개념을 인위적으로 결합하지 않는다.  
ex. 일반적인 enum은 특정 크래스에 속할 이유가 없다.

### G14: 기능 욕심
클래스 메서드는 자기 클래스의 변수와 함수에 관심을 가져야지 다른 클래스의 것을 탐내선 안된다.

### G15: 선택자 인수
boolean 인수(매개변수) 쓰지 말라
true일 때와 false일 때 기능이 달라질 바에는, 함수를 쪼개야한다.

### G16: 모호한 의도

### G17: 잘못 지운 책임
독자에게 직관적인 위치에 변수를 위치시켜라

### G18: 부적절한 static 함수
다음 상황일 때만 static 함수를 사용할 것
1. 특정 객체와 관련이 없으면서,
2. 모든 정보를 인수에서 가져오고,
3. 함수를 재정의할 가능성이 존재하지 않아야한다.

### G19: 서술적 변수
프로그램 가독성을 높이려면, 계산을 여러 단계로 나누고 중간 값으로 서술적인 변수를 사용하라.

### G20: 이름과 기능이 일치하는 함수

### G21: 알고리즘을 이해하라

### G22: 논리적 의존성은 물리적으로 드러내라

### G23: If/Else 혹은 Switch/Case 문 보다 다형성을 사용하라
새 유형을 추가할 확률보다 새 함수를 추가할 확률이 높은 코드에서는 switch 문이 적합하다.  
유형보다 함수가 더 쉽게 변하는 경우는 극히 드물다. 그러므로 모든 switch 문을 의심해야한다.

### G24: 표준 표기법을 따르라

### G25: 매직 숫자는 명명된 상수로 교체하라

### G26: 정확하라
코드에서 뭔가를 결정할 때는 정확히 결정한다.  
결정을 내리는 이유와 예외를 처리할 방법을 분명히 알아야한다.  
ex. 호출하는 함수가 null을 반환할 확률이 희박하더라도 null을 만드시 점검한다.

### G27: 관레보다 구조를 사용하라

### G28: 조건을 캡슐화하라

### G29: 부정 조건은 피하라

### G30: 함수는 한가지만 해야한다

### G31: 시간적 결합을 숨기지 마라

### G32: 일관성을 유지하라

### G33: 경계 조건을 캡슐화하라

### G34: 함수는 추상화 수준을 한 단계만 내려가야한다

### G35: 설정 정보는 최상위 단계에 둬라
```java
public static void main(String[] args) throw Exception {
	Arguments arguments = parseCommandLine(args);
	...
}

public class Arguments{
	public static final String DEFAULT_PATH = ".";
	public static final String DEFAULT_ROOT = "FitNessRoot";
	public static final int DEFAULT_PORT = 80;
	...
}
```

###  G36: 추이적 탐색을 피하라
a.getB().getC(0.doSomething(); X




# JAVA

### J1: 긴 import 목록을 피하고 와일드 카드를 사용하라
import package.*

### J2: 상수는 상속하지 않는다
ex. 상수를 인터페이스에 넣은 다음 그 인터페이스를 상속해 해당 상수를 사용하지 마라. static import를 사용하라.
import static PayrollContants;

### J3: 상수 대 Enum
Enum 사용의 좋은 예! enum 맘껏 사용하라
```java
public class HouryEmployee extends Employee {
	private int tenthsWorked;
	HouryPayGrade grade;
	
	publc Money calculatePay() {
		int straightTime = Math.min(tenthsWorked, TENTHS_PER_WEEK);
		int overTime = tenthsWorked ### straightTime;
		return new Money(
			grade.rate() * (tenthsWorked + OVERTIME_RATE * overTime);
			grade.rate() * (tenthsWorked + OVERTIME_RATE * overTime);
		);
	}
	...
}

publc enum HouryPayGrade {
	APPRENTICE {
		public double rate() {
			return 1.0;
		}
	},
	LIEUTENANT_JOURNEYMAN {
		public double rate() {
			return 1.2;
		}
	},
	JOURNEYMAN {
		public double rate() {
			return 1.5;
		}
	}, 
	MASTER {
		public double rate() {
			return 2.0;
		}
	};
	
	publc abstract double rate();
}
```




# 이름

### N1: 서술적인 이름을 사용하라

### N2: 적절한 추상화 수준에서 이름을 선택하라
구현을 드러내는 이름은 피하라. 쉽지 않은 작업이므로 보일 때마다 수정하라.

### N3: 가능하다면 표준 명명법을 사용하라

### N4: 명확한 이름 

### N5: 긴 범위는 긴 이름을 사용하라
긴 생명주기를 가지는 변수 및 함수는 긴 이름을 사용하라.
생명주기가 짧다면 짧게 지어도 된다. ex. for문의 길이가 5줄 이하라면 i라 쓰는 것이 낫다.

### N6: 인코딩을 피하라
이름에 유형 정보나 범위 정보를 넣지 마라. ex. int m_count

### N7: 이름으로 부수 효과를 설명하라
여러 작업을 수행하는 함수에다 동사 하나만 달랑 적지마라.  
ex. 단순히 oos를 가져오는 함수가 아니라 기존에 oos가 없으면 생성한다면, getOos보다는 createOrReturnOos 이름이 좋다.




# 테스트

### T1: 불충분한 테스트

### T2: 커버리지 도구를 사용하라!
커버리지 도구를 사용해 테스트가 되는 행을 눈으로 확인하라

### T3: 사소한 테스트를 건너뛰지 마라

### T4: 무시한 테스트는 모호함을 뜻한다

### T5: 경계 조건을 테스트하라

### T6: 버그 주변은 철저히 테스트하라
버그는 한곳에 모이는 경향이 있으니, 한 함수에서 버그를 발견했다면 해당 함수를 철저히 테스트하는 편이 좋다.

### T7: 실패 패턴을 살펴라

### T8: 테스트 커버리지 패턴을 살펴라

### T9: 테스트는 빨라야 한다
