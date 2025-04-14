package ru.practicum.shareit.comments;

import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.user.UserStorage;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment, UserStorage userStorage) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItemId(),
                comment.getAuthorId(),
                userStorage.getItem(comment.getAuthorId()).getName(),
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
