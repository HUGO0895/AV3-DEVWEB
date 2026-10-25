package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.EnderecoDTO;
import com.autobots.automanager.controles.EnderecoControle;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<EnderecoDTO> {

    @Override
    public void adicionarLink(List<EnderecoDTO> lista) {
        for (EnderecoDTO endereco : lista) {
            endereco.add(linkTo(methodOn(EnderecoControle.class)
                    .pegarEndereco(endereco.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(EnderecoDTO endereco) {
        endereco.add(linkTo(methodOn(EnderecoControle.class)
                .pegarEndereco(endereco.getId())).withSelfRel());

        endereco.add(linkTo(methodOn(EnderecoControle.class)
                .listarEnderecos()).withRel("enderecos"));
    }
}