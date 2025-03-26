package ru.practicum.shareit.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ItemRequest {

    private Long id;
    private String description;
    private Long requestor;
    private LocalDate created;

}
