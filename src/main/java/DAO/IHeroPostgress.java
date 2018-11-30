package DAO;

import model.Hero;

import java.sql.SQLException;
import java.util.List;

public interface IHeroPostgress {
    public List<Hero> getAll() throws SQLException;
}
