package utils.finances;

import constants.Keys;
import entity.finance.Counterparty;
import entity.finance.category.Header;
import entity.user.User;
import utils.db.hibernate.Hibernator;
import utils.json.JsonObject;

import java.util.HashMap;
import java.util.List;

public class CounterpartyUtil {

    private final Hibernator hibernator = Hibernator.getInstance();

    public Counterparty getCounterparty(JsonObject jsonObject, User owner){
        final int id = jsonObject.getInt(Keys.ID);
        final String title = jsonObject.getString(Keys.TITLE);
        Counterparty counterparty = hibernator.get(Counterparty.class, Keys.ID, id);
        Header header;
        if(counterparty == null){
            header = new Header();
            header.setOwner(owner);
            counterparty = new Counterparty();
            counterparty.setHeader(header);
        } else {
            header = counterparty.getHeader();
        }
        if (header.getTitle() == null || header.getTitle().equals(title)){
            header.setValue(title);
            hibernator.save(header);
            hibernator.save(counterparty);
        }

        return counterparty;
    }

    public List<Counterparty> findCounterparty(String key, User owner) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(Keys.HEADER_OWNER, owner);
        return hibernator.find(Counterparty.class, args, Keys.HEADER_VALUE, key);
    }
}
