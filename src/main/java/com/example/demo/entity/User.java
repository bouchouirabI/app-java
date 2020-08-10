package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String name;

   @Column(name="last_name")
   private String lastName;

   @Column(name="pseudo_name")
   private String pseudoName;

   private String email;

   private String password;

   @Column(name="is_email_verified")
   private Boolean isEmailVerified;

}
