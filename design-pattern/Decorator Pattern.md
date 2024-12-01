# 데커레이터 패턴

## 상속 기반 설계

### Java IO 클래스 라이브러리 활용 예시

    ```
    InputStream in = new FileInputStream("test.txt");
    InputStream bin = new BufferedInputStream(in);
    byte[] data = new byte[128];
    while (bin.read(data) != -1) { ... }
    ```

`FileInputStream` 객체를 생성해 `InputStream` 추상클래스 객체에 할당한 다음, `BufferedInputStream` 객체에 이를 전달하여 파일을 읽음

이렇게 사용할 바에는 `FileInputStream` 클래스를 확장하여 `BufferedFileInputStream` 클래스를 설계하는 것이 더 낫지 않을까?

        ```
        InputStream in = new BufferedFileInputStream("test.txt");
        byte[] data = new byte[128];
        while (bin.read(data) != -1) { ... }
        ```


하지만 `InputStream` 의 하위 클래스가 너무 많기 때문에, 이 모든 하위에 `Buffered` 로 시작하는 클래스를 추가하기에는 Java IO 클래스 라이브러리가 너무 커질 수 있기 때문에 상속 기반은 해결책이 될 수 없음


## 데커레이터 패턴 기반 설계 계획

상속 구조가 너무 복잡하다면 상속 관계를 `합성` 관계로 바꾸어 문제를 해결할 수 있다.
`합성`이란 기존 클래스를 상속으로 확장하는 대신 필드로 클래스의 인스턴스를 참조하게 만드는 설계


    ```java
    public class BufferedInputStream extends InputStream {
        protected volatile InputStream in;
        protected BufferedInputStream(InputStream in) {
            this.in = in;
        }
        ...
    }
    
    public class DataInputStream extends InputStream {
        protected volatile InputStream in;
        protected DataInputStream(InputStream in) {
            this.in = in;
        }
    }
    ```

단순 합성과 데커레이터 패턴의 차이점은 데커레이터 클래스는 원본 클래스와 동일한 상위 클래스를 상속하기 때문에 원본 클래스 내에 여러개의 데커레이터 클래스를 중첩할 수 있다.

        ```
        InputStream in = new FileInputStream("test.txt");
        Inputstream x = new BufferedInputstream(in); // 첫번째 중첩
        DatainputStream y = new DataInputstream(x); // 두번째 중접
        int data = y.readInt;
        ```


## 데커레이터 패턴의 활용
- 데커레이터 패턴의 주 목적은 원본 클래스에 향상된 기능을 추가하는 것
- 상속을 사용하여 객체의 행동을 확장하는 것이 어색하거나 불가능할 때 사용하는 것이 좋음
- 원본 클래스에 중첩된 여러 데커레이터 클래스를 사용할 수 있다는 장점이 있음
  - 이를 사용하려면 설계 시 데커레이터 클래스가 원본 클래스와 동일한 추상 클래스 또는 인터페이스를 상속해야함


```java
// Coffee 인터페이스 정의
interface Coffee {
    int cost();
}

// 기본 커피 클래스
class SimpleCoffee implements Coffee {
    @Override
    public int cost() {
        return 5;
    }
}

// 데코레이터 추상 클래스
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public int cost() {
        return coffee.cost();
    }
}

// Milk 데코레이터 클래스
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int cost() {
        return super.cost() + 2;
    }
}

// Sugar 데코레이터 클래스
class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int cost() {
        return super.cost() + 1;
    }
}

// 사용 예
public class Main {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.cost());  // 출력: 5

        Coffee milkCoffee = new MilkDecorator(coffee);
        System.out.println(milkCoffee.cost());  // 출력: 7

        Coffee milkAndSugarCoffee = new SugarDecorator(milkCoffee);
        System.out.println(milkAndSugarCoffee.cost());  // 출력: 8
    }
}
```