package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDto {

    private Long id;
    private String description;
    private Long requestor;
    private LocalDate created;

}
