package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarUsuarioDTO;
import com.autobots.automanager.DTO.Request.CriarUsuarioDTO;
import com.autobots.automanager.Mappers.MapperUsuario;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.exceptions.tipos.ConflitoNegocio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepositorio repositorio;
    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;
    @Autowired
    private MapperUsuario mapper;
    @Autowired
    private Localizador localizador;

    public List<Usuario> listarUsuarios() {
        return repositorio.findAll();
    }

    public Usuario acharUmUsuario(Long id) {
        return localizador.usuario(id);
    }

    @Transactional
    public Usuario salvarUsuario(CriarUsuarioDTO dto) {
        Usuario usuario = mapper.criarParaUsuario(dto);
        if (dto.getEmpresaId() != null)
            usuario.setEmpresa(localizador.empresa(dto.getEmpresaId()));
        return repositorio.save(usuario);
    }

    @Transactional
    public void atualizarUsuario(AtualizarUsuarioDTO dto) {
        Usuario usuario = localizador.usuario(dto.getId());
        mapper.atualizarUsuario(dto, usuario);
        if (dto.getEmpresaId() != null)
            usuario.setEmpresa(localizador.empresa(dto.getEmpresaId()));
        repositorio.save(usuario);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = localizador.usuario(id);
        if (vendaRepositorio.existsByClienteId(id) || vendaRepositorio.existsByFuncionarioId(id))
            throw new ConflitoNegocio("Erro ao deletar Usuario", "Usuario possui vendas vinculadas");
        if (veiculoRepositorio.existsByProprietarioId(id))
            throw new ConflitoNegocio("Erro ao deletar Usuario", "Usuario possui veiculos vinculados");
        // mercadorias fornecidas continuam existindo, apenas sem fornecedor
        usuario.getMercadorias().forEach(m -> m.setFornecedor(null));
        repositorio.delete(usuario);
    }
}
