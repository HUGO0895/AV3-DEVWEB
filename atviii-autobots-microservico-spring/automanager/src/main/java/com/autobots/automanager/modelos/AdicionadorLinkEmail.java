package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.EmailDTO;
import com.autobots.automanager.controles.EmailControle;
import com.autobots.automanager.controles.UsuarioControle;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<EmailDTO> {

    @Override
    public void adicionarLink(List<EmailDTO> lista) {
        for (EmailDTO email : lista) {
            email.add(linkTo(methodOn(EmailControle.class).obterEmail(email.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(EmailDTO email) {
        email.add(linkTo(methodOn(EmailControle.class).obterEmail(email.getId())).withSelfRel());
        email.add(linkTo(methodOn(EmailControle.class).obterEmails()).withRel("emails"));
        if (email.getUsuario() != null) {
            email.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(email.getUsuario())).withRel("usuario"));
        }
    }
}
