### 스프링 부트와 AWS 로 혼자 구현하는 웹 서비스      

---     
#### Description
이동욱 님의 저서인 "스프링 부트와 AWS로 혼자 구현하는 웹 서비스"의 예제를 공부 목적으로 작성한 코드 입니다.


*Tech Spec*
* Spring Boot 2.5.0
* mac os big sur
* H2 Database (In-Memory)
* mustache template engine

---    
#### Installation    

[Build & Package]
```shell
./gradlew clean bootJar
```       

[Execution]
```shell
java -jar ./build/libs/web-service-springboot-1.0-SNAPSHOT.jar
```      

---  

#### API List 

* Backend     
  * POST /api/v1/posts : 게시물 등록    
  * PUT /api/v1/posts/{id} : 게시물 수정   
  * GET /api/v1/posts/{id} : 게시물 조회   
  * DELETE /api/v1/posts/{id} : 게시물 삭제
    

* Frontend
  * GET /posts/save : 게시물 등록 페이지
  * GET /posts/update : 게시물 수정/삭제 페이지

---  
