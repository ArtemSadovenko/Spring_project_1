package lpnu.exception;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerService {
    @ExceptionHandler(value = IrregularDate.class )
    public ResponseEntity<Object> handleServiceException(final IrregularDate ex, final WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getCode()))
                .body(new IrregularDateDTO(ex.getMassage(), ex.getCode()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class )
    public ResponseEntity<Object> handleServiceException(final MethodArgumentNotValidException ex) {

        final String messages = //"Wrong input data";
                ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((s1, s2) -> s1 + "; " + s2)
                .orElse("We have an issue with creating error message");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new IrregularDateDTO(messages, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(value = RejectedPurchase.class)
    public ResponseEntity<Object> handleRejectException(final RejectedPurchase ex, final WebRequest request){
        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getCode()))
                .body(new IrregularDateDTO(ex.getMassage(), ex.getCode()));
    }
}
