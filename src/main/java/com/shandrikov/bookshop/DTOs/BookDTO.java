package com.shandrikov.bookshop.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class BookDTO {
    @NotBlank(message = "Author must not be empty")
    @Length(min = 3, message = "Author's name is too short")
    @Length(max = 25, message = "Author's name is too long")
    private String author;
    @NotBlank(message = "Title must not be empty")
    @Length(min = 3, message = "Title is too short")
    @Length(max = 25, message = "Title is too long")
    private String title;
    @Min(value = 0, message = "Price can not be lower than 0.00")
    @Max(value = 250, message = "Price cannot be higher than 250.00")
    private double price;
    @Min(value = 1900, message = "Year can not be less than 1900")
    @Max(value = 2024, message = "Year can not be bigger than 2024")
    private int year;
}
