package my.socpms.api.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";  // This should match the template name without .html
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()")
    public String dashboard(Model model) {
        return "dashboard";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")  
    public String profile(Model model) {
        return "profile";
    }
}

