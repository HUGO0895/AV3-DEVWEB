package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.CriarTelefone;
import com.autobots.automanager.DTO.Request.atualizarTelefone;
import com.autobots.automanager.Mappers.MapperTelefone;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@Service
public class TelefoneService {
    @Autowired
    private TelefoneRepositorio repositorio;
    @Autowired
    private MapperTelefone mapperTelefone;
    @Autowired
    private Localizador localizador;

    public List<Telefone> listarTelefones() {
        return repositorio.findAll();
    }

    public Telefone acharUmTelefone(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procutar Telefone", "Elemento não existe"));
    }

    @Transactional
    public Telefone criarTelefone(CriarTelefone tel) {
        boolean doUsuario = tel.getUsuarioId() != null;
        boolean daEmpresa = tel.getEmpresaId() != null;
        if (doUsuario == daEmpresa)
            throw new RequisicaoInvalida("Erro ao criar Telefone", "Informe usuarioId ou empresaId (apenas um)");
        Telefone telefone = mapperTelefone.CriarTelefoneParaTelefone(tel);
        if (doUsuario)
            telefone.setUsuario(localizador.usuario(tel.getUsuarioId()));
        else
            telefone.setEmpresa(localizador.empresa(tel.getEmpresaId()));
        return repositorio.save(telefone);
    }

    @Transactional
    public void atualizarTelefone(atualizarTelefone tel) {
        Telefone telefone = acharUmTelefone(tel.getId());
        mapperTelefone.AtualizarTelefoneParaTelefone(tel, telefone);
        repositorio.save(telefone);
    }

    @Transactional
    public void deletarTelefone(Long id) {
        Telefone tel = acharUmTelefone(id);
        if (tel.getUsuario() != null)
            tel.getUsuario().getTelefones().removeIf(t -> t.getId().equals(id));
        if (tel.getEmpresa() != null)
            tel.getEmpresa().getTelefones().removeIf(t -> t.getId().equals(id));
        repositorio.delete(tel);
    }
}
