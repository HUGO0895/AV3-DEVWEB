package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String razaoSocial;
	@Column
	private String nomeFantasia;
	@Column(nullable = false)
	private Date cadastro;
	@OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Telefone> telefones = new HashSet<>();
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	@OneToMany(mappedBy = "empresa")
	private Set<Usuario> usuarios = new HashSet<>();
	@OneToMany(mappedBy = "empresa")
	private Set<Mercadoria> mercadorias = new HashSet<>();
	@OneToMany(mappedBy = "empresa")
	private Set<Servico> servicos = new HashSet<>();
	@OneToMany(mappedBy = "empresa")
	private Set<Venda> vendas = new HashSet<>();
}
