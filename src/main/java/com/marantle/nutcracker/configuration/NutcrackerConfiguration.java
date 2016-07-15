package com.marantle.nutcracker.configuration;

import com.marantle.nutcracker.parser.DataParser;
import com.marantle.nutcracker.repository.NutRepo;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Configuration
public class NutcrackerConfiguration{

    @Bean
    public DataParser createDataParser(){
        return new DataParser();
    }

}
