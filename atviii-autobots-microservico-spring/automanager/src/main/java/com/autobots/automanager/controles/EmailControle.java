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

import com.autobots.automanager.DTO.Request.CriarEmailDTO;
import com.autobots.automanager.DTO.Request.AtualizarEmailDTO;
import com.autobots.automanager.DTO.Response.EmailDTO;
import com.autobots.automanager.Mappers.MapperEmail;
import com.autobots.automanager.Servicos.EmailService;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.modelos.AdicionadorLinkEmail;

@RestController
public class EmailControle {
	@Autowired
	private EmailService servico;

	@Autowired
	private MapperEmail mapper;

	@Autowired
	private AdicionadorLinkEmail adicionadorLink;

	@GetMapping("/emails")
	public ResponseEntity<List<EmailDTO>> obterEmails() {
		List<EmailDTO> lista = servico.listarEmails().stream()
				.map(item -> mapper.emailParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/emails/{id}")
	public ResponseEntity<EmailDTO> obterEmail(@PathVariable Long id) {
		EmailDTO dto = mapper.emailParaDTO(servico.acharUmEmail(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/emails")
	public ResponseEntity<Void> cadastrarEmail(@RequestBody @Valid CriarEmailDTO dto) {
		Email salvo = servico.criarEmail(dto);
		URI uri = linkTo(methodOn(EmailControle.class).obterEmail(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/emails")
	public ResponseEntity<Void> atualizarEmail(@RequestBody @Valid AtualizarEmailDTO dto) {
		servico.atualizarEmail(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/emails/{id}")
	public ResponseEntity<Void> excluirEmail(@PathVariable Long id) {
		servico.deletarEmail(id);
		return ResponseEntity.noContent().build();
	}
}
