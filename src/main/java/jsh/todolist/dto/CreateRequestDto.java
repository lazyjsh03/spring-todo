package jsh.todolist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Schema(description = "할 일 추가 요청 DTO")
public class CreateRequestDto {

    @Schema(description = "할 일 내용", example = "스프링 공부하기")
    private String content;

    @Schema(description = "마감 기한", example = "2025-09-07")
    private Date deadline;
}
