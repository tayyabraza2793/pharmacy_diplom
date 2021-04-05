package servlets;

import utils.FormValidationService;
import services.Impl.UserServiceImpl;
import services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class RegistrationServlet extends HttpServlet {
    UserService service;
    FormValidationService validationService;

    @Override
    public void init() {
        service = new UserServiceImpl();
        validationService = new FormValidationService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext context = req.getServletContext();
        HashMap<String, String[]> dict = new HashMap<>(req.getParameterMap());
        ArrayList<String> exceptions = service.registerValidation(dict);

        try {
            if (!exceptions.isEmpty()) {
                req.setAttribute("exceptions", exceptions);
                RequestDispatcher requestDispatcher = context.getRequestDispatcher("/registrationPage");
                requestDispatcher.forward(req, resp);
                return;
            }

            boolean userCreated = service.createUser(dict);
            req.setAttribute("userCreated", userCreated);
            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/registrationPage");
            requestDispatcher.forward(req, resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
