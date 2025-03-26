package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ItemRequestDto {

    private Long id;
    private String description;
    private Long requestor;
    private LocalDate created;

}
