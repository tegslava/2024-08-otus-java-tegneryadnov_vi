package ru.otus.java.pro.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@ToString
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8102699536467887386L;

    private String firstName;
    private String lastName;
    private String email;
    private List<Integer> counts;
    private LocalDate dateOfBirth;
}
