package br.edu.ufam.icomp.helloworldv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class NodeActivity extends AppCompatActivity {

    EditText endereco;
    TextView led, ldr;
    MqttClient mqtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        endereco = findViewById(R.id.nodeEndereco);
        endereco.setText("200.17.48.151");
        led = findViewById(R.id.nodeLed);
        ldr = findViewById(R.id.nodeLdr);
    }

    public void atualizarClicado(View view) {
    }

    public void ledClicado(View view) {
    }
}
