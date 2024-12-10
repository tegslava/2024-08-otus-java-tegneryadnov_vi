package ru.otus.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "CalcDivServlet", urlPatterns = "/div")
public class CalcDivServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CalcDivServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, NumberFormatException {
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("text/html");
            int a = Integer.parseInt(req.getParameter("a"));
            int b = Integer.parseInt(req.getParameter("b"));
            String result = b != 0 ? String.valueOf(a / b) : "ERROR division by 0";
            out.printf("<html><body><h1>%d / %d = %s</h1></body></html>", a, b, result);
            logger.debug("servlet executed");
        }
    }
}
