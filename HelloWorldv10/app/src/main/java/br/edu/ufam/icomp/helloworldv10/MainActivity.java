package br.edu.ufam.icomp.helloworldv10;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("HelloDebug", "Activity principal iniciada!");
    }

    public void entrarClicado(View view) {
        Intent intent = new Intent(this, BemVindoActivity.class);

        EditText inputLogin = (EditText) findViewById(R.id.editLogin);
        EditText inputSenha = (EditText) findViewById(R.id.editSenha);

        // Verifica a senha
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.getUsuario(inputLogin.getText().toString(),
                inputSenha.getText().toString());

        if (usuario != null) {
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuário e/ou Senha inválidos!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo:
                Intent intent = new Intent(this, NovoUsuarioActivity.class);
                startActivity(intent);
                return true;

            case R.id.sobre:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Hello World App v1.0")
                        .setNeutralButton("Ok", null).show();
                return true;
            case R.id.configs:
                Intent intentConfig = new Intent(this, PreferencesActivity.class);
                startActivity(intentConfig);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("HelloDebug", "Método onStart executado ...");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showLogo = sharedPreferences.getBoolean("logomarca", true);
        String login = sharedPreferences.getString("login", "");
        String tipo = sharedPreferences.getString("tipo", "1");

        ((EditText) findViewById(R.id.editLogin)).setText(login);

        if (tipo.equals("1"))
            ((RadioButton) findViewById(R.id.radioAluno)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.radioProfessor)).setChecked(true);

        if (showLogo)
            findViewById(R.id.imageIComp).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.imageIComp).setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("HelloDebug", "Método onResume executado ...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("HelloDebug", "Método onPause executado ...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("HelloDebug", "Método onStop executado ...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("HelloDebug", "Método onRestart executado ...");
    }

}
