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

import com.autobots.automanager.DTO.Request.CriarCredencialDTO;
import com.autobots.automanager.DTO.Request.AtualizarCredencialDTO;
import com.autobots.automanager.DTO.Response.CredencialDTO;
import com.autobots.automanager.Mappers.MapperCredencial;
import com.autobots.automanager.Servicos.CredencialService;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.modelos.AdicionadorLinkCredencial;

@RestController
public class CredencialControle {
	@Autowired
	private CredencialService servico;

	@Autowired
	private MapperCredencial mapper;

	@Autowired
	private AdicionadorLinkCredencial adicionadorLink;

	@GetMapping("/credenciais")
	public ResponseEntity<List<CredencialDTO>> obterCredenciais() {
		List<CredencialDTO> lista = servico.listarCredenciais().stream()
				.map(item -> mapper.credencialParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/credenciais/{id}")
	public ResponseEntity<CredencialDTO> obterCredencial(@PathVariable Long id) {
		CredencialDTO dto = mapper.credencialParaDTO(servico.acharUmaCredencial(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/credenciais")
	public ResponseEntity<Void> cadastrarCredencial(@RequestBody @Valid CriarCredencialDTO dto) {
		Credencial salvo = servico.criarCredencial(dto);
		URI uri = linkTo(methodOn(CredencialControle.class).obterCredencial(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/credenciais")
	public ResponseEntity<Void> atualizarCredencial(@RequestBody @Valid AtualizarCredencialDTO dto) {
		servico.atualizarCredencial(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/credenciais/{id}")
	public ResponseEntity<Void> excluirCredencial(@PathVariable Long id) {
		servico.deletarCredencial(id);
		return ResponseEntity.noContent().build();
	}
}
