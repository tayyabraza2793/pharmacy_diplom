package servlets;

import repositories.Impl.UserRepositoryImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try {
            session.setAttribute("users", new UserRepositoryImpl().get());
            resp.sendRedirect("/adminPage");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
