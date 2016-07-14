package com.marantle.nutcracker.configuration;

import com.marantle.nutcracker.parser.DataParser;
import com.marantle.nutcracker.repository.NutRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Marko on 13.7.2016.
 */
@Configuration
public class NutcrackerConfiguration{

    @Bean
    public DataParser createDataParser(){
        return new DataParser();
    }


}
