# Chp.03 : 함수

[ block, indent ]

- if/else/while문 안에 들어가는 블록은 1줄로 충분함 (함수 호출)
- 중첩 구조가 생길만큼 함수가 커지면 안됨

> *함수는 한 가지를 해야한다. 그 한 가지를 잘 해야한다. 그 한 가지만을 해야 한다.*
>

[ 함수 당 추상화 1개 ]

- 함수가 확실히 1가지 작업만 하려면 함수 내 모든 문장의 추상화 수준이 동일해야 함
- 코드를 위에서 아래로 읽기 (내려가기 규칙)

  : 위에서 아래로 프로그램을 읽으면 함수 추상화 수준이 한 번에 한 단계씩 낮아짐


[ switch문 ]

- switch문 대신 단일 블록이나 함수 사용하는게 나음 b/c 1가지 작업만 하는 switch문을 만들기 어려움
    - switch문은 n개의 case 분기를 다룸 = n가지 일을 처리함

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

    - 문제점
        - 함수가 길다 : 새로운 직원 추가 시 함수가 더 길어짐
        - ‘한 가지’ 작업만 수행하지 않는다
        - SRP(Single Responsibility Principle) 위반 : 코드를 변경할 이유가 여럿 존재
        - OCP(Open Closed Principle) 위반 : 새로운 유형의 직원을 추가할 때마다 코드를 변경해야 함
        - calculatePay()와 구조가 동일한 함수가 존재할 가능성이 있음

            ```java
            // 구조가 동일한 함수들 
            isPayday(Employee e, Date date);
            deliverPay(Employee e, Money pay);
            ```


    - 해결책 : 다형성(polymorphism)
    
    ```java
    /*
    올바른 작성 순서
    1. 다형적 객체 생성하는 코드 안에 switch문 작성
    2. 상속 관계로 숨김
    3. 절대 다른 코드에 노출X
    */
    
    public abstract class Employee {
    	public abstract boolean isPayday();
    	public abstract Money calculatePay();
    	public abstract void deliverPay(Money pay);
    }
    ...
    public interface EmployeeFactory {
    	public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType;
    }
    ...
    public class EmployeeFactoryImpl implements EmployeeFactory {
    	public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType {
    		switch (r.type){
    			case COMMISSIONED: 
    				return calculateCommissionedPay(r);
    			case HOURLY: 
    				return calculateHourlyPay(r);
    			case SALARIED:
    				return calculateSalariedPay(r);
    			default:
    				throw new InvalidEmployeeType(r.type);
    		}
    	}
    }
    ```


[ 서술적인 이름 사용 ]

- 함수가 작고 단순할수록 서술적인 이름을 고르기가 쉬워짐
- 함수 이름 정할 때 :
    - 길고 서술적인 이름 >> 짧고 어려운 이름
    - 여러 단어가 쉽게 읽히는 명명법 사용
    - 함수 기능을 잘 표현하는 이름 사용
- 서술적인 이름 → 설계가 뚜렷해지므로 코드 개선하기 쉬워짐

[ 함수의 인수 ]

- 인수 개수는 적을수록 좋음 (이상적인 개수 : 0개)
- 인수 개수가 많아질수록 이해하기 어려워짐
- 많이 쓰는 단항 형식
    1. 인수에 질문을 던지는 경우

        ```java
        // 예시
        boolean fileExists("filename")
        ```

    2. 인수를 뭔가로 변환해 결과를 반환하는 경우

        ```java
        // 예시 : String형의 파일 이름을 InputStream으로 변환
        InputStream fileOpen("filename")
        ```

    3. 이벤트 함수 호출하는 경우
        - 주의 : 이벤트 함수임을 코드에 명확히 드러나야 함

        ```java
        // 예시 : 함수 호출을 이벤트로 해석해 입력 인수로 시스템 상태를 바꿈 (입력 인수만 받고 출력은 없음)
        passwordAttemptFailedNtimes(int attempts)
        ```

- 플래그 인수
    - return boolean type 금지!
        - 이유 : 함수가 1가지 일을 처리하는게 아님 (true일 때, false일 때 각각 다른 함수 호출)

        ```java
        render(true) -> renderForSuite() , renderForSingleTest() 2개 함수로 나눠서 작성
        ```


- 이항 함수
    - 되도록 단항 함수로 바꾸는게 좋음
    - 많이 쓰는 이항 형식
        1. 직교 좌표계 점은 일반적으로 인수가 2개 (x, y좌표 요소들이 값 1개를 표현하므로 허용됨)
        2. 예상값, 실제값을 인수로 갖는 경우 (expected, actual)

- 인수 객체
    - 인수가 여러개일 경우 일부를 클래스 변수로 선언할 가능성이 있음

    ```java
    // 변경 전 (삼항)
    Circle makeCircle(double x, double y, double radius);
    
    // 변경 후 (이항) : center 객체 생성하여 인수를 줄임
    Circle makeCircle(Point center, double radius);
    ```


