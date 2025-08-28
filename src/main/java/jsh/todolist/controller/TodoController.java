package jsh.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jsh.todolist.dto.TodoRequestDto;
import jsh.todolist.dto.TodoResponseDto;
import jsh.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "할일 API", description = "할일(Todo) CRUD 처리")
@SecurityRequirement(name = "JWT Authentication") // 이 컨트롤러의 모든 API는 JWT 인증 필요
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos") // RESTful하게 복수형으로 변경
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "할일 추가", description = "할일 내용과 마감기한으로 할일을 추가합니다.")
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        TodoResponseDto responseDto = todoService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "내 할일 전체 조회", description = "로그인한 사용자의 모든 할일을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getMyTodos() {
        List<TodoResponseDto> responseDtos = todoService.findAllByUser();
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "할일 상세 조회", description = "ID로 특정 할일을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long id) {
        TodoResponseDto responseDto = todoService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "할일 수정", description = "ID로 특정 할일의 내용과 마감기한을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto responseDto = todoService.update(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "할일 삭제", description = "ID로 특정 할일을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.ok("ID " + id + " 할일 삭제 완료");
    }
}