package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarEmailDTO;
import com.autobots.automanager.DTO.Request.CriarEmailDTO;
import com.autobots.automanager.Mappers.MapperEmail;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;
import com.autobots.automanager.repositorios.EmailRepositorio;

@Service
public class EmailService {
    @Autowired
    private EmailRepositorio repositorio;
    @Autowired
    private MapperEmail mapper;
    @Autowired
    private Localizador localizador;

    public List<Email> listarEmails() {
        return repositorio.findAll();
    }

    public Email acharUmEmail(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Email", "Elemento não existe"));
    }

    @Transactional
    public Email criarEmail(CriarEmailDTO dto) {
        if (dto.getUsuarioId() == null)
            throw new RequisicaoInvalida("Erro ao criar Email", "usuarioId é obrigatorio");
        Email email = mapper.criarParaEmail(dto);
        email.setUsuario(localizador.usuario(dto.getUsuarioId()));
        return repositorio.save(email);
    }

    @Transactional
    public void atualizarEmail(AtualizarEmailDTO dto) {
        Email email = acharUmEmail(dto.getId());
        mapper.atualizarEmail(dto, email);
        repositorio.save(email);
    }

    @Transactional
    public void deletarEmail(Long id) {
        Email email = acharUmEmail(id);
        if (email.getUsuario() != null)
            email.getUsuario().getEmails().removeIf(e -> e.getId().equals(id));
        repositorio.delete(email);
    }
}
