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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer recid;

    @Column(name = "email", length = 128, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstName", length = 64, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 64, nullable = false)
    private String lastName;

    @Column(length = 64)
    private String photo;

    @Column(name = "enable")
    private boolean enable;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + recid +
                ", email='" + email + '\'' +
                ", fullName='" + firstName + " " + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Transient
    public String getPhotoImagePath(){
        if (recid == null || photo == null) {
            System.out.println("User70");
            return "/images/default-user.png";
        }
        System.out.println("User73: " + recid);
        return ("/user-photos/" + this.recid + "/" + this.photo);
    }
}
