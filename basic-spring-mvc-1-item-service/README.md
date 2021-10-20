### Spring MVC 1편: Item Service Example

---

#### Tech.
* [Bootstrap](https://getbootstrap.com/)
* Spring Boot 2.5.4
* JDK 11    

---    

#### Feature    

* Item Service - Thymeleaf 기능 구현
* Item Service - Spring 통합 기능 구현
  * Thymeleaf-Spring 설정 적용 (```spring-boot-starter-thymeleaf``` 자동구성 지원)
  * 입력 폼 처리 (```th:object```, ```th:field```, ```*{...}```)
  * 체크박스 (멀티) - 판매 여부 기능 추가 (```filed``` + ```check``` 속성 해결) ```@ModelAttribute```
  * 라디오 버튼 - 등록 지역 선택
  * 셀렉트 박스 - 상품 종류 선택 (```enum```)
* 메시지 국제화
  * 메시지 기능: 메시지를 한곳에서 관리하는 기능 (예) ```message.properties``` 파일
  * 국제화 기능: 메시지 기능을 나라 별로 관리 (예) ```message_en.properties``` 파일, ```accept-language``` header 로 구분하고 ```cookie``` 로 유지
  * **But, Thymeleaf 스프링이 제공하는 메시지, 국제화를 통합하여 제공**
    * ```MessageSource```
    * ```message.properties``` 파일을 자동으로 읽음 (스프링 부트)
    * 스프링 부트에서 ```spring.messages.basename=messages,config.i18n.messages``` 설정 (기본값은 ```spring.messages.basename=messages```)
    * Thymeleaf 메시지 표현식: ```#{...}```
    * ```LocaleResolver```
* Validation
  * 요구사항
    * 타입검증: 가격, 수량에 문자가 들어오면 오류 처리
    * 필드검증: 상품명(필수,공백X), 가격 10,000원 이상, 100만원 이하, 수량 최대 9,999
    * 특정필드검증: ```가격 * 수량```은 10,000원 이상
---


