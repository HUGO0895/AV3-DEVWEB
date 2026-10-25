package com.autobots.automanager.Servicos;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarVendaDTO;
import com.autobots.automanager.DTO.Request.CriarVendaDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VendaService {
    @Autowired
    private VendaRepositorio repositorio;
    @Autowired
    private Localizador localizador;

    public List<Venda> listarVendas() {
        return repositorio.findAll();
    }

    public Venda acharUmaVenda(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Venda", "Elemento não existe"));
    }

    @Transactional
    public Venda salvarVenda(CriarVendaDTO dto) {
        Venda venda = new Venda();
        venda.setIdentificacao(dto.getIdentificacao());
        venda.setCadastro(dto.getCadastro() != null ? dto.getCadastro() : new Date());
        venda.setEmpresa(localizador.empresa(dto.getEmpresaId()));
        definirCliente(venda, dto.getClienteId());
        definirFuncionario(venda, dto.getFuncionarioId());
        if (dto.getVeiculoId() != null)
            definirVeiculo(venda, dto.getVeiculoId());
        if (dto.getMercadoriaIds() != null)
            definirMercadorias(venda, dto.getMercadoriaIds());
        if (dto.getServicoIds() != null)
            definirServicos(venda, dto.getServicoIds());
        return repositorio.save(venda);
    }

    @Transactional
    public void atualizarVenda(AtualizarVendaDTO dto) {
        Venda venda = acharUmaVenda(dto.getId());
        if (dto.getIdentificacao() != null && !dto.getIdentificacao().isEmpty())
            venda.setIdentificacao(dto.getIdentificacao());
        if (dto.getClienteId() != null)
            definirCliente(venda, dto.getClienteId());
        if (dto.getFuncionarioId() != null)
            definirFuncionario(venda, dto.getFuncionarioId());
        if (dto.getVeiculoId() != null)
            definirVeiculo(venda, dto.getVeiculoId());
        else if (venda.getVeiculo() != null && dto.getClienteId() != null)
            definirVeiculo(venda, venda.getVeiculo().getId());
        if (dto.getMercadoriaIds() != null)
            definirMercadorias(venda, dto.getMercadoriaIds());
        if (dto.getServicoIds() != null)
            definirServicos(venda, dto.getServicoIds());
        repositorio.save(venda);
    }

    @Transactional
    public void deletarVenda(Long id) {
        repositorio.delete(acharUmaVenda(id));
    }

    private void definirCliente(Venda venda, Long clienteId) {
        Usuario cliente = localizador.usuario(clienteId);
        if (!cliente.getPerfis().contains(PerfilUsuario.CLIENTE))
            throw new RequisicaoInvalida("Erro ao salvar Venda", "O usuario informado como cliente não possui o perfil CLIENTE");
        venda.setCliente(cliente);
    }

    private void definirFuncionario(Venda venda, Long funcionarioId) {
        Usuario funcionario = localizador.usuario(funcionarioId);
        if (!funcionario.getPerfis().contains(PerfilUsuario.FUNCIONARIO))
            throw new RequisicaoInvalida("Erro ao salvar Venda", "O usuario informado como funcionario não possui o perfil FUNCIONARIO");
        venda.setFuncionario(funcionario);
    }

    private void definirVeiculo(Venda venda, Long veiculoId) {
        Veiculo veiculo = localizador.veiculo(veiculoId);
        if (veiculo.getProprietario() == null || !veiculo.getProprietario().getId().equals(venda.getCliente().getId()))
            throw new RequisicaoInvalida("Erro ao salvar Venda", "O veiculo informado não pertence ao cliente da venda");
        venda.setVeiculo(veiculo);
    }

    private void definirMercadorias(Venda venda, Set<Long> ids) {
        venda.getMercadorias().clear();
        for (Long id : ids) {
            Mercadoria mercadoria = localizador.mercadoria(id);
            if (mercadoria.getEmpresa() == null || !mercadoria.getEmpresa().getId().equals(venda.getEmpresa().getId()))
                throw new RequisicaoInvalida("Erro ao salvar Venda", "A mercadoria " + id + " não pertence à empresa da venda");
            venda.getMercadorias().add(mercadoria);
        }
    }

    private void definirServicos(Venda venda, Set<Long> ids) {
        venda.getServicos().clear();
        for (Long id : ids) {
            Servico servico = localizador.servico(id);
            if (servico.getEmpresa() == null || !servico.getEmpresa().getId().equals(venda.getEmpresa().getId()))
                throw new RequisicaoInvalida("Erro ao salvar Venda", "O servico " + id + " não pertence à empresa da venda");
            venda.getServicos().add(servico);
        }
    }
}
