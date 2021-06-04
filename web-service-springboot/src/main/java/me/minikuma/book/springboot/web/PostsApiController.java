package me.minikuma.book.springboot.web;

import lombok.RequiredArgsConstructor;
import me.minikuma.book.springboot.service.posts.PostsService;
import me.minikuma.book.springboot.web.dto.PostSaveRequestDto;
import me.minikuma.book.springboot.web.dto.PostsResponseDto;
import me.minikuma.book.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostSaveRequestDto request) {
        return postsService.save(request);
    }

    @PutMapping(value = "/api/v1/posts/{id}")
    public Long findById(@PathVariable(name = "id") Long id, PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }
}
