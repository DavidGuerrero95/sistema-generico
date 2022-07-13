package com.santiago.proyecto.services;

import com.santiago.proyecto.configs.Service;
import com.santiago.proyecto.models.Categoria;
import com.santiago.proyecto.models.Producto;
import com.santiago.proyecto.repositories.CrudRepository;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosServices implements IProductosServices{

    @Inject
    private CrudRepository<Producto> pRepository;


    @Override
    public Producto findById(Long id) {
        Producto p = new Producto();
        p.setId(id);
        try {
            p = pRepository.findById(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return p;
    }

    @Override
    public Producto findByNombre(String nombre) {
        Producto p = new Producto();
        p.setNombre(nombre);
        try {
            p = pRepository.findByNombre(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return p;
    }

    @Override
    public List<Producto> find() {
        List<Producto> pL = new ArrayList<>();
        try {
            pL = pRepository.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return pL;
    }

    @Override
    public void save(Producto p) {
        try {
            pRepository.save(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void delete(Producto p) {
        try {
            pRepository.delete(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
