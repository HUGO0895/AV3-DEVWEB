package com.autobots.automanager.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.autobots.automanager.DTO.Response.ErroRespostaDTO;
import com.autobots.automanager.exceptions.tipos.ConflitoNegocio;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;

@ControllerAdvice 
public class ManipuladorGlobalDeErros {

   @ExceptionHandler(NaoexistenoBanco.class)
   public ResponseEntity<ErroRespostaDTO>manipularNaoExistenciaNoBanco(NaoexistenoBanco erro){
      ErroRespostaDTO erroRes=new ErroRespostaDTO(erro.getMessage(),erro.getMensagem());
      ResponseEntity<ErroRespostaDTO> resposta=new ResponseEntity<>(erroRes,HttpStatus.NOT_FOUND);
      return resposta;
   }

   @ExceptionHandler(RequisicaoInvalida.class)
   public ResponseEntity<ErroRespostaDTO>manipularRequisicaoInvalida(RequisicaoInvalida erro){
      ErroRespostaDTO erroRes=new ErroRespostaDTO(erro.getMessage(),erro.getMensagem());
      return new ResponseEntity<>(erroRes,HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(ConflitoNegocio.class)
   public ResponseEntity<ErroRespostaDTO>manipularConflito(ConflitoNegocio erro){
      ErroRespostaDTO erroRes=new ErroRespostaDTO(erro.getMessage(),erro.getMensagem());
      return new ResponseEntity<>(erroRes,HttpStatus.CONFLICT);
   }

   @ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity<ErroRespostaDTO>manipularIntegridade(DataIntegrityViolationException erro){
      ErroRespostaDTO erroRes=new ErroRespostaDTO("Violação de integridade","Valor duplicado ou registro com vínculos em outras entidades");
      return new ResponseEntity<>(erroRes,HttpStatus.CONFLICT);
   }

   @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<ErroRespostaDTO>manipularCorpoIlegivel(HttpMessageNotReadableException erro){
      ErroRespostaDTO erroRes=new ErroRespostaDTO("Corpo da requisição inválido","Verifique o JSON enviado (tipos, datas e valores de enumeração)");
      return new ResponseEntity<>(erroRes,HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(InvalidDataAccessApiUsageException.class)
   public ResponseEntity<ErroRespostaDTO>manipularInvalidDataAcess(InvalidDataAccessApiUsageException erro){
      ErroRespostaDTO erroRes=new ErroRespostaDTO(erro.getMessage(),"Verifique os dados enviados");
      ResponseEntity<ErroRespostaDTO> resposta=new ResponseEntity<ErroRespostaDTO>(erroRes,HttpStatus.BAD_REQUEST);
      return resposta;
   }
   
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Object>manipuladorFormularios(MethodArgumentNotValidException erro){
    Map<String,String> errors=new HashMap<>();
    erro.getBindingResult().getFieldErrors().forEach((error)->{
           String campo=error.getField();
           String mensagem=error.getDefaultMessage();
           errors.put(campo, mensagem);

    });

    return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);

   }

   @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
   public ResponseEntity<Object>manipularHtppRequestMethodNotSupport(HttpRequestMethodNotSupportedException erro){
       Map<String,String> errors=new HashMap<>();
       errors.put("erro",":Esse metodo não é possivel nessa rota:"+erro.getMethod());
       return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);


   }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErroRespostaDTO> manipularHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException erro){
      ErroRespostaDTO respostaE= new ErroRespostaDTO("Tipo de envio errado","Verifique a documentação e veja os tipos de envio permitidos");
       ResponseEntity<ErroRespostaDTO> resposta=new ResponseEntity<ErroRespostaDTO>(respostaE,HttpStatus.BAD_REQUEST);
        return resposta;
    }

   

   
    
}
