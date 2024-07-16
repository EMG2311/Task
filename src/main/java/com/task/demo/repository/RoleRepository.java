package com.task.demo.repository;

import com.task.demo.model.ERol;
import com.task.demo.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByName(ERol name);
}
