package com.task.demo.repository;

import com.task.demo.model.Task;
import com.task.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findByUsuario(Usuario usuario);
}
