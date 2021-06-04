package me.minikuma.book.springboot.service.posts;

import lombok.RequiredArgsConstructor;
import me.minikuma.book.springboot.domain.posts.Posts;
import me.minikuma.book.springboot.domain.posts.PostsRepository;
import me.minikuma.book.springboot.web.dto.PostSaveRequestDto;
import me.minikuma.book.springboot.web.dto.PostsResponseDto;
import me.minikuma.book.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts postsById = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글에 없습니다. id = " + id));
        postsById.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글에 없습니다. id = " + id));
        return new PostsResponseDto(posts);
    }
}
