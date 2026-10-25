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

import com.autobots.automanager.DTO.Request.CriarVeiculoDTO;
import com.autobots.automanager.DTO.Request.AtualizarVeiculoDTO;
import com.autobots.automanager.DTO.Response.VeiculoDTO;
import com.autobots.automanager.Mappers.MapperVeiculo;
import com.autobots.automanager.Servicos.VeiculoService;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;

@RestController
public class VeiculoControle {
	@Autowired
	private VeiculoService servico;

	@Autowired
	private MapperVeiculo mapper;

	@Autowired
	private AdicionadorLinkVeiculo adicionadorLink;

	@GetMapping("/veiculos")
	public ResponseEntity<List<VeiculoDTO>> obterVeiculos() {
		List<VeiculoDTO> lista = servico.listarVeiculos().stream()
				.map(item -> mapper.veiculoParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/veiculos/{id}")
	public ResponseEntity<VeiculoDTO> obterVeiculo(@PathVariable Long id) {
		VeiculoDTO dto = mapper.veiculoParaDTO(servico.acharUmVeiculo(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/veiculos")
	public ResponseEntity<Void> cadastrarVeiculo(@RequestBody @Valid CriarVeiculoDTO dto) {
		Veiculo salvo = servico.salvarVeiculo(dto);
		URI uri = linkTo(methodOn(VeiculoControle.class).obterVeiculo(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/veiculos")
	public ResponseEntity<Void> atualizarVeiculo(@RequestBody @Valid AtualizarVeiculoDTO dto) {
		servico.atualizarVeiculo(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/veiculos/{id}")
	public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {
		servico.deletarVeiculo(id);
		return ResponseEntity.noContent().build();
	}
}
