package com.example.scheduler21.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarEvent {

    private Integer id;

    private String title;

    private LocalDateTime start;

    private LocalDateTime end;
}
