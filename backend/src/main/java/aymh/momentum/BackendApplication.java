package aymh.momentum;

import aymh.momentum.security.bean.Role;
import aymh.momentum.security.service.facade.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RoleService service){
        return args -> {
            if (service.findByAuthority("ROLE_USER").isEmpty()) {
                Role user = new Role();
                user.setAuthority("ROLE_USER");
                user.setColor("#3B82F6");
                service.save(user);
            }
            if (service.findByAuthority("ROLE_ADMIN").isEmpty()) {
                Role admin = new Role();
                admin.setAuthority("ROLE_ADMIN");
                admin.setColor("#EF4444");
                service.save(admin);
            }
        };
    }
}
