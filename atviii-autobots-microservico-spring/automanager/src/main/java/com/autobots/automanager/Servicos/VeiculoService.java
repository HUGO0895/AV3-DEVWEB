package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarVeiculoDTO;
import com.autobots.automanager.DTO.Request.CriarVeiculoDTO;
import com.autobots.automanager.Mappers.MapperVeiculo;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.exceptions.tipos.ConflitoNegocio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepositorio repositorio;
    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private MapperVeiculo mapper;
    @Autowired
    private Localizador localizador;

    public List<Veiculo> listarVeiculos() {
        return repositorio.findAll();
    }

    public Veiculo acharUmVeiculo(Long id) {
        return localizador.veiculo(id);
    }

    @Transactional
    public Veiculo salvarVeiculo(CriarVeiculoDTO dto) {
        Veiculo veiculo = mapper.criarParaVeiculo(dto);
        veiculo.setProprietario(localizador.usuario(dto.getProprietarioId()));
        return repositorio.save(veiculo);
    }

    @Transactional
    public void atualizarVeiculo(AtualizarVeiculoDTO dto) {
        Veiculo veiculo = localizador.veiculo(dto.getId());
        mapper.atualizarVeiculo(dto, veiculo);
        if (dto.getProprietarioId() != null)
            veiculo.setProprietario(localizador.usuario(dto.getProprietarioId()));
        repositorio.save(veiculo);
    }

    @Transactional
    public void deletarVeiculo(Long id) {
        Veiculo veiculo = localizador.veiculo(id);
        if (vendaRepositorio.existsByVeiculoId(id))
            throw new ConflitoNegocio("Erro ao deletar Veiculo", "Veiculo possui vendas vinculadas");
        repositorio.delete(veiculo);
    }
}
