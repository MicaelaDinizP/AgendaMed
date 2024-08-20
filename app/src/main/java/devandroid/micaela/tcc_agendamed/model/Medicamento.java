package devandroid.micaela.tcc_agendamed.model;

import java.util.ArrayList;
import java.util.List;

public class Medicamento {
    private int id = -1;
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

    public Medicamento(String nomeMedicamento, Usuario usuario, int quantidadeDosesPorEmbalagem, List<DiaDaSemana> diasDaSemana,int dosesPorDia,
                       int quantidadeEstoqueCritico, int quantidadeDosesRestantes, boolean usoPausado, boolean criarAlarmes,
                       boolean alarmeAtivo, List<String>listaHorarios){

        this.nomeMedicamento = nomeMedicamento;
        this.usuario = usuario;
        this.quantidadeDosesPorEmbalagem = quantidadeDosesPorEmbalagem;
        this.diasDaSemana = this.diasDaSemana;
        this.dosesPorDia = dosesPorDia;
        this.quantidadeEstoqueCritico = quantidadeEstoqueCritico;
        this.quantidadeDosesRestantes = quantidadeDosesRestantes;
        this.usoPausado = usoPausado;
        this.criarAlarmes = criarAlarmes;
        this.alarmeAtivo = alarmeAtivo;
        this.listaHorarios = listaHorarios;
    }

    //Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
        this.quantidadeDosesRestantes = quantidadeDosesRestantes;
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