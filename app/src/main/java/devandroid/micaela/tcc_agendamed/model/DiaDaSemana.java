package devandroid.micaela.tcc_agendamed.model;

import java.util.Locale;

public enum DiaDaSemana {
    SEGUNDA(1),
    TERCA(2),
    QUARTA(3),
    QUINTA(4),
    SEXTA(5),
    SABADO(6),
    DOMINGO(7);

    private int valor;

    DiaDaSemana(int valor) {
        this.valor = valor;
    }
    public int getValor() {
        return valor;
    }

    public static DiaDaSemana getDiaDaSemana(String dia){
        DiaDaSemana diaCorrespondente = null;
        switch(dia.toUpperCase(Locale.ROOT)){
            case "SEGUNDA":
                diaCorrespondente = SEGUNDA;
                break;
            case "TERCA":
                diaCorrespondente = TERCA;
                break;
            case "QUARTA":
                diaCorrespondente = QUARTA;
                break;
            case "QUINTA":
                diaCorrespondente = QUINTA;
                break;
            case "SEXTA":
                diaCorrespondente = SEXTA;
                break;
            case "SABADO":
                diaCorrespondente = SABADO;
                break;
            case "DOMINGO":
                diaCorrespondente = DOMINGO;
        }
        return diaCorrespondente;
    }
}

