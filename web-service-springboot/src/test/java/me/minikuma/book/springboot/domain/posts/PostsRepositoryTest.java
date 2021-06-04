package me.minikuma.book.springboot.domain.posts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestExecution;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @AfterTestExecution
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
}