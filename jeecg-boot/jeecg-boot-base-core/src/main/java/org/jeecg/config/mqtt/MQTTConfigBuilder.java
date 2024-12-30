package org.jeecg.config.mqtt;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT 配置类
 */
@Configuration
@ConfigurationProperties(prefix = "publish.mqtt")
@Data
public class MQTTConfigBuilder {

    /**
     * 服务端地址
     */
    private String host;

    /**
     * 客户端 ID
     */
    private String clientId;

    /**
     * 连接选项
     */
    private MqttConnectOptions options = new MqttConnectOptions();
}
