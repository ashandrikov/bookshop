package com.shandrikov.bookshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shandrikov.bookshop.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
//    Example: One-to-Many BI relationship. It's highly recommended to avoid coding this way. For example only
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderDetails> orderDetails = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime orderTime;

    public Order() {
        this.status = OrderStatus.NEW;
        this.orderTime = LocalDateTime.now();
    }
}
