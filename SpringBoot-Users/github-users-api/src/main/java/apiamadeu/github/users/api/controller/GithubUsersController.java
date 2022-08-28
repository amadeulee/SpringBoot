package apiamadeu.github.users.api.controller;

import apiamadeu.github.users.api.model.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GithubUsersController {
    RestTemplate template = new RestTemplate();

    @GetMapping(path = "/github/users")
    public Object[] getUsers(){
        String uri = "https://api.github.com/users";
        return template.getForObject(uri, Object[].class);
    }

    @GetMapping(path = "/github/users/{login}")
    public Object getUserByLogin(@PathVariable("login") String login){
        String uri = "https://api.github.com/users/" + login;
        return template.getForObject(uri, Object.class);
    }
}
