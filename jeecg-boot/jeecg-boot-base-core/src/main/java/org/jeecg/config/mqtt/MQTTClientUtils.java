package org.jeecg.config.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * MQTT 客户端工具类
 */
@Component
public class MQTTClientUtils {

    private MqttClient mqttClient;

    @Autowired
    private MQTTConfigBuilder mqttConfig;

    @Autowired
    private MQTTMessageListener messageListener;

    @PostConstruct
    public void initClient() {
        try {
            mqttClient = new MqttClient(mqttConfig.getHost(), mqttConfig.getClientId());
            MqttConnectOptions options = mqttConfig.getOptions();
            options.setCleanSession(true);

            mqttClient.connect(options);
            System.out.println("MQTT 客户端已连接");

            mqttClient.subscribe("Bug", 1, messageListener);
            System.out.println("成功订阅主题: Bug");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("MQTT 客户端初始化失败: " + e.getMessage());
        }
    }
}
