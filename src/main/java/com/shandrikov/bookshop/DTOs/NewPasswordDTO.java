package com.shandrikov.bookshop.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewPasswordDTO {
    @Length(min = 4, message = "Password length must be 4 chars minimum!")
    @JsonProperty("new_password")
    private String updatedPassword;
}
