package com.example.todo.todoapi.api;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final  TodoService todoService;

    //할일 등록 요청
    @PostMapping
    public ResponseEntity<?> createTodo(
           @Validated @RequestBody TodoCreateRequestDTO requestDTO,
           BindingResult result
    ){
        if(result.hasErrors()){
            log.warn("DTO검증 에러 발생: {}" , result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }
        try {
            TodoListResponseDTO responseDTO = todoService.create(requestDTO);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace(); //e.PRINTsTACKtRACE();
            return  ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO
                            .builder()
                            .error(e.getMessage())
                            .build());
        }
    }

    //할일 목록 요청
    @GetMapping
    public  ResponseEntity<?> retrieveTodoList(){
        log.info("/api/todos GET request");
        TodoListResponseDTO responseDTO = todoService.retrieve();

        return  ResponseEntity.ok().body(responseDTO);
    }


    //할 일 삭제 요청
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteTodo(
            @PathVariable("id") String todoId
    ){
        log.info("/api/todos/{} delete request!", todoId);

        if(todoId == null || todoId.trim().equals("")){
            return  ResponseEntity
                    .badRequest()
                    .body(TodoListResponseDTO
                            .builder()
                            .error("ID를 전달해 주세요")
                            .build());
        }

        try {
            TodoListResponseDTO responseDTO =  todoService.delete(todoId);
            return  ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(TodoListResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    //할일 수정하기
    @RequestMapping(method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity<?> updateTodo (
            @Validated @RequestBody TodoModifyRequestDTO requestDTO,
            BindingResult result,
            HttpServletRequest request
            ){
        if(result.hasErrors()){
            return  ResponseEntity
                    .badRequest()
                    .body(request.getTrailerFields());
        }
        log.info("/api/todos {} request! " , request.getMethod());
        log.info("modifying dto: {}",requestDTO);

        try {
            TodoListResponseDTO responseDTO =  todoService.update(requestDTO);
            return  ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            return  ResponseEntity
                    .internalServerError()
                    .body(TodoListResponseDTO
                            .builder()
                            .error(e.getMessage()).build());
        }
    }
}














