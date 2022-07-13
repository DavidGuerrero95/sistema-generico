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

@WebServlet("/admin/stock")
public class StockServlet extends HttpServlet {

    @Inject
    private ProductosServices pServices;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Producto> productList = pServices.find();
        req.setAttribute("stock", productList);
        getServletContext().getRequestDispatcher("/WEB-INF/pages/stockProducts.jsp").forward(req, resp);
    }
}
