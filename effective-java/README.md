### Effective Java 3rd

---

**Item1.생성자 대신 정적 팩토리 메서드를 고려하라.** 
* 이름을 가질 수 있다. (naming)
* 새로운 인스턴스를 계속 만들지 않아도 된다. (Caching) ```Boolean.valueOf(boolean)```
* 하위 타입의 객체를 반환할 수 있다. ```java.util.Collections```
* 입력 매개변수에 따라 다른 객체를 반환할 수 있다. ```EnumSet```
---  
**Item3.Private 생성자나 열거 타입으로 싱글턴임을 보증하라.**
* Singleton 만드는 방법
  * Private 생성자 + ```public final static``` 필드
  * 정적 팩토리 메서드를 ```public static``` 으로 제공
  * 열거 타입 (Recommend) -> 열거타입은 ```private``` 생성자를 가지고 있다.
---
**Item4.인스턴스화를 막으려거든 Private 생성자를 사용하라** 
* 정적 메서드와 정적 필드만을 가지는 클래스를 만드는 경우 (```Utility Class```)
  * 인스턴스화를 막는 방법
    * `final` class
    * `abstract` class
    * `private` 생성자 -> Recommend 방법
---
**Item5.자원을 직접 명시하지 말고 의존 객체 주입을 사용하라**
* 정적 유틸리티 클래스(Item 4)
* 싱글톤 클래스 (Item 3)
* 의존 객체 주입 -> Recommend 방법
---
