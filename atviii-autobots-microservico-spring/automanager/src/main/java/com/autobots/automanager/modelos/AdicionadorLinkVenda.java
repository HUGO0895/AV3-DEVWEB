package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.VendaDTO;
import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.controles.VendaControle;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<VendaDTO> {

    @Override
    public void adicionarLink(List<VendaDTO> lista) {
        for (VendaDTO venda : lista) {
            venda.add(linkTo(methodOn(VendaControle.class).obterVenda(venda.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(VendaDTO venda) {
        venda.add(linkTo(methodOn(VendaControle.class).obterVenda(venda.getId())).withSelfRel());
        venda.add(linkTo(methodOn(VendaControle.class).obterVendas()).withRel("vendas"));
        if (venda.getEmpresa() != null) {
            venda.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(venda.getEmpresa())).withRel("empresa"));
        }
        if (venda.getCliente() != null) {
            venda.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(venda.getCliente())).withRel("cliente"));
        }
        if (venda.getFuncionario() != null) {
            venda.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(venda.getFuncionario())).withRel("funcionario"));
        }
        if (venda.getVeiculo() != null) {
            venda.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(venda.getVeiculo())).withRel("veiculo"));
        }
        for (Long id : venda.getMercadorias()) {
            venda.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(id)).withRel("mercadorias"));
        }
        for (Long id : venda.getServicos()) {
            venda.add(linkTo(methodOn(ServicoControle.class).obterServico(id)).withRel("servicos"));
        }
    }
}
