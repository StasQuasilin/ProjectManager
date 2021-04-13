package utils.finances;

import constants.Keys;
import entity.finance.accounts.AccountPoint;
import entity.finance.accounts.PointScale;
import entity.finance.accounts.PointType;
import entity.finance.category.Header;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.db.dao.TitleDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.category.CategoryDAOImpl;
import utils.db.dao.daoService;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public class CategoryReportBuilder {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final TitleDAO titleDAO = daoService.getTitleDAO();

    public JSONArray buildReport(PointScale scale, Date date, User user) {
        final HashMap<String, Object> args = new HashMap<>();
        args.put(Keys.DATE, date);
        args.put(Keys.SCALE, scale);
        args.put(Keys.OWNER, user);
        args.put(Keys.TYPE, PointType.category);

        JSONArray array = new JSONArray();
        for (AccountPoint point : hibernator.query(AccountPoint.class, args)){
            final Header header = titleDAO.getHeader(point.getAccount());
            if (header != null) {
                final JSONObject jsonObject = header.shortJson();
                jsonObject.put(Keys.PLUS, point.getPlus());
                jsonObject.put(Keys.MINUS, point.getMinus());
                array.add(jsonObject);
            }
        }

        return array;
    }
}
