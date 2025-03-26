package ru.practicum.shareit.item.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private Boolean available;
    private Long request;

}
