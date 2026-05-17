package com.dailycodework.dreamshop.model;

import java.math.BigDecimal;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter

public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal unitprice;
    private BigDecimal totalPrice;

    @ManyToOne(cascade = CascadeType.All)
    @JoinColumn(name = "cart_id")
    private Product product;
    private Cart cart;

    public void setTotalPrice() {
        this.totalPrice = this.unitprice.multiply(new BigDecimal(quantity));

    }

}
