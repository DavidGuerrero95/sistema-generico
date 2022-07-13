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
public class ProductoRepositoryImpl implements CrudRepository<Producto> {

    @Inject
    @MysqlConn
    private Connection conn;

    public ProductoRepositoryImpl() {
    }

    @Override
    public void setConnection(Connection conn) {
        this.conn= conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p " +
                     "INNER JOIN categorias as c ON (p.id_categorias = c.id) ")){
            while (resultSet.next()){
                lista.add(crearProductos(resultSet));
            }
        }
        return lista;
    }

    @Override
    public Producto findById(Producto producto) throws SQLException {
        Producto fP = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM productos as p WHERE p.id_productos=?")){
            preparedStatement.setLong(1, producto.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    fP = crearProductos(resultSet);
                }
            }
        }
        return fP;
    }

    @Override
    public Producto findByNombre(Producto producto) throws SQLException {
        Producto fP = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM productos as p WHERE p.nombre=?")){
            preparedStatement.setString(1, producto.getNombre());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    fP = crearProductos(resultSet);
                }
            }
        }
        return fP;
    }

    @Override
    public Producto save(Producto producto) throws SQLException {
        String sql = null;
        if (producto.getId() != null && producto.getId() > 0){
            sql = "UPDATE products SET nombre=?, precio=?, stock=?, descripcion=?, id_categorias=?, id_provedor=?  WHERE id=? ";
        }else {
            sql = "INSERT INTO products(nombre, precio, stock, descripcion, fecha_ingreso, id_categoria, id_provedor) VALUES(?, ?, ?, ?, ?, ?, ?)";
        }
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setLong(2, producto.getPrecio());
            preparedStatement.setInt(3, producto.getStock());
            preparedStatement.setString(4, producto.getDescripcion());
            preparedStatement.setDate(5, new Date(producto.getFechaIngreso().getTime()));
            preparedStatement.setLong(6, producto.getCategoria().getId());
            preparedStatement.setLong(7, producto.getProvedor().getId());
            preparedStatement.executeUpdate();
            if (producto.getId() == null){
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if (resultSet.next()){
                        producto.setId(resultSet.getLong(1));
                    }
                }
            }
        }
        return producto;
    }

    @Override
    public void delete(Producto producto) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM productos WHERE id=?")){
            preparedStatement.setLong(1, producto.getId());
            preparedStatement.executeUpdate();
        }
    }

    private Producto crearProductos(ResultSet resultSet) throws SQLException {
        Producto p = new Producto();
        p.setId(resultSet.getLong("id"));
        p.setNombre(resultSet.getString("nombre"));
        p.setPrecio(resultSet.getLong("precio"));
        p.setStock(resultSet.getInt("stock"));
        p.setDescripcion(resultSet.getString("descripcion"));
        p.setFechaIngreso(resultSet.getDate("fechaIngreso"));
        Categoria c = new Categoria();
        c.setId(resultSet.getLong("id_categoria"));
        p.setCategoria(c);
        Provedor pr = new Provedor();
        pr.setId(resultSet.getLong("id_provedor"));
        p.setProvedor(pr);
        return p;
    }
}
