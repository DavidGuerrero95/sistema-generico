package com.santiago.proyecto.repositories;

import com.santiago.proyecto.configs.MysqlConn;
import com.santiago.proyecto.configs.Repository;
import com.santiago.proyecto.models.Categoria;
import com.santiago.proyecto.models.Producto;
import com.santiago.proyecto.models.Provedor;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProvedorRepositoryImpl implements CrudRepository<Provedor> {

    @Inject
    @MysqlConn
    private Connection conn;

    public ProvedorRepositoryImpl() {
    }

    @Override
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Provedor> listar() throws SQLException {
        List<Provedor> list = new ArrayList<>();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM provedor");){
            while (resultSet.next()){
                list.add(crearProvedor(resultSet));
            }
        }
        return list;
    }

    @Override
    public Provedor findById(Provedor provedor) throws SQLException {
        Long id = provedor.getId();
        Provedor p = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM provedores as p WHERE p.id_provedor=?")){
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    p = crearProvedor(resultSet);
                }
            }
        }
        return p;
    }

    @Override
    public Provedor findByNombre(Provedor provedor) throws SQLException {
        String username = provedor.getUsername();
        Provedor p = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM provedor as p WHERE p.username=?")){
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    p = crearProvedor(resultSet);
                }
            }
        }
        return p;
    }

    @Override
    public Provedor save(Provedor provedor) throws SQLException {
        String sql = null;
        if (provedor.getId() != null && provedor.getId() > 0){
            sql = "UPDATE provedor SET username=?, password=?, nombre=?, direccion=?, telefono=?, celular=?, email=?, nit=? WHERE id=? ";
        }else {
            sql = "INSERT INTO provedor(username, password, nombre, direccion, telefono, celular, email, nit) VALUES(?, ? , ?, ?, ?, ?, ?, ?)";
        }
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, provedor.getUsername());
            preparedStatement.setString(2, provedor.getPassword());
            preparedStatement.setString(3, provedor.getNombre());
            preparedStatement.setString(4, provedor.getDireccion());
            preparedStatement.setString(5, provedor.getTelefono());
            preparedStatement.setString(6, provedor.getCelular());
            preparedStatement.setString(7, provedor.getEmail());
            preparedStatement.setInt(8, provedor.getNit());
            if (provedor.getId() != null && provedor.getId() > 0){
                preparedStatement.setLong(9, provedor.getId());
            }
            preparedStatement.executeUpdate();
            if (provedor.getId() == null){
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if (resultSet.next()){
                        provedor.setId(resultSet.getLong(1));
                    }
                }
            }
        }
        return provedor;
    }

    @Override
    public void delete(Provedor provedor) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM provedor WHERE id=?")){
            preparedStatement.setLong(1, provedor.getId());
            preparedStatement.executeUpdate();
        }
    }

    private Provedor crearProvedor(ResultSet resultSet) throws SQLException {
        Provedor p = new Provedor();
        p.setId(resultSet.getLong("id"));
        p.setUsername(resultSet.getString("username"));
        p.setPassword(resultSet.getString("password"));
        p.setNombre(resultSet.getString("nombre"));
        p.setDireccion(resultSet.getString("direccion"));
        p.setTelefono(resultSet.getString("telefono"));
        p.setCelular(resultSet.getString("celular"));
        p.setEmail(resultSet.getString("email"));
        p.setNit(resultSet.getInt("nit"));
        return p;
    }
}
