package com.santiago.proyecto.services;

import com.santiago.proyecto.models.Categoria;
import com.santiago.proyecto.models.Provedor;

import java.util.List;

public interface IProvedorServices {

    Provedor findById(Long id);

    Provedor findByNombre(String nombre);

    List<Provedor> findAll();

    void save(Provedor c);

    void delete(Provedor c);

}
