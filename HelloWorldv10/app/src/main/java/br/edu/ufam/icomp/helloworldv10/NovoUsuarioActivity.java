package br.edu.ufam.icomp.helloworldv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class NovoUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);
    }


    public void cadastrarUsuario(View view) {
        EditText inputLogin = (EditText) findViewById(R.id.editNovoLogin);
        EditText inputNome = (EditText) findViewById(R.id.editNovoNome);
        EditText inputSenha = (EditText) findViewById(R.id.editNovoSenha);
        RadioButton radioAluno = (RadioButton) findViewById(R.id.radioNovoAluno);

        Usuario usuario = new Usuario(inputLogin.getText().toString(),
                inputNome.getText().toString(),
                inputSenha.getText().toString(),
                radioAluno.isChecked() ? 1 : 2);

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        if (usuarioDAO.addUsuario(usuario)) {
            Toast.makeText(this, "Usuário criado!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
        }

    }

}
