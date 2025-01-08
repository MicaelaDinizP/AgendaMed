package devandroid.micaela.tcc_agendamed.model;

import java.util.List;

public interface ColecaoMedicamentos {
        long inserir(Medicamento medicamento);
        public List<Medicamento> obterTodos(Usuario usuario);
        public Medicamento obterPorId(long id, Usuario usuario);
        public Medicamento obterPorNome(String nomeMedicamento, Usuario usuario);
        public long editar(Medicamento medicamento);
        public boolean remover(long id);
}
