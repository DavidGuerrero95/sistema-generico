package com.santiago.proyecto.services;

import com.santiago.proyecto.models.Categoria;
import com.santiago.proyecto.models.Producto;

import java.util.List;

public interface IProductosServices {

    Producto findById(Long id);

    Producto findByNombre(String nombre);

    List<Producto> find();

    void save(Producto p);

    void delete(Producto p);

}
