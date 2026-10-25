package com.autobots.automanager.modelos;

import java.util.List;

public interface AdicionadorLink<T> {
    void adicionarLink(List<T> lista);
    void adicionarLink(T objeto);
}