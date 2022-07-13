package com.santiago.proyecto.services;

import com.santiago.proyecto.models.Categoria;

import java.util.List;

public interface ICategoriaServices {

    Categoria findById(Long id);

    Categoria findByNombre(String nombre);

    List<Categoria> findAll();

    void save(Categoria c);

    void delete(Categoria c);

}
