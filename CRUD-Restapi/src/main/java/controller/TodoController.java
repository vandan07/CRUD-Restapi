package controller;
import models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import respository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoRepository todoRepo;
    @GetMapping("/")
    public ResponseEntity<?> getAllTodos(){
        List<Todo> todos = todoRepo.findAll();
        if(todos.size()>0){
            List<Todo> send = new ArrayList<>();
            for(Todo t :todos){
                if(t.isDel()==false){
                    send.add(t);
                }
            }
            return new ResponseEntity<List<Todo>>(send, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Empty",HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> createTodo(@RequestBody Todo todo){
        try{
           todoRepo.save(todo);
           return new ResponseEntity<Todo>(todo ,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PutMapping("/{id}/del")
    public ResponseEntity<?> flagid(@PathVariable("id") Integer id , @RequestBody Todo todo){
        Optional<Todo> todoup= todoRepo.findById(id);
        if(todoup.isPresent()){
            Todo todosave = todoup.get();
            todosave.setDel(todo.isDel());
            todoRepo.save(todosave);
            return new ResponseEntity<>(todosave,HttpStatus.OK);

        }else{
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateid(@PathVariable("id") Integer id , @RequestBody Todo todo){
        Optional<Todo> todoup= todoRepo.findById(id);
        if(todoup.isPresent()){
            Todo todosave = todoup.get();
            todosave.setWork(todo.getWork()!=null ? todo.getWork() : todosave.getWork());
            todosave.setDel(todo.isDel());
            todoRepo.save(todosave);
            return new ResponseEntity<>(todosave,HttpStatus.OK);

        }else{
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deletebyid(@PathVariable("id") Integer id  ){
//        try{
//            todoRepo.deleteById(id);
//            return new ResponseEntity<>("Deleted",HttpStatus.OK);
//        }catch(Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//        }
//    }



}
