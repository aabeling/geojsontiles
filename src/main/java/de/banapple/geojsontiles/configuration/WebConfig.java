package de.banapple.geojsontiles.configuration;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Spring MVC Configuration. Implements {@link WebMvcConfigurer}, which provides
 * convenient callbacks that allow us to customize aspects of the Spring Web MVC
 * framework. These callbacks allow us to register custom interceptors, message
 * converters, argument resovlers, a validator, resource handling, and other
 * things.
 * 
 * @see WebMvcConfigurer
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "de.banapple.geojsontiles")
public class WebConfig
    extends WebMvcConfigurerAdapter
{

    @Override
    public void configureDefaultServletHandling(
        DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }
    
}