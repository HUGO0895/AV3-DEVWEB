package com.autobots.automanager.Servicos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarEmpresaDTO;
import com.autobots.automanager.DTO.Request.CriarEmpresaDTO;
import com.autobots.automanager.Mappers.MapperEmpresa;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepositorio repositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;
    @Autowired
    private ServicoRepositorio servicoRepositorio;
    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private MapperEmpresa mapper;
    @Autowired
    private Localizador localizador;

    public List<Empresa> listarEmpresas() {
        return repositorio.findAll();
    }

    public Empresa acharUmaEmpresa(Long id) {
        return localizador.empresa(id);
    }

    @Transactional
    public Empresa salvarEmpresa(CriarEmpresaDTO dto) {
        return repositorio.save(mapper.criarParaEmpresa(dto));
    }

    @Transactional
    public void atualizarEmpresa(AtualizarEmpresaDTO dto) {
        Empresa empresa = localizador.empresa(dto.getId());
        mapper.atualizarEmpresa(dto, empresa);
        repositorio.save(empresa);
    }

    /** Associa um usuário existente a esta empresa. */
    @Transactional
    public void associarUsuario(Long empresaId, Long usuarioId) {
        Empresa empresa = localizador.empresa(empresaId);
        Usuario usuario = localizador.usuario(usuarioId);
        usuario.setEmpresa(empresa);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void desassociarUsuario(Long empresaId, Long usuarioId) {
        localizador.empresa(empresaId);
        Usuario usuario = localizador.usuario(usuarioId);
        if (usuario.getEmpresa() == null || !usuario.getEmpresa().getId().equals(empresaId))
            throw new NaoexistenoBanco("Erro ao desassociar Usuario", "Usuario não pertence a esta empresa");
        usuario.setEmpresa(null);
        usuarioRepositorio.save(usuario);
    }

    /** Remove a empresa e tudo que pertence a ela (vendas, veículos dos usuários, mercadorias, serviços e usuários). */
    @Transactional
    public void deletarEmpresa(Long id) {
        Empresa empresa = localizador.empresa(id);
        List<Usuario> usuarios = new ArrayList<>(empresa.getUsuarios());

        vendaRepositorio.deleteAll(new ArrayList<>(empresa.getVendas()));
        for (Usuario usuario : usuarios)
            veiculoRepositorio.deleteAll(new ArrayList<>(usuario.getVeiculos()));
        mercadoriaRepositorio.deleteAll(new ArrayList<>(empresa.getMercadorias()));
        servicoRepositorio.deleteAll(new ArrayList<>(empresa.getServicos()));
        // mercadorias de outras empresas que tinham estes usuarios como fornecedor ficam sem fornecedor
        for (Usuario usuario : usuarios)
            usuario.getMercadorias().forEach(m -> m.setFornecedor(null));
        usuarioRepositorio.deleteAll(usuarios);
        repositorio.delete(empresa);
        repositorio.flush();
    }
}
