package com.task.demo.service;

import com.task.demo.dto.*;

import java.util.List;

public interface TaskService {
    TaskResCrearDto crearTask(TaskCrearDto taskDto,String token);
    TaskResEliminarDto eliminarTask(Integer id, String token);
    TaskResCrearDto modificarTask(TaskModifDto taskDto, String token);
    TaskResCrearDto completarTask(Integer id, String token);
    List<TaskResCrearDto> listarPorUsuario(Integer id);
    List<TaskResCrearDto> listarPorUsuario(String token);
    List<TaskDto> listarTasks();


}
