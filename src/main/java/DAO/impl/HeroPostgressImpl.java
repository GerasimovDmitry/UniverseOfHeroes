package DAO.impl;

import DAO.IHeroPostgress;
import model.Hero;
import util.PostgressDBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HeroPostgressImpl implements IHeroPostgress {
    public List<Hero> getAll() throws SQLException {
        List<Hero> list = new ArrayList<Hero>();
        Connection connection = PostgressDBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM hero");
        while (resultSet.next()) {
            list.add(getHeroFromResultSet(resultSet));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    private Hero getHeroFromResultSet(ResultSet resultSet) throws SQLException {
        Hero hero = new Hero();
        hero.setId(resultSet.getInt(1));
        hero.setName(resultSet.getString(2));
        hero.setUniverse(resultSet.getString(3));
        hero.setPower(resultSet.getInt(4));
        hero.setDescription(resultSet.getString(5));
        hero.setAlive(resultSet.getBoolean(6));
        return hero;
    }
}
