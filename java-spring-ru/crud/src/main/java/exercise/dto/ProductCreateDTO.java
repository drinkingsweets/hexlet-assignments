package exercise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class ProductCreateDTO {
    @NotNull
    private JsonNullable<Long> categoryId;

    @NotBlank
    private String title;

    @NotNull
    private int price;
}
