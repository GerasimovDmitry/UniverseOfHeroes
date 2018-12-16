import DAO.IHeroPostgress;
import model.Hero;
import util.ValidationException;
import java.sql.SQLException;
import java.util.List;

public class MainApplicationController {
    private IHeroPostgress repository;
    public MainApplicationController(IHeroPostgress repository) {
        this.repository = repository;
    }
    public boolean save(Hero hero) throws SQLException {
        return repository.save(hero);
    }

    public boolean delete(int id) throws SQLException {
        return repository.delete(id);
    }

    public Hero get(int id) throws SQLException {
        return repository.get(id);
    }

    public List<Hero> getByName(String name) throws SQLException {
        return repository.getByName(name);
    }

    public List<Hero> getByNameSortNameOrPower(String name, String sort) throws SQLException {
        return repository.getByNameSortNameOrPower(name, sort);
    }

    public List<Hero> getAll() throws SQLException {
        return repository.getAll();
    }

    public void validation(Hero hero) throws SQLException {
        String name = hero.getName();
        Integer id = hero.getId();
        if (name.isEmpty()) {
            throw new ValidationException("The name must not be empty");
        }
        if (name.length() > 30) {
            throw new ValidationException("The name must not be more than 30 characters");
        }
        if (id == null && !repository.getByName(name).isEmpty()) { //проверка при создании героя на дубликат
            throw new ValidationException("Hero with the same name already exists");
        }
        /*if(id != null && !name.equalsIgnoreCase(repository.get(id).getName())) { //проверка при изменении имени на другое
            throw new ValidationException("Hero with the same name already exists");*/

        if (hero.getPower() < 0 || hero.getPower() > 100) {
            throw new ValidationException("The power must not be less than 0 and greater than 100");
        }

    }
}
