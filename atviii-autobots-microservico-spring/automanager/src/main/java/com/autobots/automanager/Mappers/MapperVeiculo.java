package com.autobots.automanager.Mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Request.AtualizarVeiculoDTO;
import com.autobots.automanager.DTO.Request.CriarVeiculoDTO;
import com.autobots.automanager.DTO.Response.VeiculoDTO;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;

@Component
public class MapperVeiculo {
    public Veiculo criarParaVeiculo(CriarVeiculoDTO dto) {
        Veiculo v = new Veiculo();
        v.setTipo(dto.getTipo());
        v.setModelo(dto.getModelo());
        v.setPlaca(dto.getPlaca());
        return v;
    }

    public void atualizarVeiculo(AtualizarVeiculoDTO dto, Veiculo v) {
        if (dto.getTipo() != null)
            v.setTipo(dto.getTipo());
        if (dto.getModelo() != null && !dto.getModelo().isEmpty())
            v.setModelo(dto.getModelo());
        if (dto.getPlaca() != null && !dto.getPlaca().isEmpty())
            v.setPlaca(dto.getPlaca());
    }

    public VeiculoDTO veiculoParaDTO(Veiculo v) {
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(v.getId());
        dto.setTipo(v.getTipo());
        dto.setModelo(v.getModelo());
        dto.setPlaca(v.getPlaca());
        if (v.getProprietario() != null)
            dto.setProprietario(v.getProprietario().getId());
        dto.setVendas(v.getVendas().stream().map(Venda::getId).collect(Collectors.toList()));
        return dto;
    }
}
