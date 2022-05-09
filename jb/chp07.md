# Chp.07 : 오류 처리

여기저기에 흩어진 오류 처리 코드로 인해 프로그램의 논리를 이해하기 어려워진다면 클린 코드라 부를 수 없다.

## **[ 오류 코드보다 예외를 사용하라 ]**

매번 함수 호출 즉시 logger.log()로 오류 확인하기 보다, 오류가 발생하면 예외를 던지는게 논리와 오류 처리 코드가 섞이지 않으므로 깔끔하다.

```java
// 전
public class DeviceController{
	...
	public void sendShutDown(){
		DeviceHandle handle = getHandle(DEV1);
		if(handle != DeviceHandle.INVALID){
			retrieveDeviceRecord(handle);
			if(record.getStatus() != DEVICE_SUSPENDED){
				pauseDevice(handle);
				clearDeviceWorkQueue(handle);
				closeDevice(handle);
			} else {
				logger.log("Device suspended. Unable to shut down.");
			}
		} else {
			logger.log("Invalid handle for: " + DEV1.toString());
		}
	}
	...
}

// ---------------------------------------------------------------

// 후 : Device 종료, 오류 처리 알고리즘을 분리
public class DeviceController{
	...
	public void sendShutDown(){
		try{
			tryToShutDown();
		} catch(){
			logger.log(e);
		}
	}

	private void tryToShutDown() throws DeviceShutDownError {
		DeviceHandle handle = getHandle(DEV1);
		DeviceRecord record = retrieveDeviceRecord(handle);
		
		pauseDevice(handle);
		clearDeviceWorkQueue(handle);
		closeDevice(handle);
	}

	private DeviceHandle getHandle(DeviceID id) {
		throw new DeviceShutDownError("Invalid handle for : " + id.toString());
		...
	}
	...
}
```

## **[ Try-Catch-Finally 문부터 작성하라 ]**

- try 블록은 transaction과 비슷하게 **프로그램 안에다가 범위를 정의**하는 개념
- try() 안에서 무슨 일이 생기면 catch()가 프로그램의 일관성을 유지시켜준다. 그래서 예외가 발생할 것 같은 코드 작성 시 try-catch-finally문으로 시작하자.
- 강제로 예외를 일으키는 test case 작성한 후, 테스트를 통과하도록 코드 작성하는 방법이 좋음 → try 블록 범위 내에서 성공적인 transaction을 유지하기 쉬워짐

## **[ 미확인(unchecked) 예외를 사용하라 ]**

- 예전에는 메서드 선언 시, 예외를 메서드의 일부로 취급하고 해당 메서드가 반환할 예외를 모두 나열함
- “확인된 예외” 단점 : 지금은 안정성을 중요시 함
    - 확인된 예외는 ***OCP(Open Closed Principle)**을 위반한다.

      하위 단계에서 코드 변경 시 상위 메서드 선언부를 전부 고쳐야한다. 즉, 모듈 관련 코드가 전혀 바뀌지 않았음에도 불구하고 선언부가 바뀌었으므로 모듈을 다시 빌드한 후 배포해야한다.

    - 예시) 최상위 함수가 하위 함수 호출 > 그 안에서 또 하위 함수 호출 > ... > 최하위 함수를 변경 (throws new error)

      변경한 최하위 함수를 호출하는 모든 함수들이 최하위 함수에서 던지는 예외를 알아야하므로 캡슐화가 깨지고 모든 함수들을 번거롭게 수정해야함.

        1. catch 블록에서 새로운 예외를 처리
        2. 선언부에 throw 절 추가
- “확인된 예외” 장점 : 중요한 라이브러리 작성 시, 모든 예외를 잡아야함. BUT 이런 의존성은 좋지 않음.<br><br>

***FYI - OCP**

- 새로운 Logic을 추가할 때마다 변경을 최소화하지 않으면 다시 컴파일 한 다음, 다시 배포해야 함
- 해결 방법 : OCP 규칙 적용하기
    - OCP (Open Closed Principle)
      : 기존의 코드를 변경하지 않으면서 기능을 추가할 수 있도록 설계되어야 한다.
