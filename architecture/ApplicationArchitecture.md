graph TD
subgraph "Client (Browser/Mobile)"
Client[💻<br>Swagger UI / Postman]
end

    subgraph "Spring Boot Server"
        Client -- HTTP Request --> FChain[Security Filter Chain]
        
        subgraph "Spring Security"
            FChain -- 1. 요청 가로채기 --> JWTFilter(JwtAuthenticationFilter)
            JWTFilter -- 2. 토큰 검증 --> JwtUtil
            JWTFilter -- 3. 사용자 정보 조회 --> UDS(UserDetailsServiceImpl)
            UDS -- 4. DB에서 사용자 확인 --> MemberRepoSec[MemberRepository]
            JWTFilter -- 5. 인증 정보 저장 --> SC[SecurityContextHolder]
        end

        FChain -- 6. 인증 완료 후 요청 전달 --> Controller(Controller Layer)
        
        subgraph "Application Layers"
            Controller -- 7. 비즈니스 로직 호출 --> Service(Service Layer)
            Service -- 8. 데이터 요청 --> Repository(Repository Layer)
            Repository -- 9. JPA / Hibernate --> DB[(💾 H2 Database)]
            DB -- 10. 데이터 반환 --> Repository
            Repository -- 11. Entity 반환 --> Service
            Service -- 12. DTO 변환 후 반환 --> Controller
        end
        
        Controller -- 13. HTTP Response --> Client
    end

    style Client fill:#D6EAF8,stroke:#3498DB
    style FChain fill:#E8DAEF,stroke:#8E44AD
    style JWTFilter fill:#FADBD8,stroke:#C0392B
    style Controller fill:#D5F5E3,stroke:#2ECC71
    style Service fill:#FCF3CF,stroke:#F1C40F
    style Repository fill:#FDEDEC,stroke:#E74C3C
    style DB fill:#E5E7E9,stroke:#839192