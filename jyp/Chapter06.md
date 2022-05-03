# 6. 객체와 자료 구조

---

### 자료구조

- 자료를 그대로 공개하고 별다른 함수를 제공하지 않는 형태
    - 자료구조가 동작하는 방식은 클래스를 따로 생성하여 구현

```java
public class Square {
	public Point topLeft;
	public double side;
}
```

### 객체

- 추상화 뒤로 자료를 숨긴 채 자료를 다루는 함수만 공개

```java
public class Square implements Shape {
	private Point topLeft;
	private double side;

	public double area(){
		return side*side;
	}
}
```

### 자료/객체 비대칭

- 절차적인 코드(자료 구조 사용)
    - 기존 자료 구조를 변경하지 않고 새 함수를 추가하기 쉽다.
    - 새로운 자료 구조를 추가하기 어렵다. → 모든 함수를 고쳐야 한다.(의존성)
    - 새로운 함수가 필요한 경우에 적합!
- 객체 지향 코드
    - 기존 함수를 변경하지 않으면서 새 클래스를 추가하기 쉽다.
    - 새로운 함수를 추가하기 어렵다. → 모든 클래스를 고쳐야 한다.(인터페이스)
    - 새로운 자료 타입이 필요한 경우에 적합!

### 디미터 법칙

- 모듈은 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙
- 목적 : 객체는 자료를 숨기고 내부 구조를 노출하지 않게 하기 위함
- 클래스 C의 메서드 f는 다음과 같은 객체의 메서드만 호출해야 한다
    - 클래스C
    - f가 생성한 객체
    - f 인수로 넘어온 객체
    - C 인스턴스 변수에 저장된 객체

```java
// 디미터의 법칙을 위반하는 코드
public void sendMessageForSeoulUser(final User user){
	if("서울".equals(user.getAddress().getRegion())) // 기차 충돌
}

출처 : https://mangkyu.tistory.com/147
```

```java
// 디미터의 법칙을 준수하는 코드
public class Address {
	private String region;
	private String details;
	
	public boolean isSeoulRegion() {
		return "서울".equals(region);
	} 
} 

public class User {
	private String email;
	private String name;
	private Address address;
	
	public boolean isSeoulUser() {
		return address.isSeoulRegion();
	}
}

출처: https://mangkyu.tistory.com/147
```

### 자료 전달 객체

- DTO(Data Transfer Object)
    - 계층 간 데이터 **교환**을 하기 위해 사용하는 객체
    - 로직을 가지지 않는 순수한 데이터 객체(getter & setter만 가진 클래스)

```java
public class customerDTO {
	private String name;
	private int customerNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
}
```

- VO(Value Object)
    - 데이터 그 자체로 의미 있는 것을 담고 있는 객체
    - Read-Only 속성을 가진다

```java
clasee Color {
	private int R,G,B;
	
	public Color(int r, int g, int b) {
		this.R = r; this.G = g, this.B = b
	}

	public int getR() {
		return R;
	}
	...
}
```

### 결론

- 시스템을 구현할 때, 새로운 자료 타입을 추가하는 경우 객체가, 새로운 동작을 추가하는 경우 자료구조와 절차적인 코드가 더 적합하다.