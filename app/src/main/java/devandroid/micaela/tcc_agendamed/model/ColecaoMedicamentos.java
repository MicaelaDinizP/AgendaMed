package devandroid.micaela.tcc_agendamed.model;

import java.util.List;

public interface ColecaoMedicamentos {
        long inserir(Medicamento medicamento);
        public List<Medicamento> obterTodos(Usuario usuario);
        public Medicamento obter(long id, Usuario usuario);
        public boolean editar(Medicamento medicamento);
        public boolean remover(long id);
}
