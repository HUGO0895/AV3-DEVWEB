package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.ServicoDTO;
import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.ServicoControle;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<ServicoDTO> {

    @Override
    public void adicionarLink(List<ServicoDTO> lista) {
        for (ServicoDTO s : lista) {
            s.add(linkTo(methodOn(ServicoControle.class).obterServico(s.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(ServicoDTO s) {
        s.add(linkTo(methodOn(ServicoControle.class).obterServico(s.getId())).withSelfRel());
        s.add(linkTo(methodOn(ServicoControle.class).obterServicos()).withRel("servicos"));
        if (s.getEmpresa() != null) {
            s.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(s.getEmpresa())).withRel("empresa"));
        }
    }
}
