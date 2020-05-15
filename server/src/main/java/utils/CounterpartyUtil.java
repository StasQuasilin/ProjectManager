package utils;

import entity.accounts.Counterparty;
import entity.user.User;
import org.json.simple.JSONObject;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import static constants.Keys.ID;
import static constants.Keys.NAME;

public class CounterpartyUtil {
    dbDAO dao = dbDAOService.getDao();
    public Counterparty getCounterparty(JSONObject json, User user){
        Counterparty counterparty = dao.getObjectById(Counterparty.class, json.get(ID));
        String counterpartyName = String.valueOf(json.get(NAME));
        if (counterparty == null){
            counterparty = dao.getCounterpartyByName(counterpartyName, user);
            if (counterparty == null) {
                counterparty = new Counterparty();
                counterparty.setName(counterpartyName);
                counterparty.setOwner(user);
                dao.save(counterparty);
            }
        }

        return counterparty;
    }
}
