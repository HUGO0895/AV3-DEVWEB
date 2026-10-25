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

import com.autobots.automanager.DTO.Request.CriarServicoDTO;
import com.autobots.automanager.DTO.Request.AtualizarServicoDTO;
import com.autobots.automanager.DTO.Response.ServicoDTO;
import com.autobots.automanager.Mappers.MapperServico;
import com.autobots.automanager.Servicos.ServicoService;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.AdicionadorLinkServico;

@RestController
public class ServicoControle {
	@Autowired
	private ServicoService servico;

	@Autowired
	private MapperServico mapper;

	@Autowired
	private AdicionadorLinkServico adicionadorLink;

	@GetMapping("/servicos")
	public ResponseEntity<List<ServicoDTO>> obterServicos() {
		List<ServicoDTO> lista = servico.listarServicos().stream()
				.map(item -> mapper.servicoParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/servicos/{id}")
	public ResponseEntity<ServicoDTO> obterServico(@PathVariable Long id) {
		ServicoDTO dto = mapper.servicoParaDTO(servico.acharUmServico(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/servicos")
	public ResponseEntity<Void> cadastrarServico(@RequestBody @Valid CriarServicoDTO dto) {
		Servico salvo = servico.salvarServico(dto);
		URI uri = linkTo(methodOn(ServicoControle.class).obterServico(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/servicos")
	public ResponseEntity<Void> atualizarServico(@RequestBody @Valid AtualizarServicoDTO dto) {
		servico.atualizarServico(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/servicos/{id}")
	public ResponseEntity<Void> excluirServico(@PathVariable Long id) {
		servico.deletarServico(id);
		return ResponseEntity.noContent().build();
	}
}
