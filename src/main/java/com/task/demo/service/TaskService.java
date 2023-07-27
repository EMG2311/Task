package com.task.demo.service;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.task.demo.dto.TaskDto;

public interface TaskService {
    TaskDto crearTask(TaskDto taskDto);
    TaskDto eliminarTask(Integer id);
    TaskDto modificarTask(TaskDto taskDto);
}
