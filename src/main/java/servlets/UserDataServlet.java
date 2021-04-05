package servlets;

import models.UserEntity;
import services.Impl.CompanyServiceImpl;
import services.Impl.MedicineServiceImpl;
import services.Impl.OrderServiceImpl;
import services.Impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDataServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext context = req.getServletContext();
        HttpSession session = req.getSession();
        UserEntity currentUser = (UserEntity) session.getAttribute("currentUser");
        String dataType = req.getParameter("data");

        try {
            switch (dataType) {
                case "order": {
                    req.setAttribute("returnedData", new OrderServiceImpl().getOrdersByUserId(currentUser.getId()));
                    ArrayList<String> fields = new ArrayList<String>
                            (Arrays.asList("Ид", "Ид пользователя", "Ид товара", "Количество", "Сумма", "Статус"));
                    req.setAttribute("dataFields", fields);
                    req.setAttribute("type", "order");
                    break;
                }
                case "medicine": {
                    req.setAttribute("returnedData", new MedicineServiceImpl().getMedicines());
                    ArrayList<String> fields = new ArrayList<String>
                            (Arrays.asList("Ид", "Ид компании", "Имя", "Тип", "Дата изготовления",
                                    "Срок годности", "Цена покупки", "Цена продажи", "Индекс", "Количество",
                                    "Количество таблеток", "Дозировка", "Описание"));
                    req.setAttribute("dataFields", fields);
                    req.setAttribute("type", "medicine");
                    break;
                }
                case "account": {
                    ArrayList<UserEntity> users = new ArrayList<>();
                    users.add(new UserServiceImpl().getUserByEmail(currentUser.getEmail()));
                    ArrayList<String> fields = new ArrayList<String>
                            (Arrays.asList("Ид", "Имя", "Фамилия", "Роль", "Телефон", "Эл.почта", "Пароль", "Соль"));
                    req.setAttribute("dataFields", fields);
                    req.setAttribute("returnedData", users);
                    req.setAttribute("type", "currentUser");
                    break;
                }
                default: {
                    break;
                }
            }

            RequestDispatcher requestDispatcher = context.getRequestDispatcher("/customerPage");
            requestDispatcher.forward(req, resp);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
