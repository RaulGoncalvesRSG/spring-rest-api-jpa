package com.projetoweb.course.entities;

//Classe para fazer a validação de argumentos
public class Field {

	private String nome;				//Campo com erro
	private String mensagem;

	public Field(String nome, String mensagem) {
		this.nome = nome;
		this.mensagem = mensagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}