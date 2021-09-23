### Thymeleaf Basic

---

공식 사이트: https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html   

---   

#### 기본 기능    

* 텍스트 - text, utext    
* 변수 - SpringEL (```${...}```)
* 기본 문법
  * ```${#request}```
  * ```${#response}```
  * ```${#session}```
  * ```${#servletContext}```
  * ```${#locale}```    
* Utility Object - https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#appendix-b-expression-utility-objects    
* URL Link (```@{...}```)      
* Literal
  * 문자(```"'문자'"```) 주의 필요!
  * 숫자
  * NULL
* Operator(연산)   
* attribute (속성 값)   
* Iterator (반복) - ```th:each``` (```state``` 지원)
  * ```index``` : 0 부터 시작하는 값
  * ```count``` : 1 부터 시작하는 값  
  * ```size``` : 전체 크기
  * ```even```, ```odd``` : 홀수, 짝수 여부  (```boolean```)
  * ```first```, ```last``` : 처음, 마직막 여부 (```boolean```)
  * ```current``` : 현재 객체   
* Conditional (조건부 평가) - ```if```, ```unless```   
* Comments (주석)
  * 표준 HTML 주석
  * 타임리프 파서 주석
  * 타임리프 프로토타입 주석
* Block (블록) - ```<th:block>``` (타임리프 자체 태그)   
* 자바스크립트 인라인 - ```<script th:inline="javascript">```
  * 텍스트 렌더링 지원
  * 자바스크립트 내츄럴 템플릿 지원
  * 객체를 JSON 으로 표현 지원
  * 인라인 each - ```[# th:each="user, stat : ${users}]``` 
* 템플릿 조각 - 공통 영역 (header, footer 등)   
* 템플릿 레이아웃  

---





