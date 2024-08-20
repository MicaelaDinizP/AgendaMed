package devandroid.micaela.tcc_agendamed.exception;

public class ColecaoMedicamentosException extends RuntimeException{
    public ColecaoMedicamentosException(Throwable cause) {
        super(cause);
    }
    public ColecaoMedicamentosException(String message, Throwable cause) {
        super(message, cause);
    }
}