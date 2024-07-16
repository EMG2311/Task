package com.task.demo.service.implementation;

import com.task.demo.configuration.JWT.JwtUtils;
import com.task.demo.dto.*;
import com.task.demo.model.Task;
import com.task.demo.model.Usuario;
import com.task.demo.repository.TaskRepository;
import com.task.demo.repository.UsuarioRepository;
import com.task.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplemmentation implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final static  Logger LOGGER=  LoggerFactory.getLogger(TaskServiceImplemmentation.class);
    @Autowired
    private JwtUtils jwtUtils;

    private static String ROLE_ADMIN="ROLE_ADMIN";

    @Override
    public TaskResCrearDto crearTask(TaskCrearDto taskDto, String token) {
        try {
            LOGGER.info("----------Buscando usuario----------");
            Usuario usuario=usuarioRepository.findByMail(jwtUtils.getMailFromToken(token)).get();
            if (usuario!=null){
                taskRepository.save(new Task(taskDto.getNombre(), taskDto.getDescripcion(), false, usuario));
                LOGGER.info("----------Tarea creada correctamente----------");
            }
            else{
                throw new Exception("El usuario no se encontro");
            }
            return new TaskResCrearDto(null,taskDto.getNombre(),taskDto.getDescripcion(),usuario.getId(),false,0,"Operacion realizada con exito");
        }catch (Exception e){
            LOGGER.error("No se pudo crear la tarea: "+ e.getMessage());
            return new TaskResCrearDto(null,taskDto.getNombre(),taskDto.getDescripcion(),null,false,-1,e.getMessage());
        }
    }

    @Override
    public TaskResEliminarDto eliminarTask(Integer id,String token) {
        String nombreTarea = "Tarea ";
        try {
            if (jwtUtils.getRoles().contains(ROLE_ADMIN)) {
                LOGGER.info("----------Buscando tarea----------");
                nombreTarea = nombreTarea + taskRepository.findById(id).get().getNombre();
                LOGGER.info("----------Eliminando tarea----------");
                taskRepository.deleteById(id);
                nombreTarea = nombreTarea + "se elimino";
                return new TaskResEliminarDto(nombreTarea, 0, "Operacion realizada con exito");
            }
            else{
                LOGGER.info("----------Buscando tarea----------");
                Task task=taskRepository.findById(id).get();
                nombreTarea = nombreTarea + task.getNombre();
                if(UserHasTask(task,token)){
                    taskRepository.deleteById(id);
                    return new TaskResEliminarDto(nombreTarea, 0, "Operacion realizada con exito");
                }
                else{
                    LOGGER.info("La tarea no existe o no pertenece al usuario");
                    throw new RuntimeException("La tarea no existe o no pertenece al usuario");
                }
            }
        }catch (Exception e){
            LOGGER.error("Error al eliminar tarea "+ e.getMessage());
            return new TaskResEliminarDto("Error al eliminar tarea",-1,e.getMessage());
        }
    }

    public boolean UserHasTask(Task task,String token){
        Usuario usuario=usuarioRepository.findByMail(jwtUtils.getMailFromToken(token)).get();
        LOGGER.info("----------Buscando tareas del usuario----------");
        List<Task> tasks= taskRepository.findByUsuario(usuario);
        if(task!= null && tasks.contains(task)) {
            return true;
        }
        return false;
    }


    @Override
    public TaskResCrearDto modificarTask(TaskModifDto taskModifDto,String token) {
        try{
            if(jwtUtils.getRoles().contains(ROLE_ADMIN)){
                LOGGER.info("----------Buscando la tarea a modificar----------");
                Task task = taskRepository.findById(taskModifDto.getIdTask()).get();
                LOGGER.info("Tarea encontrada");
                task.setNombre(taskModifDto.getNombre());
                task.setDescripcion(taskModifDto.getDescripcion());
                LOGGER.info("----------Guardando cambios----------");
                taskRepository.save(task);
                return new TaskResCrearDto(taskModifDto.getIdTask(),taskModifDto.getNombre(),taskModifDto.getDescripcion(),task.getUsuario().getId(),task.getCheck(),0,"Se modifico correctamente la tarea");

            }else{
                LOGGER.info("----------Buscando la tarea a modificar----------");
                Task task = taskRepository.findById(taskModifDto.getIdTask()).get();
                LOGGER.info("Tarea encontrada");
                if(UserHasTask(task,token)){
                    task.setNombre(taskModifDto.getNombre());
                    task.setDescripcion(taskModifDto.getDescripcion());
                    LOGGER.info("----------Guardando cambios----------");
                    taskRepository.save(task);
                    return new TaskResCrearDto(taskModifDto.getIdTask(),taskModifDto.getNombre(),taskModifDto.getDescripcion(),task.getUsuario().getId(),task.getCheck(),0,"Se modifico correctamente la tarea");
                }else{
                    LOGGER.info("La tarea no existe o no pertenece al usuario");
                    throw new RuntimeException("La tarea no existe o no pertenece al usuario");
                }

            }


        }catch (Exception e){
            LOGGER.error("Error modificando la tarea "+ e.getMessage());
            return new TaskResCrearDto(taskModifDto.getIdTask(),taskModifDto.getNombre(),taskModifDto.getDescripcion(),null,null,-1,"No se pudo modificar la tarea: "+e.getMessage());
        }
    }

    @Override
    public TaskResCrearDto completarTask(Integer id,String token) {
        try{
            if(jwtUtils.getRoles().contains(ROLE_ADMIN))
            {
                LOGGER.info("Buscando tarea para completarla");
                Task task= taskRepository.findById(id).get();
                task.setCheck(true);
                taskRepository.save(task);
                LOGGER.info("----------Tarea completada----------");
                return new TaskResCrearDto(task.getId(),task.getNombre(),task.getDescripcion(),task.getUsuario().getId(),task.getCheck(),0,"Se completo correctametne la tarea");
            }else{
                LOGGER.info("Buscando tarea para completarla");
                Task task=taskRepository.findById(id).get();
                if(UserHasTask(task,token)){
                    task.setCheck(true);
                    taskRepository.save(task);
                    LOGGER.info("----------Tarea completada----------");
                    return new TaskResCrearDto(task.getId(),task.getNombre(),task.getDescripcion(),task.getUsuario().getId(),task.getCheck(),0,"Se completo correctametne la tarea");
                }else{
                    LOGGER.info("La tarea no existe o no pertenece al usuario");
                    throw new RuntimeException("La tarea no existe o no pertenece al usuario");
                }
            }

        }catch (Exception e){
            LOGGER.error("Error completando tarea "+e.getMessage());
            return new TaskResCrearDto(id,null,null,null,null,-1,"Error completando la tarea" + e.getMessage());
        }

    }

    @Override
    public List<TaskResCrearDto> listarPorUsuario(Integer id) {
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

    @Override
    public List<TaskResCrearDto> listarPorUsuario(String tokenHeader) {
        List<TaskResCrearDto> taskResCrearDtoList = null;
        try {
            taskResCrearDtoList = new ArrayList<>();
            LOGGER.info("----------Buscando usuario por mail del token----------");
            Usuario usuario = usuarioRepository.findByMail(jwtUtils.getMailFromToken(tokenHeader)).get();
            LOGGER.info("----------Buscando tareas del usuario----------");
            List<Task> taskList = taskRepository.findByUsuario(usuario);
            for (Task t : taskList) {
                TaskResCrearDto taskResCrearDto = new TaskResCrearDto();
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
        } catch (Exception e) {
            LOGGER.error("No se pudo listar por Usuario " + e.getMessage());
            TaskResCrearDto error = new TaskResCrearDto(null, null, null, null, null, -1, "No se pudo listar por usuario " + e.getMessage());
            taskResCrearDtoList.add(error);
            return taskResCrearDtoList;
        }
    }

    @Override
    public List<TaskDto> listarTasks() {
        try {
            LOGGER.info("Busacando todas las tareas");
            List<Task> tasks = taskRepository.findAll();
            List<TaskDto> lTaskCrear = new ArrayList<>();
            for (Task i : tasks) {
                TaskDto taskDto = new TaskDto();
                taskDto.setId(i.getId());
                taskDto.setNombre(i.getNombre());
                taskDto.setDescripcion(i.getDescripcion());
                taskDto.setCheck(i.getCheck());
                lTaskCrear.add(taskDto);
            }
            return lTaskCrear;
        }catch (Exception e){
            return new ArrayList<TaskDto>();
        }
    }
}
