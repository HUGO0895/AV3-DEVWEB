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

import com.autobots.automanager.DTO.Request.CriarEmpresaDTO;
import com.autobots.automanager.DTO.Request.AtualizarEmpresaDTO;
import com.autobots.automanager.DTO.Response.EmpresaDTO;
import com.autobots.automanager.Mappers.MapperEmpresa;
import com.autobots.automanager.Servicos.EmpresaService;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.AdicionadorLinkEmpresa;

@RestController
public class EmpresaControle {
	@Autowired
	private EmpresaService servico;

	@Autowired
	private MapperEmpresa mapper;

	@Autowired
	private AdicionadorLinkEmpresa adicionadorLink;

	@GetMapping("/empresas")
	public ResponseEntity<List<EmpresaDTO>> obterEmpresas() {
		List<EmpresaDTO> lista = servico.listarEmpresas().stream()
				.map(item -> mapper.empresaParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/empresas/{id}")
	public ResponseEntity<EmpresaDTO> obterEmpresa(@PathVariable Long id) {
		EmpresaDTO dto = mapper.empresaParaDTO(servico.acharUmaEmpresa(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/empresas")
	public ResponseEntity<Void> cadastrarEmpresa(@RequestBody @Valid CriarEmpresaDTO dto) {
		Empresa salvo = servico.salvarEmpresa(dto);
		URI uri = linkTo(methodOn(EmpresaControle.class).obterEmpresa(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/empresas")
	public ResponseEntity<Void> atualizarEmpresa(@RequestBody @Valid AtualizarEmpresaDTO dto) {
		servico.atualizarEmpresa(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/empresas/{id}")
	public ResponseEntity<Void> excluirEmpresa(@PathVariable Long id) {
		servico.deletarEmpresa(id);
		return ResponseEntity.noContent().build();
	}

	// associa um usuario existente a empresa
	@PostMapping("/empresas/{empresaId}/usuarios/{usuarioId}")
	public ResponseEntity<Void> associarUsuario(@PathVariable Long empresaId, @PathVariable Long usuarioId) {
		servico.associarUsuario(empresaId, usuarioId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/empresas/{empresaId}/usuarios/{usuarioId}")
	public ResponseEntity<Void> desassociarUsuario(@PathVariable Long empresaId, @PathVariable Long usuarioId) {
		servico.desassociarUsuario(empresaId, usuarioId);
		return ResponseEntity.noContent().build();
	}
}
