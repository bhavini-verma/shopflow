package com.dailycodework.dreamshop.model;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter

public class Cart {
    @Id
    @GeneratedValue(strategy = Ge nerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    @OneToMany(mappedby = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> CartItems;
}
