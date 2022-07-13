package com.santiago.proyecto.controllers;


import com.santiago.proyecto.models.Categoria;
import com.santiago.proyecto.models.Producto;
import com.santiago.proyecto.models.Provedor;
import com.santiago.proyecto.services.CategoriaServices;
import com.santiago.proyecto.services.ProductosServices;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/provedores/productos")
public class CrearProductoServlet extends HttpServlet {

    @Inject
    private ProductosServices pServices;

    @Inject
    private CategoriaServices cServices;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Categoria> c = cServices.findAll();
        req.setAttribute("categories", c );
        Producto p = new Producto();
        req.setAttribute("product", p);
        getServletContext().getRequestDispatcher("/WEB-INF/pages/formProducts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        Long precio;

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        try {
            precio = Long.valueOf(req.getParameter("precio"));
        } catch (NumberFormatException e){
            precio = 0L;
            System.out.println("***** Precio: " +  precio);
        }

        String descripcion = req.getParameter("descripcion");

        String fIngreso = req.getParameter("fechaIngreso");
        Long idCategoria;
        try {
            idCategoria = Long.parseLong(req.getParameter("categoria"));
        } catch (NumberFormatException e){
            idCategoria = 0L;
        }

        Long idProvedor;
        try {
            idProvedor = Long.parseLong(req.getParameter("provedor"));
        } catch (NumberFormatException e){
            idProvedor = 0L;
        }

        Map<String, String> errors = new HashMap<>();
        if (nombre == null || nombre.isBlank()){
            errors.put("nombre", "Nombre es requerido!");
        }
        if (fIngreso == null || fIngreso.isBlank()){
            errors.put("fechaIngreso", "Fecha es requerido!");
        }
        if (precio.equals(0l)) {
            errors.put("precio", "Precio es requerido!");
            System.out.println("***** Precio es igual 0 ");
        }
        if (idCategoria.equals(0L)){
            errors.put("categoria", "La categoria es requerida!");
        }

        if (idCategoria.equals(0L)){
            errors.put("categoria", "La categoria es requerida!");
        }

        Date fecha;

        try {
            fecha = formatDate.parse(fIngreso);
        } catch (ParseException e) {
            fecha = null;
        }
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e){
            id = 0L;
        }
        Producto p = new Producto();
        p.setId(id);
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setDescripcion(descripcion);
        p.setFechaIngreso(fecha);

        Categoria c = new Categoria();
        c.setId(idCategoria);
        p.setCategoria(c);

        Provedor provedor = new Provedor();
        provedor.setId(idProvedor);
        p.setProvedor(provedor);

        if (errors.isEmpty()) {
            pServices.save(p);
            getServletContext().getRequestDispatcher("/admin/stock").forward(req, resp);
        } else {
            req.setAttribute("errors", errors);
            req.setAttribute("categories",  cServices.findAll());
            req.setAttribute("product", p);
            getServletContext().getRequestDispatcher("/WEB-INF/pages/formProductos.jsp").forward(req, resp);
        }
    }
}
