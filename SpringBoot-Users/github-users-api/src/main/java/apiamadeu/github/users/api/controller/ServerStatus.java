package apiamadeu.github.users.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerStatus {

    @GetMapping(path = "/api/status")
    public String getStatus(){
        return "Server is online";
    }
}
