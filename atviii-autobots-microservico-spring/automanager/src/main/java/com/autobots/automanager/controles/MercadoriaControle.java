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

import com.autobots.automanager.DTO.Request.CriarMercadoriaDTO;
import com.autobots.automanager.DTO.Request.AtualizarMercadoriaDTO;
import com.autobots.automanager.DTO.Response.MercadoriaDTO;
import com.autobots.automanager.Mappers.MapperMercadoria;
import com.autobots.automanager.Servicos.MercadoriaService;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.AdicionadorLinkMercadoria;

@RestController
public class MercadoriaControle {
	@Autowired
	private MercadoriaService servico;

	@Autowired
	private MapperMercadoria mapper;

	@Autowired
	private AdicionadorLinkMercadoria adicionadorLink;

	@GetMapping("/mercadorias")
	public ResponseEntity<List<MercadoriaDTO>> obterMercadorias() {
		List<MercadoriaDTO> lista = servico.listarMercadorias().stream()
				.map(item -> mapper.mercadoriaParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/mercadorias/{id}")
	public ResponseEntity<MercadoriaDTO> obterMercadoria(@PathVariable Long id) {
		MercadoriaDTO dto = mapper.mercadoriaParaDTO(servico.acharUmaMercadoria(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/mercadorias")
	public ResponseEntity<Void> cadastrarMercadoria(@RequestBody @Valid CriarMercadoriaDTO dto) {
		Mercadoria salvo = servico.salvarMercadoria(dto);
		URI uri = linkTo(methodOn(MercadoriaControle.class).obterMercadoria(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/mercadorias")
	public ResponseEntity<Void> atualizarMercadoria(@RequestBody @Valid AtualizarMercadoriaDTO dto) {
		servico.atualizarMercadoria(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/mercadorias/{id}")
	public ResponseEntity<Void> excluirMercadoria(@PathVariable Long id) {
		servico.deletarMercadoria(id);
		return ResponseEntity.noContent().build();
	}
}
