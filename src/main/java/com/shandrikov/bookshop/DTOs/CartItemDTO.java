package com.shandrikov.bookshop.DTOs;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemDTO {
    private BookDTO book;
    private int quantity;
    @Column(name = "total_price")
    public double getTotalPrice(){
        return book.getPrice() * quantity;
    }
}
