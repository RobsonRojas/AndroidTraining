package br.edu.ufam.icomp.helloworldv10;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuarioDAO {
    private SQLiteDatabase bancoDeDados;

    public UsuarioDAO(Context context) {
        this.bancoDeDados = (new BancoDeDados(context)).getWritableDatabase();
    }

    public Usuario getUsuario(String login, String senha) {
        Usuario usuario = null;

        String sqlQuery = "SELECT * FROM Usuarios WHERE " +
                "login='" + login + "' AND senha='" + senha + "'";

        Cursor cursor = this.bancoDeDados.rawQuery(sqlQuery, null);

        if (cursor.moveToNext()) {
            usuario = new Usuario(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3));
        }

        cursor.close();
        return usuario;
    }
    public boolean addUsuario(Usuario u) {

        try {
            String sqlCmd = "INSERT INTO Usuarios VALUES ('" +
                    u.getLogin() + "'," + " '" + u.getNome() + "', '" +
                    u.getSenha() + "', " + u.getTipo() + ")";
            this.bancoDeDados.execSQL(sqlCmd);
            return true;
        }
        catch (SQLException e) {
            Log.e("HelloAppBD", e.getMessage());
            return false;
        }

    }

    public Cursor getUsuarios() {
        return this.bancoDeDados.rawQuery("SELECT rowid AS _id, " +
                "login, nome, " +
                "CASE WHEN tipo=1 THEN 'Aluno' ELSE 'Professor' END AS tipo " +
                "FROM Usuarios ORDER BY login", null);
    }

    public Usuario[] getUsuariosArray() {
        String sqlQuery = "SELECT * FROM Usuarios";
        Cursor cursor = this.bancoDeDados.rawQuery(sqlQuery, null);
        Usuario[] usuarios = new Usuario[cursor.getCount()];

        for (int i=0; i< cursor.getCount(); i++) {
            cursor.moveToNext();
            usuarios[i] = new Usuario(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3));
        }

        cursor.close();
        return usuarios;
    }
}
