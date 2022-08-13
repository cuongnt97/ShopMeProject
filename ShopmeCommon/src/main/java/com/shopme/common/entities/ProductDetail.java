package com.shopme.common.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_detail")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public ProductDetail(String name, String value, Product product) {
        this.name = name;
        this.value = value;
        this.product = product;
    }
}
