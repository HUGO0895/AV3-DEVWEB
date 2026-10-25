package com.autobots.automanager.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {
    boolean existsByClienteId(Long clienteId);

    boolean existsByFuncionarioId(Long funcionarioId);

    boolean existsByVeiculoId(Long veiculoId);

    List<Venda> findByMercadoriasId(Long mercadoriaId);

    List<Venda> findByServicosId(Long servicoId);
}
