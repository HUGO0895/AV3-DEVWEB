package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.AtualizarCredencialDTO;
import com.autobots.automanager.DTO.Request.CriarCredencialDTO;
import com.autobots.automanager.Mappers.MapperCredencial;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.repositorios.CredencialRepositorio;

@Service
public class CredencialService {
    @Autowired
    private CredencialRepositorio repositorio;
    @Autowired
    private MapperCredencial mapper;
    @Autowired
    private Localizador localizador;

    public List<Credencial> listarCredenciais() {
        return repositorio.findAll();
    }

    public Credencial acharUmaCredencial(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Credencial", "Elemento não existe"));
    }

    @Transactional
    public Credencial criarCredencial(CriarCredencialDTO dto) {
        Credencial credencial = mapper.criarParaCredencial(dto);
        credencial.setUsuario(localizador.usuario(dto.getUsuarioId()));
        return repositorio.save(credencial);
    }

    @Transactional
    public void atualizarCredencial(AtualizarCredencialDTO dto) {
        Credencial credencial = acharUmaCredencial(dto.getId());
        mapper.atualizarCredencial(dto, credencial);
        repositorio.save(credencial);
    }

    @Transactional
    public void deletarCredencial(Long id) {
        Credencial credencial = acharUmaCredencial(id);
        if (credencial.getUsuario() != null)
            credencial.getUsuario().getCredenciais().removeIf(c -> c.getId().equals(id));
        repositorio.delete(credencial);
    }
}
