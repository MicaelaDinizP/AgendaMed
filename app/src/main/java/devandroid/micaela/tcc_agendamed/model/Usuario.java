package devandroid.micaela.tcc_agendamed.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String nome;
    private long id = -1;

    public Usuario(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public Usuario(String nome) {
        this.nome = nome;
    }

    // --- Getters & Setters ---
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

        //Parcelable
    protected Usuario(Parcel in) {
        id = in.readLong();
        nome = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nome);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}