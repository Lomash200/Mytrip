package com.lomash.mytrip.entity;


import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String username;


    @Column(unique = true, nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;


    private String firstName;
    private String lastName;
    private String phone;
    private boolean enabled = true;


    private Instant createdAt;
    private Instant updatedAt;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}