package jsh.todolist.repository;

import jsh.todolist.domain.Member;
import jsh.todolist.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByMemberOrderByCreatedAtDesc(Member member);
}
