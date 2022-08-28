package apiamadeu.github.users.api.controller;

import apiamadeu.github.users.api.model.UserEntity;
import apiamadeu.github.users.api.repository.Repository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.riversun.promise.Func;
import org.riversun.promise.Promise;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class DBUsersController {


    @Autowired
    private Repository repository;
    private RestTemplate template = new RestTemplate();

    @GetMapping(path = "/api/users")
    public List<UserEntity> getUsers(){
        return repository.findAll();
    }

    @GetMapping(path = "/api/users/{login}")
    public UserEntity getUserByLogin(@PathVariable("login") String login){
        return repository.findById(login).get();
    }

    @PostMapping(path = "/api/users")
    public boolean addUser(@RequestBody UserEntity user){
        if(repository.existsById(user.getLogin())) return false;
        repository.save(user);
        return true;
    }

    @PostMapping(path = "/api/github/{login}")
    public boolean addUserFromGithub(@PathVariable("login") String login){
        String uri = "https://api.github.com/users/" + login;
        Func searchUser = (action, data) -> {
            ResponseEntity<UserEntity> entity = template.getForEntity(uri, UserEntity.class);
            System.out.println(entity.getStatusCode());
            action.resolve(entity.getBody());
        };

        Func saveUser = (action, data) -> {
            repository.save((UserEntity) data);
        };
        if(!repository.existsById(login)){
            Promise.resolve().then(searchUser).then(saveUser).start();
            return true;
        }
        return false;
    }

    @PutMapping(path = "/api/users/{login}")
    public boolean modifyUser(@PathVariable("login") String login, @RequestBody UserEntity user){
        if(repository.existsById(login)){
            repository.save(user);
            return true;
        }
        return false;
    }

    @DeleteMapping(path = "/api/users/{login}")
    public boolean deleteUser(@PathVariable("login") String login){
        if(repository.existsById(login)){
            repository.deleteById(login);
            return true;
        }
        return false;
    }

    @DeleteMapping(path = "api/users")
    public boolean deleteAllUsers(){
        repository.deleteAll();
        return repository.findAll().isEmpty();
    }

}
