package devandroid.micaela.tcc_agendamed.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import devandroid.micaela.tcc_agendamed.controller.MedicamentoController;
import devandroid.micaela.tcc_agendamed.controller.UsuarioController;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public class BootService extends Service {
    private UsuarioController usuarioController;
    private MedicamentoController medicamentoController;
    private List<Usuario> listaUsuarios;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.usuarioController = new UsuarioController(this);
        this.medicamentoController = new MedicamentoController(this);
        medicamentoController.abrirConexao();
        usuarioController.abrirConexao();
        this.listaUsuarios = new ArrayList<>();
        List<Medicamento> listaMedicamentos = new ArrayList<>();
        obterUsuarios();
        for (Usuario usuario : this.listaUsuarios){
            listaMedicamentos = obterMedicamentosDoUsuario(usuario);
            for(Medicamento medicamento : listaMedicamentos ){
                if(checarAlarmeAtivo(medicamento)){
                    programarAlarmes(medicamento);
                }
            }
        }
        medicamentoController.fecharConexao();
        usuarioController.fecharConexao();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void programarAlarmes(Medicamento medicamento) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        GerenciadorAlarme.criarNotificationChannel(this);
        if (!medicamento.isUsoPausado()) {
            Log.d("ProgramouAlarme", "Alarme para remedio "+medicamento.getNomeMedicamento()+" foi programaado");
            GerenciadorAlarme.agendarMultiplosAlarmes(this, medicamento);
        }
    }
    private boolean checarAlarmeAtivo(Medicamento medicamento){
        if(medicamento.isCriarAlarmes()){
            return true;
        }
        return false;
    }
    private void obterUsuarios(){
        this.listaUsuarios = this.usuarioController.obterTodos();
    }
    private List<Medicamento> obterMedicamentosDoUsuario(Usuario usuario){
        return this.medicamentoController.obterTodos(usuario);
    }
}
