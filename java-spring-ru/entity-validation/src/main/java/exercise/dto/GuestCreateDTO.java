package exercise.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Getter
@Setter
public class GuestCreateDTO {
    @NotBlank
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^\\+[0-9]{11,13}$", message = "Номер телефона должен начинаться с + и содержать от 11 до 13 цифр")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @FutureOrPresent
    private LocalDate cardValidUntil;
}
// END
