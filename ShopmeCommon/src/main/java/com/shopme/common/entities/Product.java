package com.shopme.common.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 256, unique = true)
    private String name;

    @Column(name = "alias", nullable = false, length = 256, unique = true)
    private String alias;

    @Column(name = "short_description", length = 512, nullable = false)
    private String shortDescription;

    @Column(name = "full_description", length = 4096, nullable = false)
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column( name = "enable", nullable = false)
    private boolean enable;

    @Column(name = "in_stock")
    private boolean inStock;

    @Column(nullable = false)
    private float cost;

    @Column(nullable = false)
    private float price;

    @Column(name = "discount_percent")
    private float discountPercent;

    @Column(name = "main_image", nullable = false)
    private String mainImage;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductImage> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductDetail> details = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addExtraImages(String imageName) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(this);
        productImage.setName(imageName);
        this.images.add(productImage);
    }

    public void addDetail(String name, String value) {
        this.details.add(new ProductDetail(name, value, this));
    }

    public void addDetail(Integer id, String name, String value) {
        this.details.add(new ProductDetail(id, name, value, this));
    }

    @Transient
    public String getMainImagePath(){
        if (id == null || mainImage == null) {
            return "/images/image-thumbnail.png";
        }
        return ("/product-images/" + this.id + "/" + this.mainImage);
    }
}
