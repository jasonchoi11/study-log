package me.minikuma.book.springboot.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.book.springboot.service.posts.PostsService;
import me.minikuma.book.springboot.web.dto.PostSaveRequestDto;
import me.minikuma.book.springboot.web.dto.PostsResponseDto;
import me.minikuma.book.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 등록 API
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostSaveRequestDto request) {
        return postsService.save(request);
    }

    // 수정 API
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable(name = "id") Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    // 조회 API
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
