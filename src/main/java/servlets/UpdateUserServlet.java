package servlets;

import models.UserEntity;
import services.Impl.UserServiceImpl;
import services.UserService;
import utils.FormValidationService;
import utils.HashingService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ServletContext context = req.getServletContext();
            HashMap<String, String[]> dict = new HashMap<>(req.getParameterMap());
            HttpSession session = req.getSession();
            UserEntity currentUser = (UserEntity) session.getAttribute("currentUser");
            String[] a = new String[1];
            a[0] = String.valueOf(currentUser.getId());
            dict.put("id", a);
            HashingService hashingService = new HashingService();
            UserService service = new UserServiceImpl();
            ArrayList<String> exceptions = service.registerValidation(dict);

            if (!hashingService.hashPasswordWithoutSalt(req.getParameter("old password"), currentUser.getSalt()).equals(currentUser.getPassword())) {
                exceptions.add("old password is incorrect");
            }

            if (!exceptions.isEmpty()) {
                req.setAttribute("exceptions", exceptions);
                RequestDispatcher requestDispatcher = context.getRequestDispatcher("/currentUserPage");
                requestDispatcher.forward(req, resp);
                return;
            }

            if (service.updateUser(dict)) {
                req.setAttribute("status", "updated");
            } else {
                req.setAttribute("status", "not updated");
            }

            RequestDispatcher dispatcher = context.getRequestDispatcher("/currentUserPage");
            dispatcher.forward(req, resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
