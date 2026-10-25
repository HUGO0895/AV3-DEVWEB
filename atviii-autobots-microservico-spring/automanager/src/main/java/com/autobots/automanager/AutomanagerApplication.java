package com.autobots.automanager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@SpringBootApplication
public class AutomanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Component
	public static class Runner implements ApplicationRunner {
		@Autowired
		private EmpresaRepositorio repositorioEmpresa;
		@Autowired
		private UsuarioRepositorio repositorioUsuario;
		@Autowired
		private MercadoriaRepositorio repositorioMercadoria;
		@Autowired
		private ServicoRepositorio repositorioServico;
		@Autowired
		private VeiculoRepositorio repositorioVeiculo;
		@Autowired
		private VendaRepositorio repositorioVenda;

		@Override
		public void run(ApplicationArguments args) throws Exception {
			// ---------- empresa ----------
			Empresa empresa = new Empresa();
			empresa.setRazaoSocial("Car service toyota ltda");
			empresa.setNomeFantasia("Car service manutenção veicular");
			empresa.setCadastro(new Date());

			Endereco enderecoEmpresa = new Endereco();
			enderecoEmpresa.setEstado("São Paulo");
			enderecoEmpresa.setCidade("São Paulo");
			enderecoEmpresa.setBairro("Centro");
			enderecoEmpresa.setRua("Av. São João");
			enderecoEmpresa.setNumero("00");
			enderecoEmpresa.setCodigoPostal("01035-000");
			empresa.setEndereco(enderecoEmpresa);

			Telefone telefoneEmpresa = new Telefone();
			telefoneEmpresa.setDdd("011");
			telefoneEmpresa.setNumero("986454527");
			telefoneEmpresa.setEmpresa(empresa);
			empresa.getTelefones().add(telefoneEmpresa);

			empresa = repositorioEmpresa.save(empresa);

			// ---------- funcionario ----------
			Usuario funcionario = new Usuario();
			funcionario.setNome("Pedro Alcântara de Bragança e Bourbon");
			funcionario.setNomeSocial("Dom Pedro");
			funcionario.getPerfis().add(PerfilUsuario.FUNCIONARIO);
			funcionario.setEmpresa(empresa);

			Email emailFuncionario = new Email();
			emailFuncionario.setEndereco("a@a.com");
			emailFuncionario.setUsuario(funcionario);
			funcionario.getEmails().add(emailFuncionario);

			Endereco enderecoFuncionario = new Endereco();
			enderecoFuncionario.setEstado("São Paulo");
			enderecoFuncionario.setCidade("São Paulo");
			enderecoFuncionario.setBairro("Jardins");
			enderecoFuncionario.setRua("Av. São Gabriel");
			enderecoFuncionario.setNumero("00");
			enderecoFuncionario.setCodigoPostal("01435-001");
			funcionario.setEndereco(enderecoFuncionario);

			Telefone telefoneFuncionario = new Telefone();
			telefoneFuncionario.setDdd("011");
			telefoneFuncionario.setNumero("9854633728");
			telefoneFuncionario.setUsuario(funcionario);
			funcionario.getTelefones().add(telefoneFuncionario);

			Documento cpf = new Documento();
			cpf.setDataEmissao(new Date());
			cpf.setNumero("856473819229");
			cpf.setTipo(TipoDocumento.CPF);
			cpf.setUsuario(funcionario);
			funcionario.getDocumentos().add(cpf);

			CredencialUsuarioSenha credencialFuncionario = new CredencialUsuarioSenha();
			credencialFuncionario.setInativo(false);
			credencialFuncionario.setNomeUsuario("dompedrofuncionario");
			credencialFuncionario.setSenha("123456");
			credencialFuncionario.setCriacao(new Date());
			credencialFuncionario.setUltimoAcesso(new Date());
			credencialFuncionario.setUsuario(funcionario);
			funcionario.getCredenciais().add(credencialFuncionario);

			funcionario = repositorioUsuario.save(funcionario);

			// ---------- fornecedor ----------
			Usuario fornecedor = new Usuario();
			fornecedor.setNome("Componentes varejo de partes automotivas ltda");
			fornecedor.setNomeSocial("Loja do carro, vendas de componentes automotivos");
			fornecedor.getPerfis().add(PerfilUsuario.FORNECEDOR);
			fornecedor.setEmpresa(empresa);

			Email emailFornecedor = new Email();
			emailFornecedor.setEndereco("f@f.com");
			emailFornecedor.setUsuario(fornecedor);
			fornecedor.getEmails().add(emailFornecedor);

			CredencialUsuarioSenha credencialFornecedor = new CredencialUsuarioSenha();
			credencialFornecedor.setInativo(false);
			credencialFornecedor.setNomeUsuario("dompedrofornecedor");
			credencialFornecedor.setSenha("123456");
			credencialFornecedor.setCriacao(new Date());
			credencialFornecedor.setUltimoAcesso(new Date());
			credencialFornecedor.setUsuario(fornecedor);
			fornecedor.getCredenciais().add(credencialFornecedor);

			Documento cnpj = new Documento();
			cnpj.setDataEmissao(new Date());
			cnpj.setNumero("00014556000100");
			cnpj.setTipo(TipoDocumento.CNPJ);
			cnpj.setUsuario(fornecedor);
			fornecedor.getDocumentos().add(cnpj);

			Endereco enderecoFornecedor = new Endereco();
			enderecoFornecedor.setEstado("Rio de Janeiro");
			enderecoFornecedor.setCidade("Rio de Janeiro");
			enderecoFornecedor.setBairro("Centro");
			enderecoFornecedor.setRua("Av. República do chile");
			enderecoFornecedor.setNumero("00");
			enderecoFornecedor.setCodigoPostal("20031-170");
			fornecedor.setEndereco(enderecoFornecedor);

			fornecedor = repositorioUsuario.save(fornecedor);

			// ---------- mercadoria ----------
			Mercadoria rodaLigaLeve = new Mercadoria();
			rodaLigaLeve.setCadastro(new Date());
			rodaLigaLeve.setFabricao(new Date());
			rodaLigaLeve.setNome("Roda de liga leve modelo toyota etios");
			rodaLigaLeve.setValidade(new Date());
			rodaLigaLeve.setQuantidade(30);
			rodaLigaLeve.setValor(300.0);
			rodaLigaLeve.setDescricao("Roda de liga leve original de fábrica da toyota para modelos do tipo hatch");
			rodaLigaLeve.setEmpresa(empresa);
			rodaLigaLeve.setFornecedor(fornecedor);
			rodaLigaLeve = repositorioMercadoria.save(rodaLigaLeve);

			// ---------- cliente ----------
			Usuario cliente = new Usuario();
			cliente.setNome("Pedro Alcântara de Bragança e Bourbon");
			cliente.setNomeSocial("Dom pedro cliente");
			cliente.getPerfis().add(PerfilUsuario.CLIENTE);
			cliente.setEmpresa(empresa);

			Email emailCliente = new Email();
			emailCliente.setEndereco("c@c.com");
			emailCliente.setUsuario(cliente);
			cliente.getEmails().add(emailCliente);

			Documento cpfCliente = new Documento();
			cpfCliente.setDataEmissao(new Date());
			cpfCliente.setNumero("12584698533");
			cpfCliente.setTipo(TipoDocumento.CPF);
			cpfCliente.setUsuario(cliente);
			cliente.getDocumentos().add(cpfCliente);

			CredencialUsuarioSenha credencialCliente = new CredencialUsuarioSenha();
			credencialCliente.setInativo(false);
			credencialCliente.setNomeUsuario("dompedrocliente");
			credencialCliente.setSenha("123456");
			credencialCliente.setCriacao(new Date());
			credencialCliente.setUltimoAcesso(new Date());
			credencialCliente.setUsuario(cliente);
			cliente.getCredenciais().add(credencialCliente);

			Endereco enderecoCliente = new Endereco();
			enderecoCliente.setEstado("São Paulo");
			enderecoCliente.setCidade("São José dos Campos");
			enderecoCliente.setBairro("Centro");
			enderecoCliente.setRua("Av. Dr. Nelson D'Ávila");
			enderecoCliente.setNumero("00");
			enderecoCliente.setCodigoPostal("12245-070");
			cliente.setEndereco(enderecoCliente);

			cliente = repositorioUsuario.save(cliente);

			// ---------- veiculo ----------
			Veiculo veiculo = new Veiculo();
			veiculo.setPlaca("ABC-0000");
			veiculo.setModelo("corolla-cross");
			veiculo.setTipo(TipoVeiculo.SUV);
			veiculo.setProprietario(cliente);
			veiculo = repositorioVeiculo.save(veiculo);

			// ---------- servicos ----------
			Servico trocaRodas = new Servico();
			trocaRodas.setDescricao("Troca das rodas do carro por novas");
			trocaRodas.setNome("Troca de rodas");
			trocaRodas.setValor(50);
			trocaRodas.setEmpresa(empresa);
			trocaRodas = repositorioServico.save(trocaRodas);

			Servico alinhamento = new Servico();
			alinhamento.setDescricao("Alinhamento das rodas do carro");
			alinhamento.setNome("Alinhamento de rodas");
			alinhamento.setValor(50);
			alinhamento.setEmpresa(empresa);
			alinhamento = repositorioServico.save(alinhamento);

			Servico balanceamento = new Servico();
			balanceamento.setDescricao("Balanceamento das rodas do carro");
			balanceamento.setNome("Balanceamento de rodas");
			balanceamento.setValor(30);
			balanceamento.setEmpresa(empresa);
			balanceamento = repositorioServico.save(balanceamento);

			// ---------- vendas ----------
			Venda venda = new Venda();
			venda.setCadastro(new Date());
			venda.setIdentificacao("1234698745");
			venda.setEmpresa(empresa);
			venda.setCliente(cliente);
			venda.setFuncionario(funcionario);
			venda.setVeiculo(veiculo);
			venda.getMercadorias().add(rodaLigaLeve);
			venda.getServicos().add(trocaRodas);
			venda.getServicos().add(alinhamento);
			repositorioVenda.save(venda);

			Venda venda2 = new Venda();
			venda2.setCadastro(new Date());
			venda2.setIdentificacao("1234698749");
			venda2.setEmpresa(empresa);
			venda2.setCliente(cliente);
			venda2.setFuncionario(funcionario);
			venda2.setVeiculo(veiculo);
			venda2.getMercadorias().add(rodaLigaLeve);
			venda2.getServicos().add(balanceamento);
			venda2.getServicos().add(alinhamento);
			repositorioVenda.save(venda2);
		}
	}

}
