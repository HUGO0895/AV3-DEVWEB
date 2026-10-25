package com.autobots.automanager.Mappers;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Request.AtualizarEmailDTO;
import com.autobots.automanager.DTO.Request.CriarEmailDTO;
import com.autobots.automanager.DTO.Response.EmailDTO;
import com.autobots.automanager.entidades.Email;

@Component
public class MapperEmail {
    public Email criarParaEmail(CriarEmailDTO dto) {
        Email email = new Email();
        email.setEndereco(dto.getEndereco());
        return email;
    }

    public void atualizarEmail(AtualizarEmailDTO dto, Email email) {
        if (dto.getEndereco() != null && !dto.getEndereco().isEmpty())
            email.setEndereco(dto.getEndereco());
    }

    public EmailDTO emailParaDTO(Email email) {
        EmailDTO dto = new EmailDTO();
        dto.setId(email.getId());
        dto.setEndereco(email.getEndereco());
        if (email.getUsuario() != null)
            dto.setUsuario(email.getUsuario().getId());
        return dto;
    }
}
