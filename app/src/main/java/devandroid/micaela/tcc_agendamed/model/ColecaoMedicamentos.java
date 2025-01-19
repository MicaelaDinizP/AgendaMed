package devandroid.micaela.tcc_agendamed.model;

import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoMedicamentosException;

public interface ColecaoMedicamentos {
        long inserir(Medicamento medicamento) throws ColecaoMedicamentosException;
        public List<Medicamento> obterTodos(Usuario usuario) throws ColecaoMedicamentosException;
        public Medicamento obterPorId(long id, Usuario usuario) throws ColecaoMedicamentosException;
        public Medicamento obterPorNome(String nomeMedicamento, Usuario usuario) throws ColecaoMedicamentosException;
        public long editar(Medicamento medicamento) throws ColecaoMedicamentosException;
        public boolean remover(long id) throws ColecaoMedicamentosException;
}
