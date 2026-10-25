package com.autobots.automanager.Mappers;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Request.AtualizarServicoDTO;
import com.autobots.automanager.DTO.Request.CriarServicoDTO;
import com.autobots.automanager.DTO.Response.ServicoDTO;
import com.autobots.automanager.entidades.Servico;

@Component
public class MapperServico {
    public Servico criarParaServico(CriarServicoDTO dto) {
        Servico s = new Servico();
        s.setNome(dto.getNome());
        s.setValor(dto.getValor());
        s.setDescricao(dto.getDescricao());
        return s;
    }

    public void atualizarServico(AtualizarServicoDTO dto, Servico s) {
        if (dto.getNome() != null && !dto.getNome().isEmpty())
            s.setNome(dto.getNome());
        if (dto.getValor() != null)
            s.setValor(dto.getValor());
        if (dto.getDescricao() != null)
            s.setDescricao(dto.getDescricao());
    }

    public ServicoDTO servicoParaDTO(Servico s) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(s.getId());
        dto.setNome(s.getNome());
        dto.setValor(s.getValor());
        dto.setDescricao(s.getDescricao());
        if (s.getEmpresa() != null)
            dto.setEmpresa(s.getEmpresa().getId());
        return dto;
    }
}
