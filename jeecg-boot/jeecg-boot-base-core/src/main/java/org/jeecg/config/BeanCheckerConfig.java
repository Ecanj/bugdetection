package org.jeecg.config;

import org.jeecg.config.mqtt.MQTTMessageListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanCheckerConfig {

    @Bean
    public CommandLineRunner checkBeans(ApplicationContext context) {
        return args -> {
            System.out.println("Available MQTTMessageListener beans: ");
            String[] beans = context.getBeanNamesForType(MQTTMessageListener.class);
            for (String bean : beans) {
                System.out.println(bean);
            }
        };
    }
}