- 동사와 키워드
    - 단항 함수 - 함수, 인수가 동사/명사 쌍을 이뤄야 함

        ```java
        // name이 field임을 확실히 알 수 있음
        write(name) -> writeField(name)
        ```

    - 함수 이름에 키워드(인수 이름) 추가

        ```java
        // 인수의 순서를 기억할 필요가 없어짐
        assertEquals -> assertExpectedEqualsActual(expected, actual)
        ```


[ 부수적인 효과 넣지말기 ]

```java
public class UserValidator {
	private Cryptographer cryptographer;

	public boolean checkPassword(String userName, String password){
		User user = UserGateway.getPhraseEncodedByPassword();
		String phrase = cryptographer.decrypt(codedPhrase, password);
		
		if("Valid".equals(phrase)){
			Session.initialize();
			return true;
		}
	}
	return false;
}
```

- 문제점 : 보통 checkPassword() 호출 시 계정 비번 체크만 하는줄 알지, 중간에 부수적으로 세션을 초기화(Session.initialize()) 한다는 사실은 드러나지 않음

  → 잘못 호출 시 사용자를 인증하면서 세션 정보를 지워버릴 위험 존재

- 해결책 : 메서드 이름에 Session, initialize 키워드 추가하기

    ```java
    checkPassword() -> checkPasswordAndInitializeSession()
    ```

- 출력 인수
    - 일반적으로 출력 인수는 피하자 (객체 지향 언어에서는 출력 인수가 필요 없음)
    - 함수에서 상태를 변경해야 한다면 함수가 속한 객체 상태를 변경하는 방식을 선택

[ 명령과 조회를 분리 ]

- 명령 : 객체 상태를 변경
- 조회 : 객체 정보를 반환

```java
// 목표 : 이름이 attribute인 속성의 값을 value로 설정한 후 성공하면 true, 실패하면 false 반환

/* 
변경 전
if(set())의 의미가 불분명함
1. 명령) username=unclebob으로 설정 
2. 조회-1) username=unclebob이라면?
   조회-2) username을 unclebob으로 설정하는 데에 성공했다면?
*/
public boolean set(String attribute, String value);
...
if(set("username", "unclebob"))...

// 변경 후
if(attributeExists("username")){
	setAttribute("username", "unclebob");
	...
}
```

[ 오류 코드보다 예외를 사용하기 ]

- 예외 사용 시 오류 처리 코드가 원래 코드에서 분리되므로 코드가 깔끔해짐

```java
// 오류 코드
if(...){
	logger.log("page deleted");
} else {
	logger.log("delete failed"); return E_ERROR;
}

// 예외 코드
try {
	deletePage(page);
	...
} catch (Exception e) {
	logger.log(e.getMessage());
}
```

- Try/Catch 블록 별도 함수로 뽑아내기
    - delete() : 모든 오류를 처리하는 동작
    - deletePageAndAllReferences() : 예외 처리를 하지 않음 (실제 page를 삭제하는 정상 동작)

```java
public void delete(Page page) {
	try{
		deletePageAndAllReferences(page);
	} catch (Exception e){
		logError(e);
	}
}

private void deletePageAndAllReferences(Page page) throws Exception {
	deletePage(page);
	...
}

private void logError(Exception e) {
	logger.log(e.getMessage());
}
```

- 오류 처리도 한 가지 작업으로 생각하기
- [Error.java](http://Error.java) 의존성 자석
    - “오류 코드를 반환한다” == “어디선가 오류 코드를 정의한다”
    - 프로그램머는 재컴파일/재배치가 번거롭기에 새 오류 코드를 정의하기보다 기존 오류 코드를 재사용함
    - 오류 코드 대신 예외를 사용 시 새 예외는 Exception class에서 파생되므로 재컴파일/재배치 없이도 새 예외클래스를 추가할 수 있음

[ 코드 반복 금지 ]

- 객체 지향 프로그래밍은 코드를 부모 클래스로 몰아 넣어서 중복을 피함

[ 구조적 프로그래밍 ]

- 함수는 입구, 출구가 1개씩 존재 → return문 1개
- 함수가 큰 경우 : loop 안에서 break, continue, goto 사용 금지.
- 함수가 작은 경우 : 간혹 return, break, continue 여러 번 사용 가능. goto 사용 금지.

[ 함수 만드는 방법 ]

- 코드 초안 작성 완료 > 코드 모든 부분을 빠짐없이 테스트하는 단위 테스트 케이스를 만듦 > 코드를 다듬고, 함수 만들고, 이름 변경, 중복 제거, 메서드 개수 줄이고 순서 변경, 전체 클래스 쪼개기 등 (+ 항상 단위 테스트를 통과해야 함)