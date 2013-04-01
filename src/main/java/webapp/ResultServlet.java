package webapp;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="/resultServlet", urlPatterns="/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        httpServletRequest.setAttribute("select", httpServletRequest.getParameter("select"));

        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
