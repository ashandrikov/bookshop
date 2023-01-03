package com.shandrikov.bookshop.domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
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
