package devandroid.micaela.tcc_agendamed.model;

import java.io.Serializable;

public class Usuario{
    private String nome;
    private int id;
    public Usuario(int id, String nome){
        this.id = id;
        this.nome = nome;
    }
    public Usuario(String nome){
        this.nome = nome;
    }

    //  --- Getters & Setters ---
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
