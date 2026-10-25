package com.autobots.automanager.Mappers;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Request.AtualizarEmpresaDTO;
import com.autobots.automanager.DTO.Request.CriarEmpresaDTO;
import com.autobots.automanager.DTO.Response.EmpresaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;

@Component
public class MapperEmpresa {
    @Autowired
    MapperTelefone mapperTel;
    @Autowired
    MapperEndereco mapperEnd;

    public Empresa criarParaEmpresa(CriarEmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setCadastro(dto.getCadastro() != null ? dto.getCadastro() : new Date());
        if (dto.getEndereco() != null)
            empresa.setEndereco(mapperEnd.CriarEnderecoDtoParaEndereco(dto.getEndereco()));
        if (dto.getTelefones() != null) {
            dto.getTelefones().forEach(t -> {
                Telefone tel = mapperTel.CriarTelefoneParaTelefone(t);
                tel.setEmpresa(empresa);
                empresa.getTelefones().add(tel);
            });
        }
        return empresa;
    }

    public void atualizarEmpresa(AtualizarEmpresaDTO dto, Empresa empresa) {
        if (dto.getRazaoSocial() != null && !dto.getRazaoSocial().isEmpty())
            empresa.setRazaoSocial(dto.getRazaoSocial());
        if (dto.getNomeFantasia() != null && !dto.getNomeFantasia().isEmpty())
            empresa.setNomeFantasia(dto.getNomeFantasia());
    }

    public EmpresaDTO empresaParaDTO(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setNomeFantasia(empresa.getNomeFantasia());
        dto.setCadastro(empresa.getCadastro());
        if (empresa.getEndereco() != null)
            dto.setEndereco(mapperEnd.enderecoParaDTO(empresa.getEndereco()));
        dto.setTelefones(empresa.getTelefones().stream().map(mapperTel::TelefoneParaTelefoneDTO).collect(Collectors.toList()));
        dto.setUsuarios(empresa.getUsuarios().stream().map(Usuario::getId).collect(Collectors.toList()));
        dto.setMercadorias(empresa.getMercadorias().stream().map(Mercadoria::getId).collect(Collectors.toList()));
        dto.setServicos(empresa.getServicos().stream().map(Servico::getId).collect(Collectors.toList()));
        dto.setVendas(empresa.getVendas().stream().map(Venda::getId).collect(Collectors.toList()));
        return dto;
    }
}
