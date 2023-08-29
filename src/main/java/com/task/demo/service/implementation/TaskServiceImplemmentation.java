package com.task.demo.service.implementation;

import com.task.demo.dto.TaskCrearDto;
import com.task.demo.dto.TaskModifDto;
import com.task.demo.dto.TaskResCrearDto;
import com.task.demo.dto.TaskResEliminarDto;
import com.task.demo.mapper.TaskMapper;
import com.task.demo.model.Task;
import com.task.demo.model.Usuario;
import com.task.demo.repository.TaskRepository;
import com.task.demo.repository.UsuarioRepository;
import com.task.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
@Service
public class TaskServiceImplemmentation implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final static  Logger LOGGER=  LoggerFactory.getLogger(TaskServiceImplemmentation.class);

    @Override
    public TaskResCrearDto crearTask(TaskCrearDto taskDto) {
        try {
            LOGGER.info("----------Buscando usuario----------");
            Usuario usuario=usuarioRepository.findById(taskDto.getIdUsuario()).get();
            taskRepository.save(new Task(taskDto.getNombre(), taskDto.getDescripcion(), false, usuario));
            LOGGER.info("----------Tarea creada correctamente----------");
            return new TaskResCrearDto(null,taskDto.getNombre(),taskDto.getDescripcion(),taskDto.getIdUsuario(),false,0,"Operacion realizada con exito");
        }catch (Exception e){
            LOGGER.error("No se pudo crear la tarea: "+ e.getMessage());
            return new TaskResCrearDto(null,taskDto.getNombre(),taskDto.getDescripcion(),taskDto.getIdUsuario(),false,-1,e.getMessage());
        }
    }

    @Override
    public TaskResEliminarDto eliminarTask(Integer id) {
        String nombreTarea = "Tarea ";
        try {
            LOGGER.info("----------Buscando tarea----------");
            nombreTarea=nombreTarea + taskRepository.findById(id).get().getNombre();
            LOGGER.info("----------Eliminando tarea----------");
            taskRepository.deleteById(id);
            nombreTarea=nombreTarea + "se elimino";
            return new TaskResEliminarDto(nombreTarea, 0,"Operacion realizada con exito");
        }catch (Exception e){
            LOGGER.error("Error al eliminar tarea "+ e.getMessage());
            return new TaskResEliminarDto("Error al eliminar tarea",-1,e.getMessage());
        }
    }

    @Override
    public TaskResCrearDto modificarTask(TaskModifDto taskModifDto) {
        try{
            LOGGER.info("----------Buscando la tarea a modificar----------");
            Task task = taskRepository.findById(taskModifDto.getIdTask()).get();
            task.setNombre(taskModifDto.getNombre());
            task.setDescripcion(taskModifDto.getDescripcion());
            LOGGER.info("----------Guardando cambios----------");
            taskRepository.save(task);
            return new TaskResCrearDto(taskModifDto.getIdTask(),taskModifDto.getNombre(),taskModifDto.getDescripcion(),task.getUsuario().getId(),task.getCheck(),0,"Se modifico correctamente la tarea");

        }catch (Exception e){
            LOGGER.error("Error modificando la tarea "+ e.getMessage());
            return new TaskResCrearDto(taskModifDto.getIdTask(),taskModifDto.getNombre(),taskModifDto.getDescripcion(),null,null,-1,"No se pudo modificar el usuario: "+e.getMessage());
        }
    }

    @Override
    public TaskResCrearDto completarTask(Integer id) {
        try{
            LOGGER.info("Buscando tarea para completarla");
            Task task= taskRepository.findById(id).get();
            task.setCheck(true);
            taskRepository.save(task);
            LOGGER.info("----------Tarea completada----------");
            return new TaskResCrearDto(task.getId(),task.getNombre(),task.getDescripcion(),task.getUsuario().getId(),task.getCheck(),0,"Se completo correctametne la tarea");
        }catch (Exception e){
            LOGGER.error("Error completando tarea "+e.getMessage());
            return new TaskResCrearDto(id,null,null,null,null,-1,"Error completando la tarea");
        }

    }

    @Override
    public List<TaskResCrearDto> listarPorIdUsuario(Integer id) {
        List<TaskResCrearDto> taskResCrearDtoList=new ArrayList<>();
        try{
            LOGGER.info("----------Buscando usuario por id----------");
            Usuario usuario=usuarioRepository.findById(id).get();
            LOGGER.info("----------Buscando tareas del usuario----------");
            List<Task>taskList=taskRepository.findByUsuario(usuario);
            for (Task t : taskList){
                TaskResCrearDto taskResCrearDto=new TaskResCrearDto();
                taskResCrearDto.setId(t.getId());
                taskResCrearDto.setNombre(t.getNombre());
                taskResCrearDto.setDescripcion(t.getDescripcion());
                taskResCrearDto.setIdUsuario(t.getUsuario().getId());
                taskResCrearDto.setCheck(t.getCheck());
                taskResCrearDto.setCodigoError(0);
                taskResCrearDto.setMensajeError("");
                taskResCrearDtoList.add(taskResCrearDto);
            }
            return taskResCrearDtoList;

        }catch (Exception e){
            LOGGER.error("No se pudo listar por Usuario " + e.getMessage());
            TaskResCrearDto error= new TaskResCrearDto(null,null,null,null,null,-1,"No se pudo listar por usuario " + e.getMessage());
            taskResCrearDtoList.add(error);
            return taskResCrearDtoList;
        }
    }
}
