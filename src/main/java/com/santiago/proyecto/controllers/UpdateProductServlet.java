package com.santiago.proyecto.controllers;

import com.santiago.proyecto.models.Producto;
import com.santiago.proyecto.services.ProductosServices;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/provedor/stock/update")
public class UpdateProductServlet extends HttpServlet {

    @Inject
    private ProductosServices pServices;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer stock = Integer.valueOf(req.getParameter("stock"));
        Long precio = Long.valueOf(req.getParameter("precio"));
        Long id = Long.valueOf(req.getParameter("id"));
        Producto product = pServices.findById(id);
        product.setStock(stock);
        product.setPrecio(precio);
        System.out.println(product.toString());
        pServices.save(product);

        getServletContext().getRequestDispatcher("/provedor/stock").forward(req, resp);
    }
}
