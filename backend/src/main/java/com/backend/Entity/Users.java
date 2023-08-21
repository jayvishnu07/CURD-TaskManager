package com.backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_sequence")

    @Column(name = "user_id")
    private Long userID;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "mail_iD")
    private String mailId;

    @Column(name = "password")
    private String password;
}
