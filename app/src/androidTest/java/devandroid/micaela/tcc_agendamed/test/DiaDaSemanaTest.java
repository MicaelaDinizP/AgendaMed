package devandroid.micaela.tcc_agendamed.test;
import org.junit.Test;
import static org.junit.Assert.*;

import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;

public class DiaDaSemanaTest {
    @Test
    public void testValoresEnum(){
        DiaDaSemana[] diasDaSemanaEsperados = {DiaDaSemana.SEGUNDA,DiaDaSemana.TERCA, DiaDaSemana.QUARTA,DiaDaSemana.QUINTA,DiaDaSemana.SEXTA,DiaDaSemana.SABADO,DiaDaSemana.DOMINGO,};
        assertArrayEquals(diasDaSemanaEsperados, DiaDaSemana.values());
    }

    @Test
    public void testQuantidadeValores(){
        assertEquals(7, DiaDaSemana.values().length);
    }

    @Test
    public void testValoresAssociados(){
        assertEquals(1, DiaDaSemana.DOMINGO.getValor());
        assertEquals(2, DiaDaSemana.SEGUNDA.getValor());
        assertEquals(3, DiaDaSemana.TERCA.getValor());
        assertEquals(4, DiaDaSemana.QUARTA.getValor());
        assertEquals(5, DiaDaSemana.QUINTA.getValor());
        assertEquals(6, DiaDaSemana.SEXTA.getValor());
        assertEquals(7, DiaDaSemana.SABADO.getValor());
    }

    @Test
    public void testConversaoValorParaEnum(){
        assertEquals(DiaDaSemana.SEGUNDA,DiaDaSemana.valueOf("SEGUNDA"));
    }

    @Test
    public void testGetDiasDaSemanaDiasCorretos() {
        assertEquals(DiaDaSemana.DOMINGO, DiaDaSemana.getDiaDaSemana("DOMINGO"));
        assertEquals(DiaDaSemana.SEGUNDA, DiaDaSemana.getDiaDaSemana("SEGUNDA"));
        assertEquals(DiaDaSemana.TERCA, DiaDaSemana.getDiaDaSemana("TERCA"));
        assertEquals(DiaDaSemana.QUARTA, DiaDaSemana.getDiaDaSemana("QUARTA"));
        assertEquals(DiaDaSemana.QUINTA, DiaDaSemana.getDiaDaSemana("QUINTA"));
        assertEquals(DiaDaSemana.SEXTA, DiaDaSemana.getDiaDaSemana("SEXTA"));
        assertEquals(DiaDaSemana.SABADO, DiaDaSemana.getDiaDaSemana("SABADO"));
    }
    @Test
    public void testGetDiasDaSemanaStringInvalida(){
        DiaDaSemana dds = DiaDaSemana.getDiaDaSemana("STRING");
        assertEquals(dds,null);
    }

    @Test
    public void testFromIntValorInvalido(){
        assertEquals(DiaDaSemana.fromInt(10),null);
        assertEquals(DiaDaSemana.fromInt(0),null);
    }

    @Test
    public void testFromIntValoresValidos(){
        assertEquals(DiaDaSemana.fromInt(1),DiaDaSemana.DOMINGO);
        assertEquals(DiaDaSemana.fromInt(2),DiaDaSemana.SEGUNDA);
        assertEquals(DiaDaSemana.fromInt(3),DiaDaSemana.TERCA);
        assertEquals(DiaDaSemana.fromInt(4),DiaDaSemana.QUARTA);
        assertEquals(DiaDaSemana.fromInt(5),DiaDaSemana.QUINTA);
        assertEquals(DiaDaSemana.fromInt(6),DiaDaSemana.SEXTA);
        assertEquals(DiaDaSemana.fromInt(7),DiaDaSemana.SABADO);

    }

}
