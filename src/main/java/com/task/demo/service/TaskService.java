package com.task.demo.service;

import com.task.demo.dto.TaskCrearDto;
import com.task.demo.dto.TaskModifDto;
import com.task.demo.dto.TaskResCrearDto;
import com.task.demo.dto.TaskResEliminarDto;

import java.util.List;

public interface TaskService {
    TaskResCrearDto crearTask(TaskCrearDto taskDto);
    TaskResEliminarDto eliminarTask(Integer id);
    TaskResCrearDto modificarTask(TaskModifDto taskDto);
    TaskResCrearDto completarTask(Integer id);
    List<TaskResCrearDto> listarPorIdUsuario(Integer id);
}
