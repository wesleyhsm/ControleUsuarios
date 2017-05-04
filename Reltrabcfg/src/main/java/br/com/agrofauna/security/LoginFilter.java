package br.com.agrofauna.security;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/admin/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        
        if (session.getAttribute("funcionario") != null || req.getRequestURI().endsWith("index.xhtml")) {            
            chain.doFilter(request, response);           
        } else {            
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("/Reltrabcfg-1.0.0-SNAPSHOT/index.xhtml"); // quando estiver em teste
            //res.sendRedirect("/Reltrabcfg/index.xhtml"); // quando estiver produção
        }        
    }

    @Override
    public void destroy() {
    }
}