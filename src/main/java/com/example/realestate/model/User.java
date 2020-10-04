package com.example.realestate.model;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

}
