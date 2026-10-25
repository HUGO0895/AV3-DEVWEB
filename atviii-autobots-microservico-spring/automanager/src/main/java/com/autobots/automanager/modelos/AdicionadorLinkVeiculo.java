package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.VeiculoDTO;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.controles.VendaControle;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<VeiculoDTO> {

    @Override
    public void adicionarLink(List<VeiculoDTO> lista) {
        for (VeiculoDTO veiculo : lista) {
            veiculo.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(veiculo.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(VeiculoDTO veiculo) {
        veiculo.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(veiculo.getId())).withSelfRel());
        veiculo.add(linkTo(methodOn(VeiculoControle.class).obterVeiculos()).withRel("veiculos"));
        if (veiculo.getProprietario() != null) {
            veiculo.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(veiculo.getProprietario())).withRel("proprietario"));
        }
        for (Long id : veiculo.getVendas()) {
            veiculo.add(linkTo(methodOn(VendaControle.class).obterVenda(id)).withRel("vendas"));
        }
    }
}
