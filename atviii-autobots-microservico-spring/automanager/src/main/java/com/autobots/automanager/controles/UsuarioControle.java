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

import com.autobots.automanager.DTO.Request.CriarUsuarioDTO;
import com.autobots.automanager.DTO.Request.AtualizarUsuarioDTO;
import com.autobots.automanager.DTO.Response.UsuarioDTO;
import com.autobots.automanager.Mappers.MapperUsuario;
import com.autobots.automanager.Servicos.UsuarioService;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;

@RestController
public class UsuarioControle {
	@Autowired
	private UsuarioService servico;

	@Autowired
	private MapperUsuario mapper;

	@Autowired
	private AdicionadorLinkUsuario adicionadorLink;

	@GetMapping("/usuarios")
	public ResponseEntity<List<UsuarioDTO>> obterUsuarios() {
		List<UsuarioDTO> lista = servico.listarUsuarios().stream()
				.map(item -> mapper.usuarioParaDTO(item))
				.collect(Collectors.toList());
		adicionadorLink.adicionarLink(lista);
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/usuarios/{id}")
	public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
		UsuarioDTO dto = mapper.usuarioParaDTO(servico.acharUmUsuario(id));
		adicionadorLink.adicionarLink(dto);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/usuarios")
	public ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid CriarUsuarioDTO dto) {
		Usuario salvo = servico.salvarUsuario(dto);
		URI uri = linkTo(methodOn(UsuarioControle.class).obterUsuario(salvo.getId())).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/usuarios")
	public ResponseEntity<Void> atualizarUsuario(@RequestBody @Valid AtualizarUsuarioDTO dto) {
		servico.atualizarUsuario(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
		servico.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
}
