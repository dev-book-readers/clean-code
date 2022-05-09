# 2. 의미있는 이름

## 의도

## 일관성 있는 표기

## 상수처리

## 클래스 이름

- 클래스나 객체의 이름은 명사나 명사구가 적합하다.

## 메서드 이름

- 메서드 이름은 동사나 동사구가 적합하다.

### ex1

```
Complex fulcrumPoint = Complex.FromRealNumber(23.0);
```

```
Complex fulcrumPoint = new Complex(23.0);
```

- 위가 더 낫다.

## 알만한 이름을 사용하자!

## 맥락있게 코드를 짜자!

### ex2

```java
int a;
String b;

/* ... */

System.out.printf("User Requested %s, count = %d, b, a);
```

``` java
int itemCount;
String itemName;

/* ... */

System.out.printf("User Requested %s, count = %d, itemName, itemCount);
```

``` java
class SalesItem {
  ItemCode code;
  String name;
  int count;
}

/* ... */

SalesItem selectedItem = salesItemRepository.getItemByCode(purchaseRequest.getItemCode());

System.out.printf("User Requested %s, count = %d, selectedItem.getName(), selectedItem.getCount());
```

## 루프속에서 i, j, k 사용X

- advanced for 문
- lamda를 사용할 수도 있다.
    - index가 굳이 필요 없을 때

## 변수명에 타입X

``` java
String nameString -> name
Int itemPriceAmount -> itemPrice

Account[] accountArray -> accounts
List<Account> accountList -> accounts, accountList -> accounts
Map<Account> accountMap

public interface IShapeFactory -> ShapeFactory
public class ShapeFactoryImpl -> CircleFactory
```

## Google Java Naming Guide

- https://google.github.io/styleguide/javaguide.html#s5-naming
    - package: all lower case, no underscores
    - class: UpperCamelCase(test class는 Test로 끝내기)
    - method: LowerCamelCase