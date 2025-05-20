package fiap.com.sensorium.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError404() {
        return ResponseEntity.notFound().build();
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
////    pegaremos as informações da exception, que será passada atuomaticamente pelo ExceptionHandler
//    public ResponseEntity handleError400(MethodArgumentNotValidException ex) {
//        var erros = ex.getFieldErrors();
//
//        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
//    }



}
