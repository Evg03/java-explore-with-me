package ru.yandex.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.comment.dto.CommentDto;
import ru.yandex.practicum.comment.dto.NewCommentDto;
import ru.yandex.practicum.comment.dto.UpdateCommentDto;
import ru.yandex.practicum.comment.model.Comment;
import ru.yandex.practicum.comment.storage.CommentRepository;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.exception.*;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper = new CommentMapper();

    @Override
    public CommentDto createComment(NewCommentDto newCommentDto, int eventId, int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        User user = userOptional.get();
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(String.format("События с id = %s не существует.", eventId));
        }
        Comment comment = modelMapper.map(newCommentDto, Comment.class);
        comment.setEvent(eventId);
        comment.setAuthor(user);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(int userId, int commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            validateCommentOwner(comment, userId);
            commentRepository.deleteById(commentId);
        }
    }

    @Override
    public CommentDto updateComment(UpdateCommentDto updateCommentDto, int userId, int commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            throw new CommentNotFoundException(String.format("Комментария с id = %s не существует.", commentId));
        }
        Comment comment = commentOptional.get();
        validateCommentOwner(comment, userId);
        if (LocalDateTime.now().isAfter(comment.getCreated().plusMinutes(5))) {
            throw new ActionNotAllowedException(String.format("Нельзя отредактировать комментарий с id = %s, " +
                    "так как прошло более 5-ти минут с момента его публикации", commentId));
        }
        modelMapper.map(updateCommentDto, comment);
        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public CommentDto getCommentById(int commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            throw new CommentNotFoundException(String.format("Комментария с id = %s не существует.", commentId));
        }
        return modelMapper.map(commentOptional.get(), CommentDto.class);
    }

    @Override
    public List<CommentDto> getUserComments(int userId, int from, int size) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователя с id = %s не существует.", userId));
        }
        return commentRepository.findByAuthorId(userId,
                        PageRequest.of(from, size, Sort.by("created").descending())).stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    private void validateCommentOwner(Comment comment, int userId) {
        if (comment.getAuthor().getId() != userId) {
            throw new InvalidOwnerException(String.format("Пользователь с id = %s " +
                    "не является автором комментария с id = %s.", userId, comment.getId()));
        }
    }
}
