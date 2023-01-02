package com.shandrikov.bookshop.domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "books")
@Data
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
}
