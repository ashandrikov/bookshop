package com.shandrikov.bookshop.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shandrikov.bookshop.domains.OrderDetails;
import com.shandrikov.bookshop.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class OrderDTO {
    private Set<OrderDetails> orderDetails;
    private OrderStatus status;
    @JsonFormat(pattern="dd.MM.yyyy 'at' HH:mm:ss")
    private LocalDateTime orderTime;
    public BigDecimal getTotalOrderPrice(){
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal taxFee = BigDecimal.valueOf(1.05);
        for (OrderDetails details :orderDetails) {
            totalPrice = totalPrice.add(details.getTotalPrice());
        }
        return totalPrice.multiply(taxFee).setScale(3, RoundingMode.HALF_UP);
    }
}

