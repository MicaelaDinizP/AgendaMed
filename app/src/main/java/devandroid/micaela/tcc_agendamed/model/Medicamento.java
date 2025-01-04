package devandroid.micaela.tcc_agendamed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Medicamento implements Parcelable {
    private long id = -1;
    private String nomeMedicamento;
    private Usuario usuario;
    private int quantidadeDosesPorEmbalagem;
    private List<DiaDaSemana> diasDaSemana;
    private List<String> listaHorarios;
    private int dosesPorDia;
    private int quantidadeEstoqueCritico;
    private int quantidadeDosesRestantes;
    private boolean usoPausado = false;
    private boolean criarAlarmes = false;
    private boolean alarmeAtivo = false;

    public Medicamento(long id, String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
        this.id = id;
    }

    public Medicamento(String nomeMedicamento, Usuario usuario, int quantidadeDosesPorEmbalagem, List<DiaDaSemana> diasDaSemana, int dosesPorDia,
                       int quantidadeEstoqueCritico, int quantidadeDosesRestantes, boolean usoPausado, boolean criarAlarmes,
                       boolean alarmeAtivo, List<String> listaHorarios) {
        this.nomeMedicamento = nomeMedicamento;
        this.usuario = usuario;
        this.quantidadeDosesPorEmbalagem = quantidadeDosesPorEmbalagem;
        this.diasDaSemana = diasDaSemana;
        this.dosesPorDia = dosesPorDia;
        this.quantidadeEstoqueCritico = quantidadeEstoqueCritico;
        this.quantidadeDosesRestantes = quantidadeDosesRestantes;
        this.usoPausado = usoPausado;
        this.criarAlarmes = criarAlarmes;
        this.alarmeAtivo = alarmeAtivo;
        this.listaHorarios = listaHorarios;
    }
    protected Medicamento(Parcel in) {
        id = in.readLong();
        nomeMedicamento = in.readString();
        usuario = in.readParcelable(Usuario.class.getClassLoader());
        quantidadeDosesPorEmbalagem = in.readInt();
        diasDaSemana = in.createTypedArrayList(DiaDaSemana.CREATOR);
        listaHorarios = in.createStringArrayList();
        dosesPorDia = in.readInt();
        quantidadeEstoqueCritico = in.readInt();
        quantidadeDosesRestantes = in.readInt();
        usoPausado = in.readByte() != 0;
        criarAlarmes = in.readByte() != 0;
        alarmeAtivo = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nomeMedicamento);
        dest.writeParcelable(usuario, flags);
        dest.writeInt(quantidadeDosesPorEmbalagem);
        dest.writeTypedList(diasDaSemana);
        dest.writeStringList(listaHorarios);
        dest.writeInt(dosesPorDia);
        dest.writeInt(quantidadeEstoqueCritico);
        dest.writeInt(quantidadeDosesRestantes);
        dest.writeByte((byte) (usoPausado ? 1 : 0));
        dest.writeByte((byte) (criarAlarmes ? 1 : 0));
        dest.writeByte((byte) (alarmeAtivo ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Medicamento> CREATOR = new Creator<Medicamento>() {
        @Override
        public Medicamento createFromParcel(Parcel in) {
            return new Medicamento(in);
        }

        @Override
        public Medicamento[] newArray(int size) {
            return new Medicamento[size];
        }
    };

    // Getters & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getQuantidadeDosesPorEmbalagem() {
        return quantidadeDosesPorEmbalagem;
    }

    public void setQuantidadeDosesPorEmbalagem(int quantidadeDosesPorEmbalagem) {
        this.quantidadeDosesPorEmbalagem = quantidadeDosesPorEmbalagem;
    }

    public List<DiaDaSemana> getDiasDaSemana() {
        return diasDaSemana;
    }

    public void setDiasDaSemana(List<DiaDaSemana> diasDaSemana) {
        this.diasDaSemana = diasDaSemana;
    }

    public int getDosesPorDia() {
        return dosesPorDia;
    }

    public void setDosesPorDia(int dosesPorDia) {
        this.dosesPorDia = dosesPorDia;
    }

    public int getQuantidadeEstoqueCritico() {
        return quantidadeEstoqueCritico;
    }

    public void setQuantidadeEstoqueCritico(int quantidadeEstoqueCritico) {
        this.quantidadeEstoqueCritico = quantidadeEstoqueCritico;
    }

    public int getQuantidadeDosesRestantes() {
        return quantidadeDosesRestantes;
    }

    public void setQuantidadeDosesRestantes(int quantidadeDosesRestantes) {
        if(quantidadeDosesRestantes >= 0){
            this.quantidadeDosesRestantes = quantidadeDosesRestantes;
        }
    }

    public List<String> getListaHorarios() {
        return listaHorarios;
    }

    public void setListaHorarios(List<String> listaHorarios) {
        this.listaHorarios = listaHorarios;
    }

    public boolean isUsoPausado() {
        return usoPausado;
    }

    public void setUsoPausado(boolean usoPausado) {
        this.usoPausado = usoPausado;
    }

    public boolean isCriarAlarmes() {
        return criarAlarmes;
    }

    public void setCriarAlarmes(boolean criarAlarmes) {
        this.criarAlarmes = criarAlarmes;
    }

    public boolean isAlarmeAtivo() {
        return alarmeAtivo;
    }

    public void setAlarmeAtivo(boolean alarmeAtivo) {
        this.alarmeAtivo = alarmeAtivo;
    }
}