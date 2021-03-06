package com.projetoweb.course.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.projetoweb.course.entities.Field;

/*Por padrão, o Spring retorna o objeto de erro com esses dados. Então para fazer um tratamento manual das 
exceções e retornar um objeto parecido, é criada uma classe com os mesmos campos.
Essa classe fica no resources, pois o objeto de erro retornado pelo Spring é um objeto q já está na resposta
da requisição e quem trabalha com requisção e mexe com isso é o resource*/

/*Include.NON_NULL - Inclui o campo no Json apenas se n tiver o valor null, então se os campos
 estiverem vazios ou com valor null, n irá mostrar a sua chave no Json*/
@JsonInclude(Include.NON_NULL)			
public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path; 			// Caminho que fez a requisição e deu erro
	private List<Field> campos;		//Lista de campos com problemas. Validação de argumentos

	public StandardError() {
	}

	public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Field> getCampos() {
		return campos;
	}

	public void setCampos(List<Field> campos) {
		this.campos = campos;
	}
}
