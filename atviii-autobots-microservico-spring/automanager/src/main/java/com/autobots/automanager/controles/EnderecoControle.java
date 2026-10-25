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

import com.autobots.automanager.DTO.Request.CriarEnderecoDTO;
import com.autobots.automanager.DTO.Request.atualizarEndereciDTO;
import com.autobots.automanager.DTO.Response.EnderecoDTO;
import com.autobots.automanager.Mappers.MapperEndereco;
import com.autobots.automanager.Servicos.EnderecoService;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;

@RestController
public class EnderecoControle {
    @Autowired
    private EnderecoService endeServ;

    @Autowired
    private MapperEndereco mapperEnde;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping("/enderecos")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos() {
        List<EnderecoDTO> lista = endeServ.listarOsEnderecos().stream()
                .map(endereco -> mapperEnde.enderecoParaDTO(endereco))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(lista);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/enderecos/{id}")
    public ResponseEntity<EnderecoDTO> pegarEndereco(@PathVariable Long id) {
        EnderecoDTO dto = mapperEnde.enderecoParaDTO(endeServ.acharEnderecoPorId(id));
        adicionadorLink.adicionarLink(dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        endeServ.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enderecos")
    public ResponseEntity<Void> criarEndereco(@RequestBody @Valid CriarEnderecoDTO endereco) {
        Endereco salvo = this.endeServ.criarEndereco(endereco);
        URI uri = linkTo(methodOn(EnderecoControle.class).pegarEndereco(salvo.getId())).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/enderecos")
    public ResponseEntity<Void> atualizarEndereco(@RequestBody @Valid atualizarEndereciDTO endereco) {
        endeServ.atualizarEndereco(endereco);
        return ResponseEntity.noContent().build();
    }
}