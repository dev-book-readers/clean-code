## 결론
깨끗한 코드는 읽기도 좋아야하지만 안정성도 높아야한다.  
오리처리를 프로그램 논리와 부리하여 독립적인 추론이 가능하도록 하라. -> 코드 유지 보수가 편하다.

## 오류 코드보다 예외를 사용하라
비즈니스 알고리즘과 오류룰 처리하는 예외처리를 분리하라

## Try-Catch-Finally 문부터 작성하라
try-catch 구조로 범위를 정의하고, TDD(Test-Driven Development)를 사용해 나머지 논리를 추가한다.  
강제로 예외를 일으키는 테스트 케이스를 작성한 후 테스트를 통과하게 코드를 작성한다. 

## 미확인(unchecked) 예외를 사용하라
- 확인된 예외 : 프로그램의 제어 밖에 있는 예외들. 컴파일 시점에 예외처리 여부를 확인한다  
	- 모든 RuntimeException을 상속하지 않는 예외  
	- ex) FileNotFoundException, SQLException  
  
- 미확인 예외 : 프로그램 로직의 오류로인한 예외들. 런타임 시점에 예외처리 여부를 확인한다  
	- 모든 RuntimeException을 상속하는 예외  
	- ex) ArrayIndexOutOfBoundsException, IllegalArgumentException  
	
확인된 예외응 거의 필요 없다.

## 예외에 의미를 제공하라
오류 메세지에 충분한 정보를 담아 넘겨준다.

## 호출자를 고려해 예외 클래스를 정의하라
호출하는 외부 라이브러리 API를 감싸서 예외 유형을 만든다.

-> 외부 라이브러리오 ㅏ프로그램 사이에서 의존성이 크게 줄어든다.

## 정상 흐름을 정의하라
클래스와 객체가 예외적인 상황을 캡슐화하여 처리하도록 한다.  

특수 사례 페턴
```java
try {
	MealExpenses expenses= expenseReportDAO.getmeals(employee.getID());
	m_total += expenses.getTotal();
} catch(MealExpensesNotFound e) {
	m_totla += getMealPerDiem();
}
```

```java
MealExpenses expenses= expenseReportDAO.getmeals(employee.getID());
m_total += expenses.getTotal();
...
public class PerDiemMealExpenses implements MealExpenses {
	public int getTotal() {
		// 기본 값으로 일일 기본 식비를 반환한다.
	}
}
```

## null을 반환하지 마라
특수 사례 객체로 처리하라.  
ex. 리스트를 반환해야 한다면, null 을 반환하지 말고 빈 리스트(Collections.emptyList()) 반환하라.

## null을 전달하지 마라
대다수의 프로그래밍 언어는 호출자가 실수로 넘기는 null을 적절히 처리하는 방법이 없다.  
애초에 null이 넘어오지 않도록 코드를 작성하라.
