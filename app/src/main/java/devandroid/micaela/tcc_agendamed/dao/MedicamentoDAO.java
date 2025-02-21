package devandroid.micaela.tcc_agendamed.dao;

import java.util.List;

import devandroid.micaela.tcc_agendamed.exception.ColecaoMedicamentosException;
import devandroid.micaela.tcc_agendamed.model.Medicamento;
import devandroid.micaela.tcc_agendamed.model.Usuario;

public interface MedicamentoDAO {
        long inserir(Medicamento medicamento) throws ColecaoMedicamentosException;
        public List<Medicamento> obterTodos(Usuario usuario) throws ColecaoMedicamentosException;
        public Medicamento obterPorId(long id, Usuario usuario) throws ColecaoMedicamentosException;
        public Medicamento obterPorNome(String nomeMedicamento, Usuario usuario) throws ColecaoMedicamentosException;
        public long editar(Medicamento medicamento) throws ColecaoMedicamentosException;
        public boolean remover(long id) throws ColecaoMedicamentosException;
}
