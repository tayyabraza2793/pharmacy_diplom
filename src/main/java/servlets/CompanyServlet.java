package servlets;

import services.Impl.CompanyServiceImpl;
import services.Impl.OrderServiceImpl;
import services.Impl.UserServiceImpl;
import utils.FormValidationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class CompanyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ServletContext context = req.getServletContext();
            HashMap<String, String[]> dict = new HashMap<>(req.getParameterMap());

            if (dict.get("action")[0].equals("delete")) {
                new CompanyServiceImpl().deleteCompanyById(Integer.parseInt(dict.get("id")[0]));
                resp.sendRedirect("/adminPage");
            }

            if (dict.get("action")[0].equals("add")) {
                ArrayList<String> exceptions = new FormValidationService().validate(dict);
                if (!exceptions.isEmpty()) {
                    req.setAttribute("exceptions", exceptions);
                    RequestDispatcher requestDispatcher = context.getRequestDispatcher("/companyAddPage");
                    requestDispatcher.forward(req, resp);
                    return;
                }

                if (new CompanyServiceImpl().createCompany(dict)) req.setAttribute("companyCreated", "true");
                else req.setAttribute("companyCreated", "false");

                RequestDispatcher requestDispatcher = context.getRequestDispatcher("/companyAddPage");
                requestDispatcher.forward(req, resp);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
