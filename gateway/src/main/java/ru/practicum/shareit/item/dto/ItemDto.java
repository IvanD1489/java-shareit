package ru.practicum.shareit.item.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.comments.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Boolean available;
    @Nullable
    private Long requestId;
    private Long owner;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
    private Collection<CommentDto> comments;

}
