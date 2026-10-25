package com.autobots.automanager.Mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Request.AtualizarUsuarioDTO;
import com.autobots.automanager.DTO.Request.CriarUsuarioDTO;
import com.autobots.automanager.DTO.Response.UsuarioDTO;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;

@Component
public class MapperUsuario {
    @Autowired
    MapperTelefone mapperTel;
    @Autowired
    MapperEndereco mapperEnd;
    @Autowired
    MapperDicumento mapperDoc;
    @Autowired
    MapperEmail mapperEmail;
    @Autowired
    MapperCredencial mapperCred;

    public Usuario criarParaUsuario(CriarUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setNomeSocial(dto.getNomeSocial());
        usuario.setPerfis(new HashSet<>(dto.getPerfis()));

        if (dto.getEndereco() != null)
            usuario.setEndereco(mapperEnd.CriarEnderecoDtoParaEndereco(dto.getEndereco()));

        if (dto.getTelefones() != null) {
            dto.getTelefones().forEach(t -> {
                Telefone tel = mapperTel.CriarTelefoneParaTelefone(t);
                tel.setUsuario(usuario);
                usuario.getTelefones().add(tel);
            });
        }
        if (dto.getDocumentos() != null) {
            dto.getDocumentos().forEach(d -> {
                Documento doc = mapperDoc.criarDtoParaDocumento(d);
                doc.setUsuario(usuario);
                usuario.getDocumentos().add(doc);
            });
        }
        if (dto.getEmails() != null) {
            dto.getEmails().forEach(e -> {
                Email email = mapperEmail.criarParaEmail(e);
                email.setUsuario(usuario);
                usuario.getEmails().add(email);
            });
        }
        return usuario;
    }

    public void atualizarUsuario(AtualizarUsuarioDTO dto, Usuario usuario) {
        if (dto.getNome() != null && !dto.getNome().isEmpty())
            usuario.setNome(dto.getNome());
        if (dto.getNomeSocial() != null && !dto.getNomeSocial().isEmpty())
            usuario.setNomeSocial(dto.getNomeSocial());
        if (dto.getPerfis() != null && !dto.getPerfis().isEmpty()) {
            usuario.getPerfis().clear();
            usuario.getPerfis().addAll(dto.getPerfis());
        }
    }

    public UsuarioDTO usuarioParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setNomeSocial(usuario.getNomeSocial());
        dto.setPerfis(new HashSet<>(usuario.getPerfis()));
        if (usuario.getEmpresa() != null)
            dto.setEmpresa(usuario.getEmpresa().getId());
        if (usuario.getEndereco() != null)
            dto.setEndereco(mapperEnd.enderecoParaDTO(usuario.getEndereco()));
        dto.setTelefones(usuario.getTelefones().stream().map(mapperTel::TelefoneParaTelefoneDTO).collect(Collectors.toList()));
        dto.setDocumentos(usuario.getDocumentos().stream().map(mapperDoc::DocumentoParaDTo).collect(Collectors.toList()));
        dto.setEmails(usuario.getEmails().stream().map(mapperEmail::emailParaDTO).collect(Collectors.toList()));
        dto.setCredenciais(usuario.getCredenciais().stream().map(mapperCred::credencialParaDTO).collect(Collectors.toList()));
        dto.setMercadorias(ids(usuario.getMercadorias().stream().map(Mercadoria::getId).collect(Collectors.toList())));
        dto.setVeiculos(ids(usuario.getVeiculos().stream().map(Veiculo::getId).collect(Collectors.toList())));
        dto.setVendas(ids(usuario.getVendas().stream().map(Venda::getId).collect(Collectors.toList())));
        return dto;
    }

    private List<Long> ids(List<Long> lista) {
        return new ArrayList<>(lista);
    }
}
