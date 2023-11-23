package com.example.todo.todoapi.dto.response;


import lombok.*;

@Setter @Getter @ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor @Builder
public class TodoDetailResponseDTO {
    private  String id;
    private  String title;
    private  boolean done;

    public TodoDetailResponseDTO(Todo todo) {
        this.id = getTodoId();
        this.title = getT
                this.done = todo.isDone();
    }
}
