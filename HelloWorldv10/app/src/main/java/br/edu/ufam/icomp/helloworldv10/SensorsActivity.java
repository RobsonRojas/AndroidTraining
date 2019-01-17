package br.edu.ufam.icomp.helloworldv10;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acel, mag, gir;
    private TextView acelX, acelY, acelZ;
    private TextView magX, magY, magZ;
    private TextView giroX, giroY, giroZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        acel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gir = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        acelX = findViewById(R.id.acelX);
        acelY = findViewById(R.id.acelY);
        acelZ = findViewById(R.id.acelZ);

        magX = findViewById(R.id.magX);
        magY = findViewById(R.id.magY);
        magZ = findViewById(R.id.magZ);

        giroX = findViewById(R.id.giroX);
        giroY = findViewById(R.id.giroY);
        giroZ = findViewById(R.id.giroZ);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acelX.setText(String.format("%.3f", event.values[0]));
            acelY.setText(String.format("%.3f", event.values[1]));
            acelZ.setText(String.format("%.3f", event.values[2]));
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magX.setText(String.format("%.3f", event.values[0]));
            magY.setText(String.format("%.3f", event.values[1]));
            magZ.setText(String.format("%.3f", event.values[2]));
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            giroX.setText(String.format("%.3f", event.values[0]));
            giroY.setText(String.format("%.3f", event.values[1]));
            giroZ.setText(String.format("%.3f", event.values[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mag, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gir, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, acel);
        sensorManager.unregisterListener(this, mag);
        sensorManager.unregisterListener(this, gir);
    }
}
