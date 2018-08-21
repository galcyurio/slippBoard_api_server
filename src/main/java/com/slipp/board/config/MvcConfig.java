package com.slipp.board.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @see https://stackoverflow.com/questions/10650196/how-to-configure-mappingjacksonhttpmessageconverter-while-using-spring-annotatio
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private ObjectMapperFactory objectMapperFactory;

    @Primary
    @Bean
    public ObjectMapper objectMapper() throws Exception {
        return this.objectMapperFactory.getObject();
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        try {
            converters.add(mappingJackson2HttpMessageConverter());
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() throws Exception {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = objectMapperFactory.getObject();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

}
