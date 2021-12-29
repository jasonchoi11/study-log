### Effective Java 3rd

---

**Item.01.생성자 대신 정적 팩토리 메서드를 고려하라.** 
* 이름을 가질 수 있다. (naming)
* 새로운 인스턴스를 계속 만들지 않아도 된다. (Caching) ```Boolean.valueOf(boolean)```
* 하위 타입의 객체를 반환할 수 있다. ```java.util.Collections```
* 입력 매개변수에 따라 다른 객체를 반환할 수 있다. ```EnumSet```
---  
**Item.03.Private 생성자나 열거 타입으로 싱글턴임을 보증하라.**
* Singleton 만드는 방법
  * Private 생성자 + ```public final static``` 필드
  * 정적 팩토리 메서드를 ```public static``` 으로 제공
  * 열거 타입 (Recommend) -> 열거타입은 ```private``` 생성자를 가지고 있다.
---
