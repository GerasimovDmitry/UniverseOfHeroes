import DAO.IHeroPostgress;
import DAO.impl.HeroPostgressImpl;
import model.Hero;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainApplicationController {
    public static String getAllHero() {
        Hero hero = new Hero();
        List<Hero> allHeroes = new ArrayList<>();
        IHeroPostgress heroPostgress = new HeroPostgressImpl();
        try {
            allHeroes = heroPostgress.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String hero1 = allHeroes.toString();
        String json = "{hero:" + hero1 +" }";
        return json;
    }
}
