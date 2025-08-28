package jsh.todolist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Schema(description = "할일 생성/수정 DTO")
public class TodoRequestDto {

    @Schema(description = "할일 내용", example = "스프링 강의 듣기")
    private String content;

    @Schema(description = "마감 기한", example = "2025-09-07")
    private LocalDateTime deadline;
}
