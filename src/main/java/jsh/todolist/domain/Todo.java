package jsh.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    private LocalDateTime deadline;
    private boolean completed = false;

    // 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Todo(String content, LocalDateTime deadline,  Member member) {
        this.content = content;
        this.deadline = deadline;
        this.member = member;
    }

    public void update(String content, LocalDateTime deadline) {
        this.content = content;
        this.deadline = deadline;
    }
}
