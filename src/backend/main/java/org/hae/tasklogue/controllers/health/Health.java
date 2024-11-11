package org.hae.tasklogue.controllers.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health {
    @GetMapping(value = "/server-health")
    public String checkServerHealth() {
        return "Server Healthy";
    }

    @GetMapping(value = "/auth/auth-health")
    public String checkAuthHealth() {
        return "auth endpoint Healthy";
    }

    @GetMapping(value = "/tasks/tasks-health")
    public String checkTasksHealth() {
        return "tasks endpoint Healthy";
    }

}
