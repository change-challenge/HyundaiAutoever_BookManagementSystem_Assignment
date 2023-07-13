package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CopyDetailDTO {
    private Long copyId;
    private Optional<LocalDate> rentEndDate;
}
