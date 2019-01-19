package br.edu.ufam.icomp.helloworldv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class NodeActivity extends AppCompatActivity implements MqttCallback {

    EditText endereco;
    TextView led, ldr;
    Button conectButton;
    MqttClient mqtt;
    final String clientId = "82202320210Rob";
    final String topic = "82202320210";
    final String IP = "200.17.49.151";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        endereco = findViewById(R.id.nodeEndereco);
        endereco.setText(IP);
        led = findViewById(R.id.nodeLed);
        ldr = findViewById(R.id.nodeLdr);
        conectButton = findViewById(R.id.nodeConectar);
    }

    public void atualizarClicado(View view) {
        try {
            if (mqtt == null) {
                mqtt = new MqttClient("tcp://" + endereco.getText().toString() + ":1883",
                        clientId, new MemoryPersistence());

                mqtt.setCallback(this);
                mqtt.connect();
                mqtt.subscribe(topic);

                conectButton.setText("DESCONECTAR");
            } else {
                mqtt.unsubscribe(topic);
                mqtt.disconnect();
                conectButton.setText("CONECTAR");

                mqtt = null;
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void ledClicado(View view) {
        String cmd = view.getId() == R.id.nodeLiga? "L1" : "L0";
        try {
            if (mqtt != null)
                mqtt.publish(topic, cmd.getBytes(), 1, true);
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d("HelloDebug", "Conexão perdida ..");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.equals(this.topic)) {
            String payload = new String(message.getPayload());

            Log.d("HelloDebug", "Message received: " + payload);
            try {
                if (payload.startsWith("led")) {
                    final String ledValue = payload.substring(5).split(",")[0];
                    final String ldrValue = payload.split(",")[1].substring(5);

                    Log.d("HelloDebug", "Led: " + ledValue + ", LDR: " + ldrValue);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            led.setText(ledValue.equals("1")?"Ligado": "Desligado");
                            ldr.setText(ldrValue);
                        }
                    });

                }
            } catch (Exception e) {
                Log.d("HelloDebug", "EXCEPTION: " + e);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d("HelloDebug", "Mensagem entregue..");
    }
}
