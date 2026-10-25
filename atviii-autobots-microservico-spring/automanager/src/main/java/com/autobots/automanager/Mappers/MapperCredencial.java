package com.autobots.automanager.Mappers;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Request.AtualizarCredencialDTO;
import com.autobots.automanager.DTO.Request.CriarCredencialDTO;
import com.autobots.automanager.DTO.Response.CredencialDTO;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.enumeracoes.TipoCredencial;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;

@Component
public class MapperCredencial {
    public Credencial criarParaCredencial(CriarCredencialDTO dto) {
        Credencial credencial;
        if (dto.getTipo() == TipoCredencial.USUARIO_SENHA) {
            if (isVazio(dto.getNomeUsuario()) || isVazio(dto.getSenha()))
                throw new RequisicaoInvalida("Erro ao criar Credencial", "USUARIO_SENHA exige nomeUsuario e senha");
            CredencialUsuarioSenha us = new CredencialUsuarioSenha();
            us.setNomeUsuario(dto.getNomeUsuario());
            us.setSenha(dto.getSenha());
            credencial = us;
        } else {
            if (dto.getCodigo() == null)
                throw new RequisicaoInvalida("Erro ao criar Credencial", "CODIGO_BARRA exige codigo");
            CredencialCodigoBarra cb = new CredencialCodigoBarra();
            cb.setCodigo(dto.getCodigo());
            credencial = cb;
        }
        credencial.setInativo(dto.isInativo());
        credencial.setCriacao(new Date());
        return credencial;
    }

    public void atualizarCredencial(AtualizarCredencialDTO dto, Credencial credencial) {
        if (dto.getInativo() != null)
            credencial.setInativo(dto.getInativo());
        if (credencial instanceof CredencialUsuarioSenha) {
            CredencialUsuarioSenha us = (CredencialUsuarioSenha) credencial;
            if (!isVazio(dto.getNomeUsuario()))
                us.setNomeUsuario(dto.getNomeUsuario());
            if (!isVazio(dto.getSenha()))
                us.setSenha(dto.getSenha());
        } else if (credencial instanceof CredencialCodigoBarra && dto.getCodigo() != null) {
            ((CredencialCodigoBarra) credencial).setCodigo(dto.getCodigo());
        }
    }

    public CredencialDTO credencialParaDTO(Credencial credencial) {
        CredencialDTO dto = new CredencialDTO();
        dto.setId(credencial.getId());
        dto.setCriacao(credencial.getCriacao());
        dto.setUltimoAcesso(credencial.getUltimoAcesso());
        dto.setInativo(credencial.isInativo());
        if (credencial.getUsuario() != null)
            dto.setUsuario(credencial.getUsuario().getId());
        if (credencial instanceof CredencialUsuarioSenha) {
            dto.setTipo(TipoCredencial.USUARIO_SENHA);
            dto.setNomeUsuario(((CredencialUsuarioSenha) credencial).getNomeUsuario());
        } else if (credencial instanceof CredencialCodigoBarra) {
            dto.setTipo(TipoCredencial.CODIGO_BARRA);
            dto.setCodigo(((CredencialCodigoBarra) credencial).getCodigo());
        }
        return dto;
    }

    private boolean isVazio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
