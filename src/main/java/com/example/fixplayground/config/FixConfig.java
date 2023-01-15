package com.example.fixplayground.config;

import com.example.fixplayground.client.FixClientApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

import java.io.ByteArrayInputStream;

@Configuration
public class FixConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Initiator conf(FixClientApplication application, @Value("${sess}") String config) throws ConfigError {
        SessionSettings settings = new SessionSettings(new ByteArrayInputStream(config.getBytes()));
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new SLF4JLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();

        return new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
    }
}
