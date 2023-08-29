package com.task.demo.repository;

import com.task.demo.model.Task;
import com.task.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    //@Query("SELECT * FROM task t WHERE t.tsk_id=id")
    List<Task> findByUsuario(Usuario usuario);
}
