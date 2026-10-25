package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false, unique = true)
	private String identificacao;
	@ManyToOne
	private Empresa empresa;
	@ManyToOne
	private Usuario cliente;
	@ManyToOne
	private Usuario funcionario;
	@ManyToOne
	private Veiculo veiculo;
	// ManyToMany: o mesmo item de catalogo pode aparecer em varias vendas
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Mercadoria> mercadorias = new HashSet<>();
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Servico> servicos = new HashSet<>();
}
