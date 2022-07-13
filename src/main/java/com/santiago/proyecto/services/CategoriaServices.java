package com.santiago.proyecto.services;

import com.santiago.proyecto.configs.Service;
import com.santiago.proyecto.models.Categoria;
import com.santiago.proyecto.repositories.CrudRepository;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaServices implements ICategoriaServices{

    @Inject
    private CrudRepository<Categoria> cRepository;

    @Override
    public Categoria findById(Long id) {
        Categoria c = new Categoria();
        c.setId(id);
        try {
            c = cRepository.findById(c);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return c;
    }

    @Override
    public Categoria findByNombre(String nombre) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        try {
            c = cRepository.findByNombre(c);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return c;
    }

    @Override
    public List<Categoria> findAll() {
        List<Categoria> c = new ArrayList<>();
        try {
            c = cRepository.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return c;
    }

    @Override
    public void save(Categoria c) {
        try {
            cRepository.save(c);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void delete(Categoria c) {
        try {
            cRepository.delete(c);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
