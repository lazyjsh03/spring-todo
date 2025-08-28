package jsh.todolist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jsh.todolist.domain.Todo;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Schema(description = "할일 응답 DTO")
public class TodoResponseDto {

    private final Long id;
    private final String content;
    private final LocalDateTime deadline;
    private final boolean completed;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.deadline = todo.getDeadline();
        this.completed = todo.isCompleted();
    }
}
