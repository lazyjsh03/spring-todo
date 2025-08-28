package jsh.todolist.service;

import jsh.todolist.domain.Member;
import jsh.todolist.domain.Todo;
import jsh.todolist.dto.TodoRequestDto;
import jsh.todolist.dto.TodoResponseDto;
import jsh.todolist.repository.MemberRepository;
import jsh.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository; // Member를 찾기 위해 추가

    // 할일 생성
    @Transactional
    public TodoResponseDto create(TodoRequestDto requestDto) {
        Member member = getCurrentMember();
        Todo todo = new Todo(requestDto.getContent(), requestDto.getDeadline(), member); // 생성자에 member 추가
        Todo savedTodo = todoRepository.save(todo);
        return new TodoResponseDto(savedTodo);
    }

    // 내 할일 전체 조회
    @Transactional(readOnly = true)
    public List<TodoResponseDto> findAllByUser() {
        Member member = getCurrentMember();
        return todoRepository.findAllByMemberOrderByCreatedAtDesc(member).stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());
    }

    // 할일 상세 조회 (권한 확인)
    @Transactional(readOnly = true)
    public TodoResponseDto findById(Long id) {
        Todo todo = findTodoByIdAndCheckAuth(id);
        return new TodoResponseDto(todo);
    }

    // 할일 수정 (권한 확인)
    @Transactional
    public TodoResponseDto update(Long id, TodoRequestDto requestDto) {
        Todo todo = findTodoByIdAndCheckAuth(id);
        todo.update(requestDto.getContent(), requestDto.getDeadline());
        return new TodoResponseDto(todo);
    }

    // 할일 삭제 (권한 확인)
    @Transactional
    public void delete(Long id) {
        Todo todo = findTodoByIdAndCheckAuth(id);
        todoRepository.delete(todo);
    }

    // 현재 로그인한 사용자 정보 가져오기
    private Member getCurrentMember() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다."));
    }

    // ID로 할일을 찾고, 현재 사용자가 작성한 할일이 맞는지 확인
    private Todo findTodoByIdAndCheckAuth(Long id) {
        Member member = getCurrentMember();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 할일이 없습니다."));
        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 할일에 대한 권한이 없습니다.");
        }
        return todo;
    }
}