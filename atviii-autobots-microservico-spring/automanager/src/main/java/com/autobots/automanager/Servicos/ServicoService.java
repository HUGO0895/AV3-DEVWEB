package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarServicoDTO;
import com.autobots.automanager.DTO.Request.CriarServicoDTO;
import com.autobots.automanager.Mappers.MapperServico;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepositorio repositorio;
    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private MapperServico mapper;
    @Autowired
    private Localizador localizador;

    public List<Servico> listarServicos() {
        return repositorio.findAll();
    }

    public Servico acharUmServico(Long id) {
        return localizador.servico(id);
    }

    @Transactional
    public Servico salvarServico(CriarServicoDTO dto) {
        Servico servico = mapper.criarParaServico(dto);
        servico.setEmpresa(localizador.empresa(dto.getEmpresaId()));
        return repositorio.save(servico);
    }

    @Transactional
    public void atualizarServico(AtualizarServicoDTO dto) {
        Servico servico = localizador.servico(dto.getId());
        mapper.atualizarServico(dto, servico);
        repositorio.save(servico);
    }

    @Transactional
    public void deletarServico(Long id) {
        Servico servico = localizador.servico(id);
        for (Venda venda : vendaRepositorio.findByServicosId(id))
            venda.getServicos().removeIf(s -> s.getId().equals(id));
        repositorio.delete(servico);
    }
}