- [https://nesoy.github.io/articles/2018-01/OCP](https://nesoy.github.io/articles/2018-01/OCP)

## **[ 예외에 의미를 제공하라 ]**

1. 코드가 예외를 던질 때 호출 스택 사용하여 실패한 이유를 파악하기

   근데 호출 스택 만으로는 실패 이유를 파악하기 부족할 수 있음.

2. logging 기능 사용

   → catch 블록 내에서 오류 메세지에 정보를 담아 예외와 함께 전달 (실패 연산명, 실패 유형 등)


## **[ 호출자를 고려해 예외 클래스를 정의하라 ]**

- 오류가 발생한 위치에(컴포넌트) 따라 오류를 분류할 수 있는데, **오류를 잡아내는 방법**이 중요함
    - 오류 발생 컴포넌트 / 오류 유형 : 네트워크 실패, 프로그래밍 오류 등
- catch()를 연달아 작성하면서 일일이 예외를 처리하기보다, 여러 catch 블록에서 공통으로 호출하는 라이브러리 API를 감싸고 예외 유형 1개만 반환하는게 더 효율적 :

    ```java
    // p.135-136 참고
    
    // LocalPort class: ACMEPort 클래스가 던지는 예외를 잡아서 변환하는 감싸기(wrapper) 클래스.
    LocalPort port = new LocalPort(12);
    
    try{
    	port.open();
    } catch (PortDeviceFailure e) {
    	reportError(e);
    	logger.log(e.getMessage(), e);
    } finally { 
    	... 
    }
    ``` 


***FYI  - Wrapper class (감싸기 클래스)**

- Wrapper class로 외부 API를 감싸면 외부 라이브러리, 프로그램에 대한 의존성 ⬇️
- Wrapper class에서 외부 API 호출하는 대신 테스트 코드를 넣어줘서 프로그램 테스트 할 수 있음
- Wrapper class 사용 시, 프로그램 중심으로 편리한 API를 정의할 수 있음 (특정 업체가 설계한 API 방식에 한정되지 않음)

## **[ 정상 흐름을 정의하라 ]**

- 외부 API를 감싸서 독자적인 예외를 던지고, 코드 위에 처리기를 정의해서 중단된 계산을 처리한다. BUT 때로는 중단이 적합하지 않을 때도 있다.
- 특수 사례 패턴 (Special Case Pattern) : class를 만들거나 객체를 조작하여 특수 사례를 처리하는 방식
    - class, 객체가 예외 상황들을 캡슐화하여 처리하므로 코드 내부에서 예외를 직접 처리할 필요X
    - 예시) 총 비용을 계산하는 코드

```java
// 전 : 식비를 비용으로 청구하지 않은 경우 일일 기본 식비를 총계에 더하는, 특수 상황 발생
try {
	MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
	m_total += expenses.getTotal();
} catch(MealExpensesNotFound e) {
	m_total += getMealPerDiem();
}

// -----------------------------------------

// 후 : 청구한 식비가 없을 때 MealExpense 객체가 일일 기본 식비를 반환
MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
m_total += expenses.getTotal();

public class PerDiemMealExpenses implements MealExpenses {
	public int getTotal() {
		// 기본값으로 일일 기본 식비를 반환
	}
}
```

## **[ null을 반환하지 마라 ]**

- null을 반환 → 처리할 일이 많아지고, 호출자에게 문제를 떠넘김 (NullPointerException 발생 가능!)
- null을 반환하는 대신 아래를 수행할 수 있음 :
    - 예외를 던지기
    - 특수 사례 객체를 반환
    - 외부 API를 감싸는 메서드 구현한 다음, 예외 던지거나 특수 사례 객체 반환


```java
// Java에서 list에 대해 null 체크할 때, 차라리 empty list를 반환해야 
// NullPointerException 발생 가능성이 줄어듦.

// 방법#1
List<Employee> employees = getEmployees();
for(Employee e : employees){
	totalPay += e.getPay();
}

// 방법#2
// Java에서 읽기 전용 리스트를 반환하는 Collections.emptyList() 제공함!
public List<Employee> getEmployees() {
	if(/* 직원이 없다면 */)
		return Collections.emptyList();
}
```

## **[ null을 전달하지 마라 ]**

- 정상적인 인수로 null을 기대하는 API가 아니라면, 메서드로 null을 전달하지 말기.
- 다양한 방식으로 null, 예상 못한 인수값 type을 처리할 수는 있지만, **호출자가 실수로 넘기는 null 값을 100% 처리할 방법은 없음**.
- 아래 예시 ) assert문 사용함 BUT 누군가가 Point p1, p2 값으로 null을 전달하면 여전히 실행 오류 발생

```java
// assert문 사용하여 NullPointerException, InvalidArgumentException 처리
// 두 지점 사이의 거리를 계산하는 간단한 메서드
public class MetricsCalculator{
	public double xProjectioin(Point p1, Point p2){
		assert p1 != null : "p1 should not be null";
		assert p2 != null : "p2 should not be null";
		return (p2.x - p1.x) * 1.5;
	}
}
```

## **[ 결론 ]**

- 클린코드 = 가독성 좋아야함 = 안정성 높아야함
- 오류 처리와 프로그램의 논리를 분리시켜서 독립적인 코드로 만들고 유지보수성을 높이자