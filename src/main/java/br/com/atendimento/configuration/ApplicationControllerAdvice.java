package br.com.atendimento.configuration;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NonUniqueResultException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleConstraintViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        ApiErros apiError =
                new ApiErros(ex.getCause().getMessage());
        return apiError;
    }

    @ExceptionHandler(NonUniqueResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleNonUniqueResultException(NonUniqueResultException ex) {
        ApiErros apiError = new ApiErros("Consulta ao banco de dados retornou mais de um registro para campos que não comportam duplicidade. Ex: Código Barras, CPF e etc. Verifique essa quebra de integridade em seu banco de dados.\n" + ex.getCause().getMessage());
        return apiError;
    }


    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleNonUniqueResultException(IncorrectResultSizeDataAccessException ex) {
        ApiErros apiError = new ApiErros("Consulta ao banco de dados retornou mais de um registro para campos que não comportam duplicidade. Ex: Código Barras, CPF e etc. Verifique essa quebra de integridade em seu banco de dados.\n" + ex.getCause().getMessage());
        return apiError;
    }

    /**
     * Intercepta e manipula a saida padrao de erros no formato JSON quando ocorre erro de validacao
     *
     * @param ex
     * @return um object
     * @throws Exception the exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //jamais esquecer esta linha, ela que definira no cliente seja angular ou que for quando houve sucesso ou nao na requisicao
    public ApiErros handleValidationErros(MethodArgumentNotValidException ex) {
        BindingResult bindResult = ex.getBindingResult();
        List<String> messages = bindResult.getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErros(messages);
    }

    /**
     * Intercepta e manipula a saida padrao de erros no formato JSON quando ocorre qualquer outro erro, formata o HTTP_STATUS
     *
     * @param ex
     * @return um object
     * @throws Exception the exception
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
        //Desta forma, o ex.getMessage tras tanto o codigo de status quanto a mensagem de erro
        //String mensagemErro = ex.getMessage();
        String mensagemErro = ex.getReason();
        HttpStatus codigoStatus = ex.getStatus();
        ApiErros apiErros = new ApiErros(mensagemErro);
        return new ResponseEntity(apiErros, codigoStatus);
    }



    /**
     * Intercepta e manipula a saida padrao de erros no formato JSON quando ocorre qualquer outro erro, formata o HTTP_STATUS
     * Arquivo grande Upload
     *
     * @param ex
     * @return um object
     * @throws Exception the exception
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException ex) {
        String mensagemErro = ex.getMessage() + " => Arquivo excedeu o tamanho.";
        ApiErros apiErros = new ApiErros(mensagemErro);
        return new ResponseEntity(apiErros, HttpStatus.EXPECTATION_FAILED);
    }


    /**
     * Intercepta e manipula a saida padrao de erros no formato JSON quando ocorre qualquer outro erro, formata o HTTP_STATUS
     * Arquivo grande Upload
     *
     * @param ex
     * @return um object
     * @throws Exception the exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handle(Exception ex) {
        String mensagemErro = ex.getMessage();
        ApiErros apiErros = new ApiErros(mensagemErro);
        if (ex instanceof NullPointerException) {
            return new ResponseEntity(apiErros, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(apiErros, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }*/

}
