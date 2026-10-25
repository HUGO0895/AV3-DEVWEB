package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.EmpresaDTO;
import com.autobots.automanager.DTO.Response.TelefoneDto;
import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VendaControle;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<EmpresaDTO> {

    @Override
    public void adicionarLink(List<EmpresaDTO> lista) {
        for (EmpresaDTO empresa : lista) {
            empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresa.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(EmpresaDTO empresa) {
        empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresa.getId())).withSelfRel());
        empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresas()).withRel("empresas"));

        if (empresa.getEndereco() != null) {
            empresa.add(linkTo(methodOn(EnderecoControle.class).pegarEndereco(empresa.getEndereco().getId())).withRel("endereco"));
        }
        if (empresa.getTelefones() != null) {
            for (TelefoneDto tel : empresa.getTelefones()) {
                tel.add(linkTo(methodOn(TelefoneControle.class).obterTelefone(tel.getId())).withSelfRel());
            }
        }
        for (Long id : empresa.getUsuarios()) {
            empresa.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(id)).withRel("usuarios"));
        }
        for (Long id : empresa.getMercadorias()) {
            empresa.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(id)).withRel("mercadorias"));
        }
        for (Long id : empresa.getServicos()) {
            empresa.add(linkTo(methodOn(ServicoControle.class).obterServico(id)).withRel("servicos"));
        }
        for (Long id : empresa.getVendas()) {
            empresa.add(linkTo(methodOn(VendaControle.class).obterVenda(id)).withRel("vendas"));
        }
    }
}
