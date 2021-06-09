package me.minikuma.book.springboot.domain.posts;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestExecution;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("저장된 게시글 조회하기")
    public void get_saved_posts() {
        // given
        String title = "테스트 게시글 제목";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build()
        );

        // when
        List<Posts> findPosts = postsRepository.findAll();

        // then
        Posts posts = findPosts.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("base time entity 가 자동으로 등록된다")
    public void base_time_entity_register() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 6, 9, 0,0,0);
        postsRepository.save(Posts.builder()
            .title("타이틀")
            .author("저자")
            .content("컨텐츠")
            .build()
        );

        // when
        List<Posts> posts = postsRepository.findAll();
        Posts post = posts.get(0);
        log.info("createdDate : {}, modifiedDate : {} ", post.getCreatedDate(), post.getModifiedDate());

        // then
        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}