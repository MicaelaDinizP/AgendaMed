package devandroid.micaela.tcc_agendamed.model;

import java.util.List;

public interface ColecaoMedicamentos {
        long inserir(Medicamento medicamento);
        public List<Medicamento> obterTodos();
        public Medicamento obter(long id);
        public int editar(Medicamento medicamento);
        public int remover(long id);
}
