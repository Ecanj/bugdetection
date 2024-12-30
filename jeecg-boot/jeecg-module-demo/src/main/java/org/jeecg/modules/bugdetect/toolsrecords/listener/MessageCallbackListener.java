//package org.jeecg.modules.bugdetect.toolsrecords.listener;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.jeecg.config.mqtt.MQTTMessageListener;
//import org.jeecg.modules.bugdetect.toolsrecords.entity.ToolRecords;
//import org.jeecg.modules.bugdetect.toolsrecords.service.IToolRecordsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class MessageCallbackListener implements MQTTMessageListener {
//
//    @Autowired
//    private IToolRecordsService toolRecordsService;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void messageArrived(String topic, MqttMessage message) {
//        String messageBody = new String(message.getPayload(), StandardCharsets.UTF_8);
//        log.info("收到消息：主题: {}, 内容: {}", topic, messageBody);
//
//        try {
//            // 将 MQTT 消息解析为 ToolRecords 对象
//            ToolRecords toolRecord = objectMapper.readValue(messageBody, ToolRecords.class);
//
//            // 设置时间戳
//            toolRecord.setDatatime(new Date());
//
//            // 保存到数据库
//            toolRecordsService.save(toolRecord);
//            log.info("消息已成功保存到数据库：{}", toolRecord);
//
//        } catch (Exception e) {
//            log.error("解析或保存消息时发生错误：{}", e.getMessage(), e);
//        }
//    }
//}
package org.jeecg.modules.bugdetect.toolsrecords.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jeecg.config.mqtt.MQTTMessageListener;
import org.jeecg.modules.bugdetect.toolsrecords.entity.ToolRecords;
import org.jeecg.modules.bugdetect.toolsrecords.service.IToolRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class MessageCallbackListener implements MQTTMessageListener {

    @Autowired
    private IToolRecordsService toolRecordsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String messageBody = new String(message.getPayload(), StandardCharsets.UTF_8);
        log.info("收到消息：主题: {}, 内容: {}", topic, messageBody);

        try {
            // JSON 转换为实体类
            ToolRecords toolRecord = objectMapper.readValue(messageBody, ToolRecords.class);

            // 替换 Base64 数据中的换行符，确保数据完整
            if (toolRecord.getImageData() != null) {
                toolRecord.setImageData(toolRecord.getImageData().replace("\n", "").replace("\r", ""));
            }

            // 设置数据时间
            toolRecord.setDatatime(new Date());

            // 保存到数据库
            toolRecordsService.save(toolRecord);
            log.info("消息已成功保存到数据库：{}", toolRecord);

        } catch (Exception e) {
            log.error("解析或保存消息时发生错误：{}", e.getMessage(), e);
        }
    }
}

