### Effective Java 3rd

---

Item.01.생성자 대신 정적 팩토리 메서드를 고려하라.   
* 이름을 가질 수 있다. (naming)
* 새로운 인스턴스를 계속 만들지 않아도 된다. (Caching) ```Boolean.valueOf(boolean)```
* 하위 타입의 객체를 반환할 수 있다. ```java.util.Collections```
* 입력 매개변수에 따라 다른 객체를 반환할 수 있다. ```EnumSet```
---  
