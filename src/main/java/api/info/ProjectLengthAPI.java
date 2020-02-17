package api.info;

import com.sun.deploy.util.ArrayUtil;
import constants.API;
import org.apache.log4j.Logger;
import services.LanguageBase;
import services.answers.IAnswer;
import services.answers.SuccessAnswer;
import utils.DateParser;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@WebServlet(API.INFO.PROJECT_LENGTH)
public class ProjectLengthAPI extends HttpServlet {

    private static final LanguageBase languageBase = LanguageBase.getBase();
    private final Logger log =Logger.getLogger(ProjectLengthAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String language = req.getSession().getAttribute("language").toString();

        try {
            HashMap<String, String> body = PostUtil.parseBody(req);
            LocalDate begin = DateParser.parseFromEditor(body.get("begin")).toLocalDate();
            log.info("Begin date: " + begin);

            LocalDate complete = DateParser.parseFromEditor(body.get("complete")).toLocalDate();
            log.info("Complete date: " + complete);

            Period period = Period.between(begin, complete);
            int years = period.getYears();
            log.info("Years: " + years);

            int months = period.getMonths();
            log.info("Month: " + months);

            int days = period.getDays();
            log.info("Days: " + days);

            ArrayList<String> list = new ArrayList<>();
            if (years > 0) {
                list.add(years + " " + getYears(years, language, "year"));
            }
            if (months > 0) {
                list.add(months + " " + getYears(months, language, "month"));
            }
            if (days > 0) {
                list.add(days + " " + getYears(days, language, "day"));
            }

            IAnswer answer = new SuccessAnswer();
            answer.add("length", String.join(", ", list));

            body.clear();
            list.clear();
        } catch (Exception e){
             e.printStackTrace();
        }
    }

    static synchronized String getYears(int count, String language, String unit){
        if (count == 1 || count == 21 || count == 31){
            return languageBase.get(language, "1." + unit).toLowerCase();
        } else if (count >= 2 && count <= 4 || count >= 22 && count <= 24){
            return languageBase.get(language, "2." + unit + "s").toLowerCase();
        } else {
            return languageBase.get(language, "many." + unit + "s").toLowerCase();
        }
    }
}
