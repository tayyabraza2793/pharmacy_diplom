package servlets;

import models.UserEntity;
import services.Impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class CurrentUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext context = req.getServletContext();
        HashMap<String, String[]> dict = new HashMap<>(req.getParameterMap());
        HttpSession session = req.getSession();
        UserEntity currentUser = (UserEntity) session.getAttribute("currentUser");

        try {
            if (dict.get("action")[0].equals("editPage")) {
                RequestDispatcher dispatcher = context.getRequestDispatcher("/currentUserPage");
                dispatcher.forward(req, resp);
            }

            if (dict.get("action")[0].equals("delete")) {
                if (new UserServiceImpl().deleteUserById(currentUser.getId()))
                    resp.sendRedirect("/logoutServlet");
                else {
                    RequestDispatcher dispatcher = context.getRequestDispatcher("/adminPage");
                    dispatcher.forward(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
