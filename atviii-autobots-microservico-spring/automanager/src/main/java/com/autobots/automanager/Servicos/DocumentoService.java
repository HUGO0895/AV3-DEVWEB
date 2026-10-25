package com.autobots.automanager.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.Request.CriarDocumento;
import com.autobots.automanager.DTO.Request.atualizarDocumento;
import com.autobots.automanager.Mappers.MapperDicumento;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.exceptions.tipos.RequisicaoInvalida;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepositorio docRepo;
    @Autowired
    private MapperDicumento mapperdoc;
    @Autowired
    private Localizador localizador;

    public List<Documento> pegarDocumentos() {
        return docRepo.findAll();
    }

    public Documento pegarDocumento(Long id) {
        return docRepo.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Documento", "Elemento não existe"));
    }

    @Transactional
    public Documento criarDocumento(CriarDocumento documento) {
        if (documento.getUsuarioId() == null)
            throw new RequisicaoInvalida("Erro ao criar Documento", "usuarioId é obrigatorio");
        Documento doc = mapperdoc.criarDtoParaDocumento(documento);
        doc.setUsuario(localizador.usuario(documento.getUsuarioId()));
        return docRepo.save(doc);
    }

    @Transactional
    public void atualizarDocumento(atualizarDocumento documento) {
        Documento doc = pegarDocumento(documento.getId());
        mapperdoc.atualizarDtoParaDocumento(documento, doc);
        docRepo.save(doc);
    }

    @Transactional
    public void deletarDocumento(Long id) {
        Documento doc = pegarDocumento(id);
        if (doc.getUsuario() != null)
            doc.getUsuario().getDocumentos().removeIf(d -> d.getId().equals(id));
        docRepo.delete(doc);
    }
}
