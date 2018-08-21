package com.slipp.board.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.slipp.board.model.config.CustomDateTimeDeserializer;
import com.slipp.board.model.config.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperFactory implements FactoryBean<ObjectMapper>, InitializingBean {

    private static SimpleModule dateTimeModule;

    @Override
    public void afterPropertiesSet() throws Exception {
        dateTimeModule = new SimpleModule("dateTimeModule", new Version(0,0,0,null,null,null));
        dateTimeModule.addSerializer(DateTime.class, new CustomDateTimeSerializer());
        dateTimeModule.addDeserializer(DateTime.class, new CustomDateTimeDeserializer());
    }

    @Override
    public ObjectMapper getObject() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(dateTimeModule);

        return objectMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
