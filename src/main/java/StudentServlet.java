import configuration.SpringConfiguration;
import model.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-student")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        Student student = context.getBean(Student.class);

        HttpSession session = request.getSession();
        List<Student> studentList = (List<Student>) session.getAttribute("students");
        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        if (request.getParameter("name").length() > 0 && request.getParameter("surname").length() > 0) {
            student = new Student(request.getParameter("name"),
                    request.getParameter("surname"),
                    request.getParameter("age"),
                    request.getParameter("email"),
                    request.getParameter("group"),
                    request.getParameter("faculty"));
        }
        studentList.add(student);
        session.setAttribute("students", studentList);
        response.sendRedirect(request.getContextPath() + "/add-student");
    }
}
