package filters;

import org.apache.log4j.Logger;
import services.hibernate.HibernateSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Quasilin on 09.09.2018.
 */
@WebFilter(value = "*")
public class ContextFilter implements Filter {

    private static final Logger log = Logger.getLogger(ContextFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Init new context filter");
        HibernateSessionFactory.init();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        req.setAttribute("context", req.getContextPath());
        req.setAttribute("language", req.getSession().getAttribute("lang"));
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("previous", req.getRequestURI());
        req.setAttribute("now", Date.valueOf(LocalDate.now()));
        req.setAttribute("month", LocalDate.now().getMonthValue());
        req.setAttribute("year", LocalDate.now().getYear());
        filterChain.doFilter(req, servletResponse);
    }

    public void destroy() {
        HibernateSessionFactory.shutdown();
    }
}
