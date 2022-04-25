# Chp.02 : 의미 있는 이름

[https://velog.io/@ljinsk3/정적-팩토리-메서드는-왜-사용할까](https://velog.io/@ljinsk3/%EC%A0%95%EC%A0%81-%ED%8C%A9%ED%86%A0%EB%A6%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EB%8A%94-%EC%99%9C-%EC%82%AC%EC%9A%A9%ED%95%A0%EA%B9%8C)

[https://google.github.io/styleguide/javaguide.html#s5-naming](https://google.github.io/styleguide/javaguide.html#s5-naming)

###**[ 의도를 분명히 밝혀라 ]**
변수/함수/클래스의 존재 이유, 수행 기능, 사용 방법을 고려하여 이름을 지어야한다.

```java
public List<int[]> getThem() {
	List<int[]> list1 = new ArrayList<int[]>();
		for (int[] x : theList)
			if (x[0] == 4)
				list1.add(x);
	return list1;
}
```

복잡한 문장이 없어도 위 코드의 역할을 파악하기 어렵다. 그래서 위의 코드를 아래처럼 이름만 바꿔도 확실히 파악하기가 쉬워진다.

```java
public List<Cell> getFlaggedCells() {
	List<Cell> flaggedCells = new ArrayList<Cell>();
	for (Cell cell : gameBoard)
		if (cell[STATUS_VALUE] == FLAGGED)
			flaggedCells.add(cell);
	return flaggedCells;
}
```

이렇게 int[] 배열에 담을 cell값들을 Cell 클래스에서 처리해주고, 변수명/메서드명을 바꿔주니까 똑같이 간결한 코드임에도 훨씬 이해하기가 쉬워진다.

###**[ 그릇된 정보를 피하라 ]**
일관성이 떨어지는 표기법 피하기
ex) 소문자 l, o는 숫자 1, 0이랑 비슷하게 보인다.

###**[ 의미 있게 구분하라 ]**
컴파일러나 인터프리터만 통과하려는 생각으로 코드를 구현하면 안된다. 읽는 사람이 이름을 통해서 역할 차이를 구분할 수 있도록 이름을 지어야한다.

- 변수명으로 a1, a2,...와 같이 연속적인 숫자를 덧붙인 이름
- 불용어를 추가한 이름은 중복을 만듦
  = 이름 앞에/끝에 Info, Data, a, an, the 등을 붙이는 행위.
  ex) theNameString / Name, ProductInfo /Product 등

###**[ 발음하기 쉬운 이름을 사용 ]**

###**[ 검색하기 쉬운 이름을 사용 ]**

- 1개의 문자를 사용한 이름
- 상수(숫자)
- 간단한 메서드에서 로컬 변수만 한 문자를 사용하는건 괜찮음 b/c 이름 길이는 범위 크기에 비례해야 한다.
- 이름 지을 때 명사보다 동사를 먼저 쓰는게 좋음

###**[ 인코딩을 피하자 ]**

- 기본적으로 이름에 인코딩할 정보는 많은데, 유형이나 범위 정보까지 인코딩에 직접 넣으면 그만큼 이름을 해독하기 어려워진다. (Java - 변수 이름에 type을 인코딩할 필요 X)
- 헝가리식 표기법 사용X → 카멜 표기법 쓰기
- 멤버 변수 접두어 사용X → 주의를 흐트리고 과도한 정보를 제공함
    - 굳이 클래스/인터페이스/...임을 알릴 필요가 없음
    - ex: 멤버변수 m_dsc; 인터페이스 IShapeFactory;

###**[ 나의 기억력을 믿지 말기 ]**

- 변수명으로 문자 1개 X
- loop 내에서 사용하는 변수 i, j, k는 괜찮음 (충돌하지 않는 경우)

###**[ Class, Method 이름 짓는 방법 ]**

- Class, instance(객체) 이름 : 명사, 명사구
    - ex) Customer, Account, AddressParser, ...
    - NOT ex) Manager, Data, Info, ...
- Method 이름 : 동사, 동사구 (상황에 따라 앞에 get/set/is 등을 붙임)
    - ex) postPayment, deletePage, save, setName, ...

###**[ 일관성 ]**

- 한 개념에 한 단어를 사용 : 메서드 이름을 일관적으로 지어야 나중에 어느 클래스에서 어떤 이름을 썼는지 기억하기 쉬움
    - ex) add → insert, append
- 해법 영역에서 가져온 이름을 사용 : 기술 개념에는 기술 이름이 가장 적합함
- 문제 영역에서 가져온 이름을 사용 : 적절한 프로그래머 용어가 없다면 문제 영역에서 이름을 가져옴.

###**[ 의미 있는 맥락을 추가하라 ]**

- 클래스, 함수, 이름 공간에 의미를 넣어 맥락을 부여 > 실패할 경우, 마지막 수단으로 접두어 붙임
- 이름에 불필요한 맥락은 버리기
    - ex) GSDAccountAddress, GSDCustomerAddress → “GSD...Address” 10글자씩이나 중복되므로 Address 클래스를 따로 만들기