package com.autobots.automanager.exceptions.tipos;

public class ConflitoNegocio extends RuntimeException {
    private String mensagem;

    public ConflitoNegocio(String titulo, String mensagem) {
        super(titulo);
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
