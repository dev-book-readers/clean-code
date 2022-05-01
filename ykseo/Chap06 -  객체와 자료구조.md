
## 결론
- 객체는 동작을 공개하고 자료를 숨긴다. -> 기존 동작을 변경하지 않으면서 새 객체 타입을 추가는 쉬우나, 새 동작을 추가하기는 어렵다.  
- 자료구조(절차적)는 동작없이 자료를 노출한다. -> 기존 자료구조에 새 동작을 추가하기는 쉬우나, 새 자료구조룰 추가하기는 어렵다.  

시스템을 구현할 때,  
- 새로운 자료 타입 추가하는 유연성이 필요 -> 객체  
- 새로운 자료 종작 추가하는 유연성이 필요 -> 자료 구조  

해당 사실을 이해하고, 직면한 문제에 최적인 해결책을 선택하자.  


### 객체
```java
public class Square implements Shape {
	public Point topLeft;
	public double side;
	
	public double area() {
		return side * side;
	}
}

public class Rectangle implements Shape {
	public Point topLeft;
	public double hight;
	public doublc width;
	
	public double area() {
		return hight * width;
	}
}

public class Circle implements Shape {
	public Point center;
	public double radius;
	public final double PI = 3.14;
	
	public double area() {
		return PI * radius * radius;
	}
}
```

### 자료 구조
```java
public class Square {
	public Point topLeft;
	public double side;
}

public class Rectangle {
	public Point topLeft;
	public double hight;
	public doublc width;
}

public class Circle {
	public Point center;
	public double radius;
}

public class Geometry {
	public final double PI = 3.14;
	
	public double area(Object shape) throws NoSuchShapeException {
		if(Shape instanceof Square) {
			Square s = (Square)shape;
			return s.side * s.side;
		} else if(Shape instanceof Rectangle) {
			Rectangle r = (Rectangle)shape;
			return r.hight * r.width;
		} else if(Shape instanceof Circle) {
			Circle c = (Circle)shape;
			return IP * c.radius * c.radius;
		}
		
		throws new NoSuchShapeException();
	}
}		
```

## 디미터 법칙
모듈은 자신이 조작하는 객체의 속사정을 몰라야한다는 법칙  

객체 내부 구조를 하나하나 가지고와 수행하는 아래와 같은 코드를 기차충돌이라함, 피해야한다.   
```java
final String outputDir = ctxt.getOptions().getScratchdir().getAbsolutepath();
```

ctxt가 객체라면 뭔가를 하라고 말해야지 속을 드러내면 안된다.
임시 디렉터리의 절대 경로가 필요한 목적을 찾아 추상화시킨다.
```java
BufferredOutputStream bos = ctxt.createScratchfileStream(classFilename);
```

## 자료 전달 객체
공개변수만 있고 함수가 없는 클래스  
이러한 자료 구조체를 자료 전달 객체(Data Transfer Object, DTO)라 한다.  
데이터베이스와 통신하거나 소켓에서 받은 메세지의 구문을 분석할 때 유용하다.

### bean
아무 의미 없이 변수만 있고, private 변수와 getter/setter 함수만 있는 것  

### 활성 레코드
DTO의 특수한 형태  
private 변수와 getter/setter 함수 + save, find와 같은 탐색 함수 제공  
데이터베이스 테이블이나 다른 소스에서 자료를 직접 변환한 결과  

### 비즈니스 규칙
비즈니스 규칙을 담으면서 내부 자료를 숨기는 객체는 따로 생성하자.