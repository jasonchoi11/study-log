package me.minikuma.book.springboot.web;

import lombok.extern.slf4j.Slf4j;
import me.minikuma.book.springboot.domain.posts.Posts;
import me.minikuma.book.springboot.domain.posts.PostsRepository;
import me.minikuma.book.springboot.web.dto.PostSaveRequestDto;
import me.minikuma.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void delete() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물을 등록한다")
    public void insert_posts() {
        // given
        String title = "테스트 제목";
        String content = "테스트 컨텐츠";

        // when
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("테스트 작가")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";
        ResponseEntity<Long> postForEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(postForEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postForEntity.getBody()).isGreaterThan(0L);

        List<Posts> posts = postsRepository.findAll();

        assertThat(posts.get(0).getTitle()).isEqualTo(title);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물을 수정한다")
    public void update_posts() {
        // given
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("테스트 타이틀-1")
                .content("테스트 컨텐츠-1")
                .author("테스트 작가-1")
                .build()
        );

        Long updatedId = savePosts.getId();
        String expectedTitle = "수정 타이틀-1";
        String expectedContent = "수정 컨텐츠-1";

        // update 용 dto 생성
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        // when
        // 전송 url 만들기
        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId;

        // 전송하기
        HttpEntity<PostsUpdateRequestDto> httpEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, httpEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> posts = postsRepository.findAll();
        log.info(posts.get(0).getContent());
        assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.get(0).getContent()).isEqualTo(expectedContent);
    }
}