package com.santiago.proyecto.controllers;

import com.santiago.proyecto.models.Provedor;
import com.santiago.proyecto.services.ProvedorServices;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({"/login", "/login/provedor"})
public class LoginServlet extends HttpServlet {

    @Inject
    private ProvedorServices providerService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=UTF-8");

        session.setAttribute("username", username );
        session.setAttribute("password", password );

        String path = req.getServletPath();

        Boolean isProvider = path.endsWith("/provider");
        if(isProvider){
            Provedor p = providerService.findByNombre(username);
            if (p != null && p.getPassword().equals(password)){
                session.setAttribute("rol", "provider");
                session.setAttribute("id", p.getId());
                getServletContext().getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
            }else {
                resp.sendRedirect(getServletContext().getContextPath() + "/login-form?login=Provider");
            }
        }
    }
}
