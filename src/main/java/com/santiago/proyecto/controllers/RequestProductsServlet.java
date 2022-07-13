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
import java.util.List;

@WebServlet("/request-form")
public class RequestProductsServlet extends HttpServlet {

    @Inject
    private ProductosServices pService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Producto> productList = pService.find();
        req.setAttribute("stock", productList);
        getServletContext().getRequestDispatcher("/WEB-INF/pages/requestProductos.jsp").forward(req, resp);

    }
}
