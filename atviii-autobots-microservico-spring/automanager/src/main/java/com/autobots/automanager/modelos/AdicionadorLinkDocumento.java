package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.Response.DocumentoDtO;
import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.controles.UsuarioControle;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<DocumentoDtO> {

    @Override
    public void adicionarLink(List<DocumentoDtO> lista) {
        for (DocumentoDtO doc : lista) {
            doc.add(linkTo(methodOn(DocumentoControle.class).pegarDoc(doc.getId())).withSelfRel());
        }
    }

    @Override
    public void adicionarLink(DocumentoDtO doc) {
        doc.add(linkTo(methodOn(DocumentoControle.class).pegarDoc(doc.getId())).withSelfRel());
        doc.add(linkTo(methodOn(DocumentoControle.class).listarDocs()).withRel("documentos"));

        // usuario dono do documento (outro controller)
        if (doc.getUsuario() != null) {
            doc.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(doc.getUsuario())).withRel("usuario"));
        }
    }
}
