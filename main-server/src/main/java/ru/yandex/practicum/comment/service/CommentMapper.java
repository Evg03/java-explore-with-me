package ru.yandex.practicum.comment.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import ru.yandex.practicum.comment.dto.CommentDto;
import ru.yandex.practicum.comment.model.Comment;

public class CommentMapper extends ModelMapper {

    public CommentMapper() {
        TypeMap<Comment, CommentDto> commentDtoTypeMap = this.createTypeMap(Comment.class, CommentDto.class);
        commentDtoTypeMap.addMapping(comment -> comment.getAuthor().getName(), CommentDto::setAuthorName);
        this.getConfiguration().setSkipNullEnabled(true);
    }
}
