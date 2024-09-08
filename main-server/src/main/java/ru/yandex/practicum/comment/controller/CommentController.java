package ru.yandex.practicum.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.comment.dto.CommentDto;
import ru.yandex.practicum.comment.dto.NewCommentDto;
import ru.yandex.practicum.comment.dto.UpdateCommentDto;
import ru.yandex.practicum.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/users/{userId}/events/{eventId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@Valid @RequestBody NewCommentDto newCommentDto,
                                    @PathVariable int eventId,
                                    @PathVariable int userId) {
        return commentService.createComment(newCommentDto, eventId, userId);
    }

    @DeleteMapping(path = "/users/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable int userId,
                              @PathVariable int commentId) {
        commentService.deleteComment(userId, commentId);
    }

    @PatchMapping(path = "/users/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@Valid @RequestBody UpdateCommentDto updateCommentDto,
                                    @PathVariable int userId,
                                    @PathVariable int commentId) {
        return commentService.updateComment(updateCommentDto, userId, commentId);
    }

    @GetMapping(path = "/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentById(@PathVariable int commentId) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping(path = "/users/{userId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getUserComments(@PathVariable int userId,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size) {
        return commentService.getUserComments(userId, from, size);
    }
}
