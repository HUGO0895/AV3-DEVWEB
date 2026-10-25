package com.autobots.automanager.exceptions.tipos;

public class RequisicaoInvalida extends RuntimeException {
    private String mensagem;

    public RequisicaoInvalida(String titulo, String mensagem) {
        super(titulo);
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
