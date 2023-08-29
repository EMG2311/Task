package com.task.demo.controller;

import com.task.demo.dto.TaskCrearDto;
import com.task.demo.dto.TaskModifDto;
import com.task.demo.dto.TaskResCrearDto;
import com.task.demo.dto.TaskResEliminarDto;
import com.task.demo.service.implementation.TaskServiceImplemmentation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskServiceImplemmentation taskServiceImplemmentation;
    @PostMapping("/crear")
    public ResponseEntity<TaskResCrearDto> crearTask(@RequestBody TaskCrearDto taskCrearDto){
        TaskResCrearDto taskResCrearDto= taskServiceImplemmentation.crearTask(taskCrearDto);
        if (taskResCrearDto.getCodigoError()==0){
            return ResponseEntity.ok(taskResCrearDto);
        }else{
            return ResponseEntity.status(409).body(taskResCrearDto);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<TaskResEliminarDto> eliminarTask(@PathVariable Integer id){
        TaskResEliminarDto taskResEliminarDto=taskServiceImplemmentation.eliminarTask(id);
        if (taskResEliminarDto.getCodigoError()==0){
            return ResponseEntity.ok(taskResEliminarDto);
        }
        else{
            return ResponseEntity.status(409).body(taskResEliminarDto);
        }
    }

    @PostMapping("/modificar")
    public ResponseEntity<TaskResCrearDto> modificarTask(@RequestBody TaskModifDto taskModifDto){
        TaskResCrearDto taskResCrearDto= taskServiceImplemmentation.modificarTask(taskModifDto);
        if(taskResCrearDto.getCodigoError()==0){
            return ResponseEntity.ok(taskResCrearDto);
        }
        else{
            return ResponseEntity.status(409).body(taskResCrearDto);
        }
    }
    @PostMapping("/completar/{id}")
    public ResponseEntity<TaskResCrearDto> completarTask(@PathVariable Integer id){
        TaskResCrearDto taskResCrearDto=taskServiceImplemmentation.completarTask(id);
        if(taskResCrearDto.getCodigoError()==0){
            return ResponseEntity.ok(taskResCrearDto);
        }
        else{
            return ResponseEntity.status(409).body(taskResCrearDto);
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<TaskResCrearDto>> listarPorUsuario(@PathVariable Integer id){
        List<TaskResCrearDto> taskResCrearDtoList=taskServiceImplemmentation.listarPorIdUsuario(id);
        for(TaskResCrearDto u: taskResCrearDtoList){
            if(u.getCodigoError()!=0){
                return ResponseEntity.status(409).body(taskResCrearDtoList);
            }
        }
        return ResponseEntity.ok(taskResCrearDtoList);
    }


}
