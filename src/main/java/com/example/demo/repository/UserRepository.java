package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.tags.form.SelectTag;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

   User findByMailAndPassword(String mail, String password);



}
