package com.autobots.automanager.controles;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.DTO.Request.CriarDocumento;
import com.autobots.automanager.DTO.Request.atualizarDocumento;
import com.autobots.automanager.DTO.Response.DocumentoDtO;
import com.autobots.automanager.Mappers.MapperDicumento;
import com.autobots.automanager.Servicos.DocumentoService;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;

@RestController
public class DocumentoControle {
    @Autowired
    DocumentoService docServ;

    @Autowired
    MapperDicumento mappperDoc;

    @Autowired
    AdicionadorLinkDocumento adicionadorLink;

    @GetMapping("/documentos")
    public ResponseEntity<List<DocumentoDtO>> listarDocs() {
        List<DocumentoDtO> lista = docServ.pegarDocumentos().stream()
                .map(doc -> mappperDoc.DocumentoParaDTo(doc))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(lista);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/documentos/{id}")
    public ResponseEntity<DocumentoDtO> pegarDoc(@PathVariable Long id) {
        DocumentoDtO dto = mappperDoc.DocumentoParaDTo(docServ.pegarDocumento(id));
        adicionadorLink.adicionarLink(dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/documentos")
    public ResponseEntity<Void> criarDoc(@RequestBody @Valid CriarDocumento doc) {
        Documento salvo = docServ.criarDocumento(doc);
        URI uri = linkTo(methodOn(DocumentoControle.class).pegarDoc(salvo.getId())).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/documentos")
    public ResponseEntity<Void> atualizarDoc(@RequestBody @Valid atualizarDocumento doc) {
        docServ.atualizarDocumento(doc);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Void> deletarDoc(@PathVariable Long id) {
        docServ.deletarDocumento(id);
        return ResponseEntity.noContent().build();
    }
}