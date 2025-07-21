package com.ra.ss9.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @Column(name = "username",length = 100, unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "full_name",length = 70, nullable = false)
    private String fullName;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "address", length = 200)
    private String address;
    @Column(name = "email", unique = true)
    private String email;
    @Column( name = "phone",length = 20, unique = true)
    private String phone;
    @Column(name = "image_url")
    private String imageUrl;
}
