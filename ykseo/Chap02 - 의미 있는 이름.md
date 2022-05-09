# Chap01 Naming

## 의도를 분명히 밝혀라
변수와 함수 클래스 이름이 꼭 포함해야할 내용
* 존재 이유
* 수행 기능
* 사용 방법

## 하지 말아야할 것
### 그릇된 정보를 피하라
1. 널리 쓰고 있는 단어 사용하지 않기 - ex. data, hp, 등..
2. 서로 흡사한 이름을 사용하지 않기 - ex. XYZControllerForEfficentHandlingOfStrings vs XYZControllerForEfficentStorageOfStrings
3. 유사한 개념은 유사한 표기법 사용하기 - IDE의 자동 코드 완성 기능을 유용하게 사용할 수 있는가?

### 의미 있게 구분하라
동일한 스코프 안에서는 다른 두 개념에 같은 이름을 사용하지 못한다고 불용어를 사용하지 말 것 - ex. class vs. klass -> 이렇게 하지 말 것.
연속적인 숫자를 덧붙인 이름은 아무런 정보를 제공하지 않는다. - ex. (a1, a2, ,,, aN)
불용어(의미가 불분명한 용어)를 사용하지 말 것 
의미없는 접두어, 접미어 - ex. a, the, data, info 
단, 의미가 분명하다면 사용해도 무방 - ex. 모든 지역변수는 a, 함수는 the를 붙이는 경우(요즘엔 변수와 함수를 구분하게 해주는 다양한 IDE가 존재하므로 잘 사용하진 않음)
의미없는 중복을 피할 것 - ex. nameString -> name은 String이 아닌 다른 type이 될 수 없다. 그냥 name이 더 간결하다.
명확한 관례가 없다면 불용어(의미 없는 용어)는 사용하지 말 것

### 발음하기 쉬운 이름을 사용하라
```java
class DtaRcrd102 {
	private Date genymdhms; // generate date, year, month, day, hour, minute, second
	private Date modymdhms; // modify ,,
	private final String pszqint = "102";
	/* ... */
};
```
```java
class Customer {
	private Date generationTimestamp;
	private Date modificationTimestamp;
	private final String recodeId = "102";
	/* ... */
};
```

### 검색하기 쉬운 이름을 사용하라
문자 하나를 사용하여 이름을 정하지 말라
ex. MAX_CLASSES_PER_STUDENT vs 7 vs e
루프에서 반복 횟수를 세는 변수  i, j, k 정도는 괜찮음. l, o는 1 과 0이랑 비슷하니 피할 것!!!
간단한 메서드에서 로컬 변수로만 한 문자를 사용하자
이름 길이는 범위 크기에 비례해야한다.

### 인코딩을 피하라
인코딩 : 변수 및 함수의 이름 인자 앞에 데이터 타입을 명시하는 코딩 규칙
1. 헝가리식 표기법  - ex. i_num : int 타입의 숫자 vs num
2. 인터페이스 클래스와 구현 클래스 - ex. IShapeFactory : I가 인터페이스임을 명시함 vs ShapeFactory

### 자신의 기억력을 자랑하지 마라
독자가 코드를 일으면서 변수 이르을 자신이 아는 이름으로 변환해야한다면 좋지 않다.
명로함이 최고다

### 기발한 단어는 피하라
특정 문화에서만 사용하는 농담은 피할 것


## 표기법
### 클래스 이름
클래스 이름이나 객체 이름은 명사나 명사구가 적합 - ex.  Customer, WikiPage, Account, AddressParser
Manager, Processor, Data, Info와 같은 광범위한 단어는 피하고 동사는 사용하지 말것

### 함수 이름
동사나 동사구가 접합 - ex. postzPayment, deletePage, save
접근자, 변경자, 조건자 -> get, set, is

????

### 한 개념에 한 단어를 사용할 것
비슷한 코드, 클래스, 타입이라면 일관성 있는 어휘를 사용하기

### 말장난 하지 마라
기존의 add 메소드가 두개의 값을 더하거나 이어서 새로운 값을 만든다면, 
집합에 새로운 값을 추가하는 메소드는 insert를 사용할 것

### 해법 영역에서 가져온 이름을 사용하라
전산 용어, 알고리즘 이름, 패턴 이름, 수학 용어 사용 가능!

### 문제 영역에서 가져온 이름을 사용하라

### 의미 있는 맥락을 추가하라

### 불필요한 맥락을 없애라
