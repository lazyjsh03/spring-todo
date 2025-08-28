erDiagram
MEMBER {
Long member_id PK "기본키 (Auto-increment)"
String username UK "사용자 이름/이메일 (Unique)"
String password "암호화된 비밀번호"
}

    TODO {
        Long todo_id PK "기본키 (Auto-increment)"
        String content "할일 내용"
        Boolean completed "완료 여부"
        LocalDateTime deadline "마감 기한"
        LocalDateTime createdAt "생성 시간 (자동 생성)"
        LocalDateTime updatedAt "수정 시간 (자동 생성)"
        Long member_id FK "MEMBER 외래키"
    }

    MEMBER ||--|{ TODO : "has"