package br.edu.ufam.icomp.helloworldv10;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListarUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuariosAdapter adapter;
    private UsuarioDAO usuarioDAO;
    private SimpleCursorAdapter dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        recyclerView = findViewById(R.id.listar_usuarios_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UsuariosAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuariosViewHolder> {

        private Usuario[] usuarios;
        public Context context;

        public UsuariosAdapter(Context context) {
            this.context = context;

            UsuarioDAO usuarioDAO = new UsuarioDAO(context);
            usuarios = usuarioDAO.getUsuariosArray();
        }


        public UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_linha, parent, false);

            UsuariosViewHolder vh = new UsuariosViewHolder(v);
            return new UsuariosViewHolder(v);
        }

        public void onBindViewHolder(UsuariosViewHolder holder, int position) {
            holder.login.setText(usuarios[position].getLogin());
            holder.nome.setText(usuarios[position].getNome());
            holder.tipo.setText(usuarios[position].getTipoString());

            if (usuarios[position].getTipo() == 1) {
                holder.view.setBackgroundColor(Color.parseColor("#ddffdd"));
            }
            else {
                holder.view.setBackgroundColor(Color.parseColor("#ffdddd"));
            }
        }

        public int getItemCount() {
            return usuarios.length;
        }


        public class UsuariosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ConstraintLayout view;
            public TextView login, nome, tipo;

            public UsuariosViewHolder(ConstraintLayout v) {
                super(v);
                view = v;
                login = v.findViewById(R.id.linhaLogin);
                nome = v.findViewById(R.id.linhaNome);
                tipo = v.findViewById(R.id.linhaTipo);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Olá" + this.login.getText().toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
