package controllers.api.finance.accounts;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.accounts.Account;
import entity.finance.accounts.AccountMember;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.dao.user.UserDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(ApiLinks.SAVE_ACCOUNT_MEMBERS)
public class SaveAccountMembersApi extends API {

    private final AccountDAO accountDAO = daoService.getAccountDAO();
    private final UserDAO userDAO = daoService.getUserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            final Account account = accountDAO.getAccount(body.get(Keys.ITEM));
            final HashMap<Integer, AccountMember> memberHashMap = new HashMap<>();
            for (AccountMember member : accountDAO.getMembers(account)){
                memberHashMap.put(member.getId(), member);
            }
            for (Object o : (JSONArray)body.get(Keys.MEMBERS)){
                final int memberId = Integer.parseInt(String.valueOf(o));
                AccountMember member = memberHashMap.remove(memberId);
                if(member == null){
                    member = new AccountMember();
                    member.setAccount(account);
                    member.setMember(userDAO.getUserById(memberId));
                    accountDAO.saveMember(member);
                }
            }
            for (AccountMember member : memberHashMap.values()){
                accountDAO.removeMember(member);
            }
            answer = new SuccessAnswer();
        } else {
            answer = new ErrorAnswer("Empty request body");
        }
        write(resp, answer);
    }
}
