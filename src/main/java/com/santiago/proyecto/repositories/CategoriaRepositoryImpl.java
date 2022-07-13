package com.santiago.proyecto.repositories;

import com.santiago.proyecto.configs.MysqlConn;
import com.santiago.proyecto.configs.Repository;
import com.santiago.proyecto.models.Categoria;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaRepositoryImpl implements CrudRepository<Categoria> {

    @Inject
    @MysqlConn
    private Connection conn;

    public CategoriaRepositoryImpl() {}

    @Override
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> list = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM categorias");){
            while (resultSet.next()){
                list.add(crearCategoria(resultSet));
            }
        }
        return list;
    }

    @Override
    public Categoria findById(Categoria categoria) throws SQLException {
        Long id = categoria.getId();
        Categoria fC = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM categorias as c WHERE c.id=?")){
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    fC = crearCategoria(resultSet);
                }
            }
        }
        return fC;
    }

    @Override
    public Categoria findByNombre(Categoria categoria) throws SQLException {
        String nombre = categoria.getNombre();
        Categoria fC = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM categorias as c WHERE c.nombre=?")){
            preparedStatement.setString(1, nombre);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    fC = crearCategoria(resultSet);
                }
            }
        }
        return fC;
    }

    @Override
    public Categoria save(Categoria categoria) throws SQLException {
        String sql = null;
        if (categoria.getId() != null && categoria.getId() > 0){
            sql = "UPDATE categories SET nombre=? WHERE id=? ";
        }else {
            sql = "INSERT INTO categorias(nombre) VALUES(?)";
        }
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, categoria.getNombre());
            if (categoria.getId() != null && categoria.getId() > 0){
                preparedStatement.setLong(2, categoria.getId());
            }
            preparedStatement.executeUpdate();
            if (categoria.getId() == null){
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if (resultSet.next()){
                        categoria.setId(resultSet.getLong(1));
                    }
                }
            }
        }
        return categoria;
    }

    @Override
    public void delete(Categoria categoria) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM categorias WHERE id=?")){
            preparedStatement.setLong(1, categoria.getId());
            preparedStatement.executeUpdate();
        }
    }

    private Categoria crearCategoria(ResultSet resultSet) throws SQLException {
        Categoria c = new Categoria();
        c.setId(resultSet.getLong("id"));
        c.setNombre(resultSet.getString("name"));
        return c;
    }

    private Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setNombre(rs.getString("nombre"));
        categoria.setId(rs.getLong("id"));
        return categoria;
    }
}
