package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.Status;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingDto {

    private Long id;
    private LocalDate start;
    private LocalDate end;
    private Long item;
    private Long booker;
    private Status status;

}
