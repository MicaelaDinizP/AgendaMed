package devandroid.micaela.tcc_agendamed.test;

import android.os.Parcel;
import devandroid.micaela.tcc_agendamed.model.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioTest {

    @Test
    public void testEscreverECriarDeParcel() {
        Usuario usuario = new Usuario(1L, "Micaela");

        Parcel parcel = Parcel.obtain();
        usuario.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Usuario usuarioCriado = Usuario.CREATOR.createFromParcel(parcel);

        assertEquals(usuario.getId(), usuarioCriado.getId());
        assertEquals(usuario.getNome(), usuarioCriado.getNome());

        parcel.recycle();
    }

    @Test
    public void testEscreverECriarDeParcelComNomeNulo() {
        Usuario usuarioOriginal = new Usuario(1L, null);

        Parcel parcel = Parcel.obtain();
        usuarioOriginal.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);
        Usuario usuarioCriado = Usuario.CREATOR.createFromParcel(parcel);

        assertNull(usuarioCriado.getNome());

        parcel.recycle();
    }

    @Test
    public void testEscreverECriarDeParcelComNomeVazio() {
        Usuario usuarioOriginal = new Usuario(1L, "");

        Parcel parcel = Parcel.obtain();
        usuarioOriginal.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Usuario usuarioCriado = Usuario.CREATOR.createFromParcel(parcel);

        assertEquals("", usuarioCriado.getNome());

        parcel.recycle();
    }

    @Test
    public void testCriarArrayDeParcel() {
        Usuario[] usuariosArray = Usuario.CREATOR.newArray(2);

        assertEquals(2, usuariosArray.length);
    }

    @Test
    public void testDescreverConteudos() {
        Usuario usuario = new Usuario(1L, "Micaela");

        assertEquals(0, usuario.describeContents());
    }

    @Test
    public void testIdPadrao() {
        Usuario usuario = new Usuario("Micaela");

        assertEquals(-1L, usuario.getId());
    }

    @Test
    public void testSetters() {
        Usuario usuario = new Usuario(1L, "Micaela");

        usuario.setId(2L);
        usuario.setNome("Joana");

        assertEquals(2L, usuario.getId());
        assertEquals("Joana", usuario.getNome());
    }

    @Test
    public void testConsistenciaDoObjetoAposSerializacao() {

        Usuario usuarioOriginal = new Usuario(1L, "Micaela");

        usuarioOriginal.setId(3L);
        usuarioOriginal.setNome("Laura");

        Parcel parcel = Parcel.obtain();
        usuarioOriginal.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Usuario usuarioCriado = Usuario.CREATOR.createFromParcel(parcel);

        assertEquals(3L, usuarioCriado.getId());
        assertEquals("Laura", usuarioCriado.getNome());

        parcel.recycle();
    }
}
