package devandroid.micaela.tcc_agendamed.test;

import android.os.Parcel;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.Usuario;
import devandroid.micaela.tcc_agendamed.model.DiaDaSemana;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MedicamentoTest {

    @Test
    public void testCriarParcel() {
        Medicamento medicamento = new Medicamento(1L, "Dorflex");

        Parcel parcel = Parcel.obtain();
        medicamento.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Medicamento medicamentoCriado = Medicamento.CREATOR.createFromParcel(parcel);

        assertEquals(medicamento.getId(), medicamentoCriado.getId());
        assertEquals(medicamento.getNomeMedicamento(), medicamentoCriado.getNomeMedicamento());

        parcel.recycle();
    }

    @Test
    public void testCriarArrayListParcel() {
        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        Medicamento med1= new Medicamento("Dorflex", null, 20, Collections.emptyList(), 2, 5, 10, false, false, false, Collections.emptyList());
        Medicamento med2= new Medicamento("Buscofem", null, 20, Collections.emptyList(), 2, 5, 10, false, false, false, Collections.emptyList());
        medicamentos.add(med1);
        medicamentos.add(med2);
        assertEquals(2, medicamentos.size());
    }

    @Test
    public void testConsistenciaDoObjetoAposSerializacao() {
        List<DiaDaSemana> dias = new ArrayList<>();
        dias.add(DiaDaSemana.SEGUNDA);

        List<String> horarios = Arrays.asList("08:00", "14:00");
        Usuario usuario = new Usuario(1L, "Meg");

        Medicamento medicamentoOriginal = new Medicamento("Dorflex", usuario, 10, dias, 2, 5, 8, false, true, true, horarios);
        medicamentoOriginal.setId(3L);

        Parcel parcel = Parcel.obtain();
        medicamentoOriginal.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Medicamento medicamentoCriado = Medicamento.CREATOR.createFromParcel(parcel);

        assertEquals(3L, medicamentoCriado.getId());
        assertEquals("Dorflex", medicamentoCriado.getNomeMedicamento());
        assertEquals(usuario.getId(), medicamentoCriado.getUsuario().getId());
        assertEquals(10, medicamentoCriado.getQuantidadeDosesPorEmbalagem());
        assertEquals(2, medicamentoCriado.getDosesPorDia());
        assertEquals(5, medicamentoCriado.getQuantidadeEstoqueCritico());
        assertEquals(8, medicamentoCriado.getQuantidadeDosesRestantes());
        assertFalse(medicamentoCriado.isUsoPausado());
        assertTrue(medicamentoCriado.isCriarAlarmes());
        assertTrue(medicamentoCriado.isAlarmeAtivo());
        assertEquals(2, medicamentoCriado.getListaHorarios().size());

        parcel.recycle();
    }
}
