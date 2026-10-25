package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.CredencialDTO;
import com.autobots.automanager.DTO.Response.DocumentoDtO;
import com.autobots.automanager.DTO.Response.EmailDTO;
import com.autobots.automanager.DTO.Response.TelefoneDto;
import com.autobots.automanager.DTO.Response.UsuarioDTO;
import com.autobots.automanager.controles.CredencialControle;
import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.controles.EmailControle;
import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.controles.VendaControle;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<UsuarioDTO> {

    @Override
    public void adicionarLink(List<UsuarioDTO> lista) {
        for (UsuarioDTO usuario : lista) {
            usuario.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(usuario.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(UsuarioDTO usuario) {
        usuario.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(usuario.getId())).withSelfRel());
        usuario.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios()).withRel("usuarios"));

        if (usuario.getEmpresa() != null) {
            usuario.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(usuario.getEmpresa())).withRel("empresa"));
        }
        if (usuario.getEndereco() != null) {
            usuario.add(linkTo(methodOn(EnderecoControle.class).pegarEndereco(usuario.getEndereco().getId())).withRel("endereco"));
        }
        for (TelefoneDto tel : usuario.getTelefones()) {
            tel.add(linkTo(methodOn(TelefoneControle.class).obterTelefone(tel.getId())).withSelfRel());
        }
        for (DocumentoDtO doc : usuario.getDocumentos()) {
            doc.add(linkTo(methodOn(DocumentoControle.class).pegarDoc(doc.getId())).withSelfRel());
        }
        for (EmailDTO email : usuario.getEmails()) {
            email.add(linkTo(methodOn(EmailControle.class).obterEmail(email.getId())).withSelfRel());
        }
        for (CredencialDTO cred : usuario.getCredenciais()) {
            cred.add(linkTo(methodOn(CredencialControle.class).obterCredencial(cred.getId())).withSelfRel());
        }
        for (Long id : usuario.getMercadorias()) {
            usuario.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(id)).withRel("mercadorias"));
        }
        for (Long id : usuario.getVeiculos()) {
            usuario.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(id)).withRel("veiculos"));
        }
        for (Long id : usuario.getVendas()) {
            usuario.add(linkTo(methodOn(VendaControle.class).obterVenda(id)).withRel("vendas"));
        }
    }
}
