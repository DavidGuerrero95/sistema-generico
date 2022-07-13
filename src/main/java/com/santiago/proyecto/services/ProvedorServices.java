package com.santiago.proyecto.services;

import com.santiago.proyecto.configs.Service;
import com.santiago.proyecto.models.Categoria;
import com.santiago.proyecto.models.Provedor;
import com.santiago.proyecto.repositories.CrudRepository;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvedorServices implements IProvedorServices{

    @Inject
    private CrudRepository<Provedor> pRepository;

    @Override
    public Provedor findById(Long id) {
        Provedor p = new Provedor();
        p.setId(id);
        try {
            p = pRepository.findById(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return p;
    }

    @Override
    public Provedor findByNombre(String nombre) {
        Provedor p = new Provedor();
        p.setNombre(nombre);
        try {
            p = pRepository.findByNombre(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return p;
    }

    @Override
    public List<Provedor> findAll() {
        List<Provedor> p = new ArrayList<>();
        try {
            p = pRepository.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
        return p;
    }

    @Override
    public void save(Provedor p) {
        try {
            pRepository.save(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void delete(Provedor p) {
        try {
            pRepository.delete(p);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
