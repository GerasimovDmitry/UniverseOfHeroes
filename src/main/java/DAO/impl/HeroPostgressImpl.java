package DAO.impl;

import DAO.IHeroPostgress;
import model.Hero;
import util.PostgressDBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeroPostgressImpl implements IHeroPostgress {
    public boolean save(Hero hero) throws SQLException {
        Connection connection = PostgressDBUtil.getConnection();
        PreparedStatement statement = null;
        statement = connection.prepareStatement("UPDATE hero SET " +
                "name=?, universe=?, power=?, description=?, alive=? WHERE id=?");
        statement.setInt(6, hero.getId());
        if (hero.isNew()) {
            statement = connection.prepareStatement("INSERT INTO hero (name, universe, power, description, alive) VALUES (?, ?, ?, ?, ?)");
        }
        statement.setString(1, hero.getName());
        statement.setString(2, hero.getUniverse());
        statement.setInt(3, hero.getPower());
        statement.setString(4, hero.getDescription());
        statement.setBoolean(5, hero.isAlive());
        boolean execute = statement.execute();
        statement.close();
        connection.close();
        return execute;
    }

    public boolean delete(int id) throws SQLException {
        Connection connection = PostgressDBUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM hero WHERE id=?");
        statement.setInt(1, id);
        boolean execute = statement.execute();
        statement.close();
        connection.close();
        return execute;
    }

    public Hero get(int id) throws SQLException {
        Hero hero = null;
        Connection connection = PostgressDBUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM hero WHERE id=?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            hero = getHeroFromResultSet(resultSet);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return hero;
    }

    public List<Hero> getByName(String name) throws SQLException {
        List<Hero> list = new ArrayList<Hero>();
        Connection connection = PostgressDBUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM hero WHERE lower(name) LIKE ?");
        statement.setString(1, name.toLowerCase());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            list.add(getHeroFromResultSet(resultSet));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

    public List<Hero> getByNameSortNameOrPower(String name, String sort) throws SQLException {
        List<Hero> list = new ArrayList<Hero>();
        Connection connection = PostgressDBUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM hero WHERE lower(name) LIKE ? ORDER BY " + sort);
        statement.setString(1, name.toLowerCase());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            list.add(getHeroFromResultSet(resultSet));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return list;
    }

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
