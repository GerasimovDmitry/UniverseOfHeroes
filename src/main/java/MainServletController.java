import DAO.impl.HeroPostgressImpl;
import model.Hero;
import util.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MainServletController extends HttpServlet {
    private MainApplicationController service;

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
    @Override
    public void init() throws ServletException {
        super.init();
        service = new MainApplicationController(new HeroPostgressImpl());
    }

    @Override
    public void destroy() {
        super.destroy();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            switch (action == null ? "all" : action) {
                case "find" :
                    StringBuilder like = new StringBuilder(request.getParameter("nameHero"));
                    boolean matches = Boolean.parseBoolean(request.getParameter("matches"));
                    if (!matches) {
                        like.insert(0, "%");
                        like.append("%");
                    }
                    List<Hero> listName = service.getByName(like.toString());
                    String json = listName.toString();
                    response.getWriter().write(json);
                    break;
                case "sort" :
                    String sortParam = request.getParameter("sortParam");
                    StringBuilder nameHero = new StringBuilder(request.getParameter("nameHero"));
                    nameHero.insert(0, "%");
                    nameHero.append("%");
                    String sortJson = service.getByNameSortNameOrPower(nameHero.toString(), sortParam).toString();
                    response.getWriter().write(sortJson);
                    break;
                case "delete":
                    int id = getId(request);
                    service.delete(id);
                    break;
                case "update":
                    Hero hero = service.get(getId(request));
                    response.getWriter().write(hero.toString());
                    break;
                case "all":
                default:
                    String allJSON = service.getAll().toString();
                    response.getWriter().write(allJSON);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch ( Exception e) {
                id = 0;
            }
            String name = request.getParameter("name");
            String universe = request.getParameter("universe");
            int power = Integer.parseInt(request.getParameter("power"));
            String description = request.getParameter("description");
            boolean alive = Boolean.parseBoolean(request.getParameter("alive"));
            Hero hero = new Hero(id, name, universe, power, description, alive);
            service.validation(hero);
            service.save(hero);
        } catch (ValidationException e) {
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }
}
