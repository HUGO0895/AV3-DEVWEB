package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.TelefoneDto;
import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.controles.UsuarioControle;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<TelefoneDto> {

    @Override
    public void adicionarLink(List<TelefoneDto> lista) {
        for (TelefoneDto tel : lista) {
            tel.add(linkTo(methodOn(TelefoneControle.class).obterTelefone(tel.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(TelefoneDto tel) {
        tel.add(linkTo(methodOn(TelefoneControle.class).obterTelefone(tel.getId())).withSelfRel());
        tel.add(linkTo(methodOn(TelefoneControle.class).obterTelefones()).withRel("telefones"));

        // dono do telefone (outro controller)
        if (tel.getUsuario() != null) {
            tel.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(tel.getUsuario())).withRel("usuario"));
        }
        if (tel.getEmpresa() != null) {
            tel.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(tel.getEmpresa())).withRel("empresa"));
        }
    }
}
