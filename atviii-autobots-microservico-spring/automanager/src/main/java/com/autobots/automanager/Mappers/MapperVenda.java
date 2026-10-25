package com.autobots.automanager.Mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.VendaDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;

@Component
public class MapperVenda {
    public VendaDTO vendaParaDTO(Venda v) {
        VendaDTO dto = new VendaDTO();
        dto.setId(v.getId());
        dto.setCadastro(v.getCadastro());
        dto.setIdentificacao(v.getIdentificacao());
        if (v.getEmpresa() != null)
            dto.setEmpresa(v.getEmpresa().getId());
        if (v.getCliente() != null)
            dto.setCliente(v.getCliente().getId());
        if (v.getFuncionario() != null)
            dto.setFuncionario(v.getFuncionario().getId());
        if (v.getVeiculo() != null)
            dto.setVeiculo(v.getVeiculo().getId());
        dto.setMercadorias(v.getMercadorias().stream().map(Mercadoria::getId).collect(Collectors.toList()));
        dto.setServicos(v.getServicos().stream().map(Servico::getId).collect(Collectors.toList()));
        double total = v.getMercadorias().stream().mapToDouble(Mercadoria::getValor).sum()
                + v.getServicos().stream().mapToDouble(Servico::getValor).sum();
        dto.setTotal(total);
        return dto;
    }
}
