package com.shandrikov.bookshop.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CartItemDTO {
    private BookDTO book;
    private int quantity;
    public BigDecimal getTotalPrice(){
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
