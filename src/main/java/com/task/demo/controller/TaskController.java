package com.task.demo.controller;

import com.task.demo.configuration.JWT.JwtUtils;
import com.task.demo.dto.*;
import com.task.demo.model.Task;
import com.task.demo.service.TaskService;
import com.task.demo.service.implementation.TaskServiceImplemmentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")

public class TaskController {

    @Autowired
    private TaskService taskServiceImplemmentation;

    private final static Logger LOGGER=  LoggerFactory.getLogger(TaskServiceImplemmentation.class);


    @PostMapping("/crear")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TaskResCrearDto> crearTask(@RequestBody TaskCrearDto taskCrearDto
                                                    ,@RequestHeader("Authorization") String token) {
        TaskResCrearDto taskResCrearDto = taskServiceImplemmentation.crearTask(taskCrearDto,token.substring(7));
        if (taskResCrearDto.getCodigoError() == 0) {
            return ResponseEntity.ok(taskResCrearDto);
        } else {
            return ResponseEntity.status(409).body(taskResCrearDto);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<TaskResEliminarDto> eliminarTask(@PathVariable Integer id,
                                                            @RequestHeader("Authorization") String token) {
        TaskResEliminarDto taskResEliminarDto = taskServiceImplemmentation.eliminarTask(id,token.substring(7));
        if (taskResEliminarDto.getCodigoError() == 0) {
            return ResponseEntity.ok(taskResEliminarDto);
        } else {
            return ResponseEntity.status(409).body(taskResEliminarDto);
        }
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<TaskResCrearDto> modificarTask(@RequestBody TaskModifDto taskModifDto
                                                        ,@RequestHeader("Authorization")String token) {
        TaskResCrearDto taskResCrearDto = taskServiceImplemmentation.modificarTask(taskModifDto,token.substring(7));
        if (taskResCrearDto.getCodigoError() == 0) {
            return ResponseEntity.ok(taskResCrearDto);
        } else {
            return ResponseEntity.status(409).body(taskResCrearDto);
        }
    }

    @PostMapping("/completar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<TaskResCrearDto> completarTask(@PathVariable Integer id
                                                        ,@RequestHeader("Authorization") String token) {
        TaskResCrearDto taskResCrearDto = taskServiceImplemmentation.completarTask(id,token.substring(7));
        if (taskResCrearDto.getCodigoError() == 0) {
            return ResponseEntity.ok(taskResCrearDto);
        } else {
            return ResponseEntity.status(409).body(taskResCrearDto);
        }
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskResCrearDto>> listarPorUsuario(@PathVariable Integer id) {
        List<TaskResCrearDto> taskResCrearDtoList = taskServiceImplemmentation.listarPorUsuario(id);
        for (TaskResCrearDto u : taskResCrearDtoList) {
            if (u.getCodigoError() != 0) {
                return ResponseEntity.status(409).body(taskResCrearDtoList);
            }
        }
        return ResponseEntity.ok(taskResCrearDtoList);
    }

    @GetMapping("/listar-loggeado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<TaskResCrearDto>> listarPorUsuario(@RequestHeader("Authorization") String token){
        List<TaskResCrearDto> taskResCrearDtoList = taskServiceImplemmentation.listarPorUsuario(token.substring(7));
        for (TaskResCrearDto u : taskResCrearDtoList) {
            if (u.getCodigoError() != 0) {
                return ResponseEntity.status(409).body(taskResCrearDtoList);
            }
        }
        return ResponseEntity.ok(taskResCrearDtoList);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDto>> mostrarTodasLasTask(){
        List<TaskDto> listaTask=taskServiceImplemmentation.listarTasks();
        if(listaTask.isEmpty()){
            return ResponseEntity.status(409).body(listaTask);
        }
        return ResponseEntity.ok(listaTask);
    }


}
