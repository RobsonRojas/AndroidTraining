package br.edu.ufam.icomp.helloworldv10;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NodeActivity extends AppCompatActivity {

    EditText endereco;
    TextView led, ldr;
    final String IP = "192.168.0.31";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        endereco = findViewById(R.id.nodeEndereco);
        endereco.setText(IP);

        led = findViewById(R.id.nodeLed);
        ldr = findViewById(R.id.nodeLdr);
    }

    public void atualizarClicado(View view) {
        AtualizarAsyncTask task = new AtualizarAsyncTask(this);
        task.execute();
    }

    public void ledClicado(View view) {
        int state = view.getId() == R.id.nodeLiga ? 1 : 0;
        LedAsyncTask task = new LedAsyncTask(this, state);
        task.execute();
    }

    private class AtualizarAsyncTask extends AsyncTask<Void, Void, String> {
        NodeActivity activity;

        public AtualizarAsyncTask(NodeActivity nodeActivity) {
            this.activity = nodeActivity;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (activity.endereco != null) {
                    String endereco = activity.endereco.getText().toString();

                    Log.i("HelloDebug", "Enviando requisicao para " + endereco + "..");

                    URL url = new URL("http://" + endereco);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    // Converte a resposta para string
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());

                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null)
                        stringBuilder.append(line);

                    return stringBuilder.toString();
                }

                return null;
            } catch (MalformedURLException e1) {
                Log.e("HelloDebug", "Exceção! " + e1);
                e1.printStackTrace();
            } catch (IOException e1) {
                Log.e("HelloDebug", "Exceção! " + e1);
                e1.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            Log.i("HelloDebug", "Resposta JSON: " + result);

            try {
                JSONObject json = new JSONObject(result);
                activity.led.setText(json.getInt("led") == 1 ? "Ligado" : "Desligado");
                activity.ldr.setText(json.getString("ldr"));

            } catch (JSONException e) {
                Toast.makeText(activity, "Erro ao realizar requisição!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LedAsyncTask extends AsyncTask<Void, Void, String> {
        NodeActivity activity;
        int state;

        public LedAsyncTask(NodeActivity nodeActivity, int state) {
            this.activity = nodeActivity;
            this.state = state;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String endereco = activity.endereco.getText().toString();
                Log.i("HelloDebug", "Enviando requisicao para " + endereco + "..");

                URL url = new URL("http://" + endereco + "/led?state=" + state);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                // Converte a resposta para string
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null)
                    stringBuilder.append(line);

                return stringBuilder.toString();
            } catch (IOException e) {
                Log.e("HelloDebug", "Exceção! " + e);
            }

            return null;
        }

        protected void onPostExecute(String result) {
            Log.i("HelloDebug", "Resposta JSON: " + result);

            try {
                JSONObject json = new JSONObject(result);
                activity.led.setText(state == 1 ? "Ligado" : "Desligado");

            } catch (JSONException e) {
                Toast.makeText(activity, "Erro ao realizar requisição!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
