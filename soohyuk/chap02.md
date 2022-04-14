# Clean Code Chapter02

# 코드의 의도를 분명히 밝혀라

- 코드가 하는 일을 짐작하기 힘든 예시

```java
public List<int[]> getThem(){
List<int[]> list1 = new ArrayList<int[]>();
for( int[] x : theList ){
  if(x[0] == 4 ) list1.add(x);
}
return list1;
}
```

- 올바른 방향으로의 수정

```java
public List<Cell> getFlaggedCells(){
	List<Cell> flaggedCells = new ArrayList<Cell>();
	for( Cell cell : gameBoard ){
			if( cell.isFlagged()) flaggedCells.add(cell);
	}
  return flaggedCells;
}
```

- 생성자를 중복 정의할때는 정적 팩토리 메서드를 사용한다.
    - 이 때, 메서드는 인수를 설명하는 이름을 사용한다.

( 아래 링크는 그 이유에 대한 상세한 설명  )

[정적 팩토리 메서드는 왜 사용할까? 에 대한 설명 링크 ](https://velog.io/@ljinsk3/%EC%A0%95%EC%A0%81-%ED%8C%A9%ED%86%A0%EB%A6%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EB%8A%94-%EC%99%9C-%EC%82%AC%EC%9A%A9%ED%95%A0%EA%B9%8C)
