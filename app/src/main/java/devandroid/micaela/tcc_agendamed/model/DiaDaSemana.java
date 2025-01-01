package devandroid.micaela.tcc_agendamed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public enum DiaDaSemana implements Parcelable {
    SEGUNDA(2),
    TERCA(3),
    QUARTA(4),
    QUINTA(5),
    SEXTA(6),
    SABADO(7),
    DOMINGO(1);

    private int valor;

    DiaDaSemana(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static DiaDaSemana getDiaDaSemana(String dia) {
        DiaDaSemana diaCorrespondente = null;
        switch (dia.toUpperCase(Locale.ROOT)) {
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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.valor);
    }
    public static final Creator<DiaDaSemana> CREATOR = new Creator<DiaDaSemana>() {
        @Override
        public DiaDaSemana createFromParcel(Parcel in) {
            int valor = in.readInt();
            return fromInt(valor);
        }
        @Override
        public DiaDaSemana[] newArray(int size) {
            return new DiaDaSemana[size];
        }
    };
    public static DiaDaSemana fromInt(int valor) {
        for (DiaDaSemana dia : DiaDaSemana.values()) {
            if (dia.getValor() == valor) {
                return dia;
            }
        }
        return null;
    }
}