package servlets;

import models.UserEntity;
import services.Impl.UserServiceImpl;
import services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        service = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UserEntity user = service.login(req.getParameter("login"), req.getParameter("password"));
            if (user != null) {
                HttpSession session = req.getSession();
                String path = service.getPath(user.getRole());
                session.setAttribute("currentUser", user);
                resp.sendRedirect(path);
                return;
            }

            ServletContext context = req.getServletContext();
            String exception = "User email/password incorrect";
            req.setAttribute("exception", exception);
            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/loginPage");
            requestDispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
