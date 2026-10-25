package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.CredencialDTO;
import com.autobots.automanager.controles.CredencialControle;
import com.autobots.automanager.controles.UsuarioControle;

@Component
public class AdicionadorLinkCredencial implements AdicionadorLink<CredencialDTO> {

    @Override
    public void adicionarLink(List<CredencialDTO> lista) {
        for (CredencialDTO cred : lista) {
            cred.add(linkTo(methodOn(CredencialControle.class).obterCredencial(cred.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(CredencialDTO cred) {
        cred.add(linkTo(methodOn(CredencialControle.class).obterCredencial(cred.getId())).withSelfRel());
        cred.add(linkTo(methodOn(CredencialControle.class).obterCredenciais()).withRel("credenciais"));
        if (cred.getUsuario() != null) {
            cred.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(cred.getUsuario())).withRel("usuario"));
        }
    }
}
