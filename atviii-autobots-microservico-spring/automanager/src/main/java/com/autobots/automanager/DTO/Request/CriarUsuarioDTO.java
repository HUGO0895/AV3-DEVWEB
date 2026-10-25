package com.autobots.automanager.DTO.Request;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarUsuarioDTO {
    @NotBlank(message = "Nome precisa ter valor")
    private String nome;
    private String nomeSocial;
    @NotEmpty(message = "Informe ao menos um perfil (CLIENTE, FORNECEDOR ou FUNCIONARIO)")
    private Set<PerfilUsuario> perfis;
    private Long empresaId;
    @Valid
    private CriarEnderecoDTO endereco;
    @Valid
    private List<CriarTelefone> telefones;
    @Valid
    private List<CriarDocumento> documentos;
    @Valid
    private List<CriarEmailDTO> emails;
}
