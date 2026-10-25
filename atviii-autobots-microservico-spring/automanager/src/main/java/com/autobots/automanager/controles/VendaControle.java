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

import com.autobots.automanager.DTO.Request.CriarVendaDTO;
import com.autobots.automanager.DTO.Request.AtualizarVendaDTO;
import com.autobots.automanager.DTO.Response.VendaDTO;
import com.autobots.automanager.Mappers.MapperVenda;
import com.autobots.automanager.Servicos.VendaService;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.AdicionadorLinkVenda;

@RestController
public class VendaControle {
	@Autowired
	private VendaService servico;

	@Autowired
	private MapperVenda mapper;

	@Autowired
	private AdicionadorLinkVenda adicionadorLink;

	@GetMapping("/vendas")
	public ResponseEntity<List<VendaDTO>> obterVendas() {
		List<VendaDTO> lista = servico.listarVendas().stream()
				.map(item -> mapper.vendaParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/vendas/{id}")
	public ResponseEntity<VendaDTO> obterVenda(@PathVariable Long id) {
		VendaDTO dto = mapper.vendaParaDTO(servico.acharUmaVenda(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/vendas")
	public ResponseEntity<Void> cadastrarVenda(@RequestBody @Valid CriarVendaDTO dto) {
		Venda salvo = servico.salvarVenda(dto);
		URI uri = linkTo(methodOn(VendaControle.class).obterVenda(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/vendas")
	public ResponseEntity<Void> atualizarVenda(@RequestBody @Valid AtualizarVendaDTO dto) {
		servico.atualizarVenda(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/vendas/{id}")
	public ResponseEntity<Void> excluirVenda(@PathVariable Long id) {
		servico.deletarVenda(id);
		return ResponseEntity.noContent().build();
	}
}
