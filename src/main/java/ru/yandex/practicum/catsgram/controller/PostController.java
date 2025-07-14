package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.SortOrder;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "0") String from,
            @RequestParam(defaultValue = "10") String size) {

        SortOrder sortReal = SortOrder.from(sort);
        if (sortReal == null) {
            throw new ParameterNotValidException("sort", String.format("Получено: %s должно быть: ask или desc", sort));
        }
        int sizeReal = Integer.parseInt(size);
        if (sizeReal <= 0) {
            throw new ParameterNotValidException("size", String.format("Размер должен быть больше нуля," +
                    " текущее значение : %s", size));
        }
        int fromReal = Integer.parseInt(from);
        if (fromReal < 0) {
            throw new ParameterNotValidException("from", String.format("Начало выборки должно быть положительным" +
                    " числом,текущее значение : %s", from));
        }

        return postService.findAll(sortReal, fromReal, sizeReal);
    }

    @GetMapping("/{id}")
    public Post findPostById(@PathVariable Long id){
        return postService.findPostById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Пост с id=" + id + " не найден"
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }
}

