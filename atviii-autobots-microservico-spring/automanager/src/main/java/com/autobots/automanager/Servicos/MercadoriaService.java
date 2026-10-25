package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarMercadoriaDTO;
import com.autobots.automanager.DTO.Request.CriarMercadoriaDTO;
import com.autobots.automanager.Mappers.MapperMercadoria;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class MercadoriaService {
    @Autowired
    private MercadoriaRepositorio repositorio;
    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private MapperMercadoria mapper;
    @Autowired
    private Localizador localizador;

    public List<Mercadoria> listarMercadorias() {
        return repositorio.findAll();
    }

    public Mercadoria acharUmaMercadoria(Long id) {
        return localizador.mercadoria(id);
    }

    @Transactional
    public Mercadoria salvarMercadoria(CriarMercadoriaDTO dto) {
        Mercadoria mercadoria = mapper.criarParaMercadoria(dto);
        mercadoria.setEmpresa(localizador.empresa(dto.getEmpresaId()));
        if (dto.getFornecedorId() != null)
            mercadoria.setFornecedor(buscarFornecedor(dto.getFornecedorId()));
        return repositorio.save(mercadoria);
    }

    @Transactional
    public void atualizarMercadoria(AtualizarMercadoriaDTO dto) {
        Mercadoria mercadoria = localizador.mercadoria(dto.getId());
        mapper.atualizarMercadoria(dto, mercadoria);
        if (dto.getFornecedorId() != null)
            mercadoria.setFornecedor(buscarFornecedor(dto.getFornecedorId()));
        repositorio.save(mercadoria);
    }

    @Transactional
    public void deletarMercadoria(Long id) {
        Mercadoria mercadoria = localizador.mercadoria(id);
        for (Venda venda : vendaRepositorio.findByMercadoriasId(id))
            venda.getMercadorias().removeIf(m -> m.getId().equals(id));
        repositorio.delete(mercadoria);
    }

    private Usuario buscarFornecedor(Long fornecedorId) {
        Usuario fornecedor = localizador.usuario(fornecedorId);
        if (!fornecedor.getPerfis().contains(PerfilUsuario.FORNECEDOR))
            throw new RequisicaoInvalida("Erro ao salvar Mercadoria", "O usuario informado como fornecedor não possui o perfil FORNECEDOR");
        return fornecedor;
    }
}