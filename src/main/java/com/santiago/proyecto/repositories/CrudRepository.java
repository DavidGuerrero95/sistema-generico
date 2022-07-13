package com.santiago.proyecto.repositories;

import com.santiago.proyecto.models.Categoria;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {

    void setConnection(Connection conn);
    List<T> listar() throws SQLException;
    T findById(T t) throws SQLException;
    T findByNombre(T t) throws SQLException;
    T save(T t) throws SQLException;
    void delete(T t) throws SQLException;

}
