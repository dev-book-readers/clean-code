# Chapter3.

## 문제가 있는 switch문

```java
public Money calculatePay(Employee e) throws InvalidEmployeeType {
	switch (e.type) { 
		case COMMISSIONED:
			return calculateCommissionedPay(e); 
		case HOURLY:
			return calculateHourlyPay(e); 
		case SALARIED:
			return calculateSalariedPay(e); 
		default:
			throw new InvalidEmployeeType(e.type); 
	}
}
```

위 코드의 문제점

1. 함수가 길다.
    - 새 직원을 추가하면 더 더 더더더ㅓ더더ㅓㅓㅓ 길어진다.
2. ‘한 가지’ 작업만을 수행하지 않는다.
3. Single Responsibility Principle을 위반한다.
4. Open Closed Principle도 위반한다.
    - 이는 새 직원 유형 추가시마다 코드를 변경하기 때문.
5. 가장 큰 문제

```java
isPayday(Employee e, Date date);
// 혹은
deliverPay(Employee e, Money pay);
```

위와 같은 유해한 함수들이 코딩 환경을 오염시킨다. 아름다운 지구를 만들기 위해 코드를 청소하자.

### 청소중

```java
public abstract class Employee {
	public abstract boolean isPayday();
	public abstract Money calculatePay();
	public abstract void deliverPay(Money pay);
}
-----------------
public interface EmployeeFactory {
	public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType; 
}
-----------------
public class EmployeeFactoryImpl implements EmployeeFactory {
	public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType {
		switch (r.type) {
			case COMMISSIONED:
				return new CommissionedEmployee(r) ;
			case HOURLY:
				return new HourlyEmployee(r);
			case SALARIED:
				return new SalariedEmploye(r);
			default:
				throw new InvalidEmployeeType(r.type);
		} 
	}
}
```

- 위의 코드는 추상팩토리를 사용
- 

[[생성 패턴] 추상 팩토리 패턴(Abstract Factory Pattern) 이해 및 예제](https://readystory.tistory.com/119)

- 나온 김에 겸사겸사 위 링크로 추상팩토리도 공부하자.

# 함수의 인수

- 적을수록 좋다.
    - 이상적인 인수는 0개
    - 다음은 1개
    - 다음은 2개
    - 3개는 피해라
    - 4개는 특별할때만 사용해! 라고 하지만, 사실 특별해도 사용하지마.

### 인수를 줄이는 tip

```java
Circle makeCircle(double x, double y, double radius);
Circle make Circle( Point center, double radius);
```

- 함수에 부수적인 기능을 넣지 마라( 제발 하라는 것만 하세요 )
    - 예상치 못하게 클래스 변수를 수정할 수 있다.
    - 함수로 넘어온 인수나, 전역 변수를 수정한다.
    - 시간적인 결합이나 순서 종속성을 초래한다.
    - 부수효과를 일으키는 예시
    - 
    
    ```java
    public class UserValidator {
    	private Cryptographer cryptographer;
    	public boolean checkPassword(String userName, String password) { 
    		User user = UserGateway.findByName(userName);
    		if (user != User.NULL) {
    			String codedPhrase = user.getPhraseEncodedByPassword(); 
    			String phrase = cryptographer.decrypt(codedPhrase, password); 
    			if ("Valid Password".equals(phrase)) {
    				Session.initialize();
    				return true; 
    			}
    		}
    		return false; 
    	}
    }
    ```
    
    - Session.initialize() —> checkPasswordAndInitializeSession()
    - 함수가 1가지 이상의 기능을 하지만, 차라리 수정하는게 훨씬 좋다.

- 일반적으로 출력인수를 사용하지 않는 것이 좋다.
    - 그러라고 this가 나온 것 ( 객체지향 )
    - 함수가 속한 객체의 상태를 변경해야 한다면, 객체의 상태를 변경해줘라.
    
- 명령과 조회를 분리하라.
- 함수는 뭔가를 수행할 때
1. 수행하거나
2. 답하거나
- 하나만 수행해야한다.( 둘 다 하면 스스로 불러온 재앙에 짓눌린다. )

- 오류 처리도 하나의 함수로 만들면 편하다.

```java
public void delete(Page page) {
	try {
		deletePageAndAllReferences(page);
  	} catch (Exception e) {
  		logError(e);
  	}
}
```

```java
private void deletePageAndAllReferences(Page page) throws Exception { 
	deletePage(page);
	registry.deleteReference(page.name); 
	configKeys.deleteKey(page.name.makeKey());
}

private void logError(Exception e) { 
	logger.log(e.getMessage());
}
```

- 반복하지 마라.
    - Codd는 자료의 중복을 제거할 목적으로 관계형 데이터베이스에 정규 형식을 만듬
    - 구조적 프로그래밍, AOP, COP 모두 어떤 면에서는 중복 제거의 전략이다.
    - 소프트웨어 개발에서 일어난 혁신들은 소스 코드의 중복을 제거하려는 지속적 노력