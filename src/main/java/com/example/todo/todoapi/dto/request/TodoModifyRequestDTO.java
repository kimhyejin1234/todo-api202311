package com.example.todo.todoapi.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.security.PrivateKey;
@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder @EqualsAndHashCode
public class TodoModifyRequestDTO {

    @NotBlank
    private  String id;
    private  boolean done;

}
