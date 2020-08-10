package com.example.demo.repository;

import com.example.demo.entity.Code;
import com.example.demo.entity.CodeStatus;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    List<Code>findByUserAndStatus(User user, CodeStatus status);
    List<Code> findByUser(User user);
    Optional<Code> findByUserAndValueAndStatus(User user, Integer value ,CodeStatus status);
}
