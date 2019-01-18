package br.edu.ufam.icomp.helloworldv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class BemVindoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        Intent intent = getIntent();

        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");

        if (usuario != null) {
            TextView textBemVindo =
                    (TextView) findViewById(R.id.textBemVindo);

            textBemVindo.setText(Html.fromHtml(
                    "Olá <b>" + usuario.getLogin() +
                            "</b>! Seu nome é <b>" + usuario.getNome() +
                            "</b>, sua senha é <b>" + usuario.getSenha() +
                            "</b> e você é <i>" + usuario.getTipoString() +
                            "</i>.<br><br>Bem Vindo!"));
        }
    }


    public void abrirListarUsuarios(View view) {
        Intent intent = new Intent(this, ListarUsuariosActivity.class);
        startActivity(intent);
    }

}
