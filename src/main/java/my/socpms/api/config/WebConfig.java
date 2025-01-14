package my.socpms.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Handle webjars
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(true);
        
        // Handle static resources
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true);

        // Handle other common paths
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }
}