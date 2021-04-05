package servlets;

import services.Impl.MedicineServiceImpl;
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

public class MedicineServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ServletContext context = req.getServletContext();
            HashMap<String, String[]> dict = new HashMap<>(req.getParameterMap());

            if (dict.get("action")[0].equals("delete")) {
                new MedicineServiceImpl().deleteMedicineById(Integer.parseInt(dict.get("id")[0]));
                resp.sendRedirect("/adminPage");
            }

            if (dict.get("action")[0].equals("update")) {
                ArrayList<String> exceptions = new FormValidationService().validate(dict);
                if (!exceptions.isEmpty()) {
                    req.setAttribute("exceptions", exceptions);
                    RequestDispatcher requestDispatcher = context.getRequestDispatcher("/medicineAddPage");
                    requestDispatcher.forward(req, resp);
                    return;
                }

                if (new MedicineServiceImpl().updateMedicine(dict)) req.setAttribute("medicineUpdated", "true");
                else req.setAttribute("medicineUpdated", "false");

                RequestDispatcher requestDispatcher = context.getRequestDispatcher("/medicineEditPage");
                requestDispatcher.forward(req, resp);
                return;
            }

            if (dict.get("action")[0].equals("order")) {
                req.setAttribute("medicine_id", dict.get("id")[0]);
                RequestDispatcher requestDispatcher = context.getRequestDispatcher("/orderAddPage");
                requestDispatcher.forward(req, resp);
                return;
            }

            if (dict.get("action")[0].equals("edit")) {
                ArrayList<String> exceptions = new FormValidationService().validate(dict);
                if (!exceptions.isEmpty()) {
                    req.setAttribute("exceptions", exceptions);
                    RequestDispatcher requestDispatcher = context.getRequestDispatcher("/medicineAddPage");
                    requestDispatcher.forward(req, resp);
                    return;
                }

                req.setAttribute("medicine", new MedicineServiceImpl().getMedicineById(Integer.parseInt(req.getParameter("id"))));
                RequestDispatcher requestDispatcher = context.getRequestDispatcher("/medicineEditPage");
                requestDispatcher.forward(req, resp);
                return;
            }

            if (dict.get("action")[0].equals("add")) {
                ArrayList<String> exceptions = new FormValidationService().validate(dict);
                if (!exceptions.isEmpty()) {
                    req.setAttribute("exceptions", exceptions);
                    RequestDispatcher requestDispatcher = context.getRequestDispatcher("/medicineAddPage");
                    requestDispatcher.forward(req, resp);
                    return;
                }

                if (new MedicineServiceImpl().createMedicine(dict)) req.setAttribute("medicineCreated", "true");
                else req.setAttribute("medicineCreated", "false");

                RequestDispatcher requestDispatcher = context.getRequestDispatcher("/medicineAddPage");
                requestDispatcher.forward(req, resp);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
