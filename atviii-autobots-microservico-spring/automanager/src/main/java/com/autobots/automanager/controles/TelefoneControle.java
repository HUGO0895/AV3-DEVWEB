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

import com.autobots.automanager.DTO.Request.CriarTelefone;
import com.autobots.automanager.DTO.Request.atualizarTelefone;
import com.autobots.automanager.DTO.Response.TelefoneDto;
import com.autobots.automanager.Mappers.MapperTelefone;
import com.autobots.automanager.Servicos.TelefoneService;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;

@RestController
public class TelefoneControle {
    @Autowired
    TelefoneService telServ;

    @Autowired
    MapperTelefone mapperTel;

    @Autowired
    AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/telefones")
    public ResponseEntity<List<TelefoneDto>> obterTelefones() {
        List<TelefoneDto> lista = telServ.listarTelefones().stream()
                .map(tel -> mapperTel.TelefoneParaTelefoneDTO(tel))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(lista);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/telefones/{id}")
    public ResponseEntity<TelefoneDto> obterTelefone(@PathVariable Long id) {
        TelefoneDto dto = mapperTel.TelefoneParaTelefoneDTO(telServ.acharUmTelefone(id));
        adicionadorLink.adicionarLink(dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/telefones")
    public ResponseEntity<Void> criarTelefone(@RequestBody @Valid CriarTelefone telefone) {
        Telefone salvo = telServ.criarTelefone(telefone);
        URI uri = linkTo(methodOn(TelefoneControle.class).obterTelefone(salvo.getId())).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/telefones")
    public ResponseEntity<Void> atualizarTelefone(@RequestBody @Valid atualizarTelefone tel) {
        telServ.atualizarTelefone(tel);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/telefones/{id}")
    public ResponseEntity<Void> deletarTelefone(@PathVariable Long id) {
        telServ.deletarTelefone(id);
        return ResponseEntity.noContent().build();
    }
}