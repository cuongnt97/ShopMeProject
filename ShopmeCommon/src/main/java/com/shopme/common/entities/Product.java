package com.shopme.common.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 256, unique = true)
    private String name;

    @Column(name = "alias", nullable = false, length = 64, unique = true)
    private String alias;

    @Column(name = "short_description", length = 512, nullable = false)
    private String shortDescription;

    @Column(name = "full_description", length = 4096, nullable = false)
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(nullable = false)
    private boolean enable;

    @Column(name = "in_stock")
    private boolean inStock;

    @Column(nullable = false)
    private float cost;

    @Column(nullable = false)
    private float price;

    @Column(name = "discount_percent")
    private float discountPercent;

    @Column(nullable = false)
    private float length;

    @Column(nullable = false)
    private float width;
    @Column(nullable = false)
    private float height;
    @Column(nullable = false)
    private float weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
