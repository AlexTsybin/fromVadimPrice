package ru.uss.costprice.servlets;

import ru.uss.costprice.model.ShapeCut;

import javax.swing.text.html.HTML;
import java.io.IOException;

/**
 * Created by Komyshenets on 21.05.2016.
 */
@javax.servlet.annotation.WebServlet(name = "ServletTest", urlPatterns = "/uss")
public class ServletTest extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        ShapeCut cut = ShapeCut.getShapeCut("bug");
        String s = ShapeCut.getStr();
        response.getWriter().write(s.toString());
    }


}
