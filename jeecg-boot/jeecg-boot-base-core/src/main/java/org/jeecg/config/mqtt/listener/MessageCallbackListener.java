//package org.jeecg.config.mqtt.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.jeecg.config.mqtt.MQTTMessageListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * 消息回调返回
// */
//@Slf4j
//@Component
//public class MessageCallbackListener implements MQTTMessageListener {
//
//    private static final String CALLBACK_URL = "http://localhost:8080/jeecg-boot/api/mqtt/receive";
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        String messageBody = new String(message.getPayload(), StandardCharsets.UTF_8);
//        log.info("订阅的主题: {}, 收到的消息: {}", topic, messageBody);
//        try {
//            restTemplate.postForObject(CALLBACK_URL, messageBody, String.class);
//            log.info("消息已成功发送到回调接口");
//        } catch (Exception e) {
//            log.error("处理MQTT消息时出错: {}", e.getMessage(), e);
//        }
//    }
//}
