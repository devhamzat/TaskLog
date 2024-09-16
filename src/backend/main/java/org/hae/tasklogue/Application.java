package org.hae.tasklogue;

import org.hae.tasklogue.entity.Role;
import org.hae.tasklogue.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;


@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode =
        VIA_DTO)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            Role role = new Role();
            role.setName("USER");
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(
                        role
                );
            }
        };
    }

}
