package com.shandrikov.bookshop.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewPassword {
    @Length(min = 4, message = "Password length must be 4 chars minimum!")
    @JsonProperty("new_password")
    private String updatedPassword;
}
