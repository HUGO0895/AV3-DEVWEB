package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.MercadoriaDTO;
import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.UsuarioControle;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<MercadoriaDTO> {

    @Override
    public void adicionarLink(List<MercadoriaDTO> lista) {
        for (MercadoriaDTO m : lista) {
            m.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(m.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(MercadoriaDTO m) {
        m.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(m.getId())).withSelfRel());
        m.add(linkTo(methodOn(MercadoriaControle.class).obterMercadorias()).withRel("mercadorias"));
        if (m.getEmpresa() != null) {
            m.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(m.getEmpresa())).withRel("empresa"));
        }
        if (m.getFornecedor() != null) {
            m.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(m.getFornecedor())).withRel("fornecedor"));
        }
    }
}
