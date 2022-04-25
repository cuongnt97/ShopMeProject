package com.shopme.common.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 45, unique = true)
    private String name;

    @Column(name = "logo", nullable = false, length = 128)
    private String logo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "brands_categories",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", categories=" + categories + "]";
    }

    @Transient
    public String getLogoImagePath(){
        if (id == null || logo == null) {
            return "/images/image-thumbnail.png";
        }
        return ("/brand-logos/" + this.id + "/" + this.logo);
    }

}
