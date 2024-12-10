package ru.otus.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "CalcAddServlet", urlPatterns = "/add")
public class CalcAddServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CalcAddServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, NumberFormatException {
        try (PrintWriter out = resp.getWriter()) {
            // PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            int a = Integer.parseInt(req.getParameter("a"));
            int b = Integer.parseInt(req.getParameter("b"));
            out.printf("<html><body><h1>%d + %d = %d</h1></body></html>", a, b, a + b);
            logger.debug("servlet executed");
        }
    }
}
