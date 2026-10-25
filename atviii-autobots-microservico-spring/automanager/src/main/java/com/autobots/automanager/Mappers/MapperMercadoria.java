package com.autobots.automanager.Mappers;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Request.AtualizarMercadoriaDTO;
import com.autobots.automanager.DTO.Request.CriarMercadoriaDTO;
import com.autobots.automanager.DTO.Response.MercadoriaDTO;
import com.autobots.automanager.entidades.Mercadoria;

@Component
public class MapperMercadoria {
    public Mercadoria criarParaMercadoria(CriarMercadoriaDTO dto) {
        Mercadoria m = new Mercadoria();
        m.setNome(dto.getNome());
        m.setValidade(dto.getValidade());
        m.setFabricao(dto.getFabricao());
        m.setCadastro(dto.getCadastro() != null ? dto.getCadastro() : new Date());
        m.setQuantidade(dto.getQuantidade());
        m.setValor(dto.getValor());
        m.setDescricao(dto.getDescricao());
        return m;
    }

    public void atualizarMercadoria(AtualizarMercadoriaDTO dto, Mercadoria m) {
        if (dto.getNome() != null && !dto.getNome().isEmpty())
            m.setNome(dto.getNome());
        if (dto.getValidade() != null)
            m.setValidade(dto.getValidade());
        if (dto.getFabricao() != null)
            m.setFabricao(dto.getFabricao());
        if (dto.getQuantidade() != null)
            m.setQuantidade(dto.getQuantidade());
        if (dto.getValor() != null)
            m.setValor(dto.getValor());
        if (dto.getDescricao() != null)
            m.setDescricao(dto.getDescricao());
    }

    public MercadoriaDTO mercadoriaParaDTO(Mercadoria m) {
        MercadoriaDTO dto = new MercadoriaDTO();
        dto.setId(m.getId());
        dto.setNome(m.getNome());
        dto.setValidade(m.getValidade());
        dto.setFabricao(m.getFabricao());
        dto.setCadastro(m.getCadastro());
        dto.setQuantidade(m.getQuantidade());
        dto.setValor(m.getValor());
        dto.setDescricao(m.getDescricao());
        if (m.getEmpresa() != null)
            dto.setEmpresa(m.getEmpresa().getId());
        if (m.getFornecedor() != null)
            dto.setFornecedor(m.getFornecedor().getId());
        return dto;
    }
}
