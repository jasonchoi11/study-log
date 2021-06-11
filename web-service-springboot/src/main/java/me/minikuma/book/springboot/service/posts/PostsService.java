package me.minikuma.book.springboot.service.posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.book.springboot.domain.posts.Posts;
import me.minikuma.book.springboot.domain.posts.PostsRepository;
import me.minikuma.book.springboot.web.dto.PostSaveRequestDto;
import me.minikuma.book.springboot.web.dto.PostsListResponseDto;
import me.minikuma.book.springboot.web.dto.PostsResponseDto;
import me.minikuma.book.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
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

    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList())
        ;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
    }
}
