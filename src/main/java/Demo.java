import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.MessageFormat;

public class Demo {
    public static void main(String[] args) {
        //mqtt broker地址与端口号
        String broker = "tcp://39.97.118.148:1883";
        //mqtt客户端id
        String clientId = "JavaClient";

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker:" + broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected......");

            String topic = "demo/java";
            System.out.println("Subscribe to topic:" + topic);
            mqttClient.subscribe(topic);
            mqttClient.setCallback(new MqttCallback() {
                public void connectionLost(Throwable throwable) {

                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String theMsg = MessageFormat.format("{0} is arrived for topic {1}.", new String(message.getPayload()), topic);
                    System.out.println(theMsg);
                }

                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

            String content = "Hello I am Java.....";
            int qos = 2;
            System.out.println("Publish message:" + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
            System.out.println("Message published");

        } catch (MqttException e) {
            System.out.println("reason" + e.getReasonCode());
            System.out.println("msg" + e.getMessage());
            System.out.println("loc" + e.getLocalizedMessage());
            System.out.println("cause" + e.getCause());
            System.out.println("excep" + e);
            e.printStackTrace();
        }
    }
}
