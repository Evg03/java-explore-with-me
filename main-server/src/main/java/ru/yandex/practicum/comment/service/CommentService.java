package ru.yandex.practicum.comment.service;

import ru.yandex.practicum.comment.dto.CommentDto;
import ru.yandex.practicum.comment.dto.NewCommentDto;
import ru.yandex.practicum.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(NewCommentDto newCommentDto, int eventId, int userId);

    void deleteComment(int userId, int commentId);

    CommentDto updateComment(UpdateCommentDto updateCommentDto, int userId, int commentId);

    CommentDto getCommentById(int commentId);

    List<CommentDto> getUserComments(int userId, int from, int size);
}
