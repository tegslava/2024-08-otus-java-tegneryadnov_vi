package ru.otus.web;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalsServletsTest {
    @Test
    @DisplayName("CalcAddServlet Ok")
    void CalcAddServletTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("a")).thenReturn("6");
        when(request.getParameter("b")).thenReturn("3");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new CalcAddServlet().doGet(request, response);
        writer.flush();
        assertTrue(stringWriter.toString().contains("9"));
    }

    @Test
    @DisplayName("CalcSubtractServlet Ok")
    void CalcSubServletTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("a")).thenReturn("10");
        when(request.getParameter("b")).thenReturn("8");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new CalcSubtractServlet().doGet(request, response);
        writer.flush();
        assertTrue(stringWriter.toString().contains("2"));
    }

    @Test
    @DisplayName("CalcMultiplyServlet Ok")
    void CalcMultiplyServletTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("a")).thenReturn("250");
        when(request.getParameter("b")).thenReturn("4");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new CalcMultiplyServlet().doGet(request, response);
        writer.flush();
        assertTrue(stringWriter.toString().contains("1000"));
    }

    @Test
    @DisplayName("CalcDivServlet Ok")
    void CalcDivServletTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("a")).thenReturn("120");
        when(request.getParameter("b")).thenReturn("30");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new CalcDivServlet().doGet(request, response);
        writer.flush();
        assertTrue(stringWriter.toString().contains("4"));
    }

    @Test
    @DisplayName("CalcDivServlet generate error Ok")
    void CalcDivServletErrorTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("a")).thenReturn("190");
        when(request.getParameter("b")).thenReturn("0");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new CalcDivServlet().doGet(request, response);
        writer.flush();
        assertTrue(stringWriter.toString().contains("ERROR division by 0"));
    }
}
