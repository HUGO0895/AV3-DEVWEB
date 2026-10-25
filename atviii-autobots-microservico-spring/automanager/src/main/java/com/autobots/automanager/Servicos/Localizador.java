package com.autobots.automanager.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.exceptions.tipos.NaoexistenoBanco;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;

/** Busca entidades referenciadas por id, lançando 404 quando não existem. */
@Component
public class Localizador {
    @Autowired
    private EmpresaRepositorio empresaRepo;
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    @Autowired
    private VeiculoRepositorio veiculoRepo;
    @Autowired
    private MercadoriaRepositorio mercadoriaRepo;
    @Autowired
    private ServicoRepositorio servicoRepo;

    public Empresa empresa(Long id) {
        return empresaRepo.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Empresa", "Elemento não existe"));
    }

    public Usuario usuario(Long id) {
        return usuarioRepo.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Usuario", "Elemento não existe"));
    }

    public Veiculo veiculo(Long id) {
        return veiculoRepo.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Veiculo", "Elemento não existe"));
    }

    public Mercadoria mercadoria(Long id) {
        return mercadoriaRepo.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Mercadoria", "Elemento não existe"));
    }

    public Servico servico(Long id) {
        return servicoRepo.findById(id).orElseThrow(() -> new NaoexistenoBanco("Erro ao procurar Servico", "Elemento não existe"));
    }
}
