package com.shopme.common.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 128, nullable = false, unique = true)
    private String name;

    @Column (name = "alias", length = 64, nullable = false, unique = true)
    private String alias;

    @Column(name = "image", length = 128, nullable = false)
    private String image;

    @Column(name = "enable")
    private boolean enable;

    @OneToOne
    @JoinColumn(name = "parent")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    @Transient
    public String getCategoryImagePath(){

        if (id == null || image == null) {
            return "/images/image-thumbnail.png";
        }
        return ("/category-images/" + this.id + "/" + this.image);
    }

    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Category copyCategory(Category category) {
        Category copyCategory = new Category();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());
        copyCategory.setAlias(category.getAlias());
        copyCategory.setImage(category.getImage());
        copyCategory.setEnable(category.isEnable());

        return copyCategory;
    }

    public static Category copyCategory(Category category, String name) {
        Category copyCategory = Category.copyCategory(category);
        copyCategory.setName(name);

        return copyCategory;
    }
}
