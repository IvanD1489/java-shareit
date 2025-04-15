package ru.practicum.shareit.comments;

import ru.practicum.shareit.comments.dto.CommentDto;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment, String authorName) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItemId(),
                comment.getAuthorId(),
                authorName,
                comment.getCreated()
        );
    }

    public static Comment toComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getItemId(),
                commentDto.getAuthorId(),
                commentDto.getCreated()
        );
    }

}
