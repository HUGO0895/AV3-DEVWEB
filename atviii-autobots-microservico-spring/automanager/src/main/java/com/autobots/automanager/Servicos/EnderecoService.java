package com.autobots.automanager.Servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.CriarEnderecoDTO;
import com.autobots.automanager.DTO.Request.atualizarEndereciDTO;
import com.autobots.automanager.Mappers.MapperEndereco;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class EnderecoService {
    @Autowired
    EnderecoRepositorio enderecoRepositorio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    EmpresaRepositorio empresaRepositorio;
    @Autowired
    MapperEndereco mapperEnd;
    @Autowired
    Localizador localizador;

    public List<Endereco> listarOsEnderecos() {
        return enderecoRepositorio.findAll();
    }

    public Endereco acharEnderecoPorId(Long id) {
        return enderecoRepositorio.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Endereco", "Elemento não existe"));
    }

    /** Sem usuarioId/empresaId o endereço é criado solto; com um deles, passa a ser o endereço daquele dono. */
    @Transactional
    public Endereco criarEndereco(CriarEnderecoDTO ende) {
        if (ende.getUsuarioId() != null && ende.getEmpresaId() != null)
            throw new RequisicaoInvalida("Erro ao criar Endereco", "Informe usuarioId ou empresaId (apenas um)");
        Endereco endereco = mapperEnd.CriarEnderecoDtoParaEndereco(ende);
        if (ende.getUsuarioId() != null) {
            Usuario usuario = localizador.usuario(ende.getUsuarioId());
            usuario.setEndereco(endereco);
            return usuarioRepositorio.save(usuario).getEndereco();
        }
        if (ende.getEmpresaId() != null) {
            Empresa empresa = localizador.empresa(ende.getEmpresaId());
            empresa.setEndereco(endereco);
            return empresaRepositorio.save(empresa).getEndereco();
        }
        return enderecoRepositorio.save(endereco);
    }

    @Transactional
    public void deletarEndereco(Long id) {
        Endereco ende = acharEnderecoPorId(id);
        Optional<Usuario> usuario = usuarioRepositorio.findByEnderecoId(id);
        if (usuario.isPresent())
            usuario.get().setEndereco(null);
        Optional<Empresa> empresa = empresaRepositorio.findByEnderecoId(id);
        if (empresa.isPresent())
            empresa.get().setEndereco(null);
        enderecoRepositorio.delete(ende);
    }

    @Transactional
    public void atualizarEndereco(atualizarEndereciDTO ende) {
        Endereco endereco = acharEnderecoPorId(ende.getId());
        mapperEnd.AtualizarEnderecoParaEndereco(ende, endereco);
        enderecoRepositorio.save(endereco);
    }
}
