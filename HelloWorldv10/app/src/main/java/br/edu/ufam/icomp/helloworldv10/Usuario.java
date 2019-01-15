package br.edu.ufam.icomp.helloworldv10;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String login, nome, senha;
    private int tipo;

    public Usuario(String login, String nome, String senha, int tipo) {
        this.login = login;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTipoString() {
        if (this.tipo == 1) return "Aluno";
        else return "Professor";
    }
}