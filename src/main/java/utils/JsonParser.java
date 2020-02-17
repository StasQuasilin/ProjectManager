package utils;

import entity.budget.Budget;
import entity.project.Project;
import entity.project.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.answers.IAnswer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
public class JsonParser {


    public static String prettyJson(String json){
        int tabLevel = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : json.toCharArray()){
            if (c == '}' || c == ']'){
                tabLevel--;
                builder.append('\n');
                for (int i = 0; i < tabLevel; i++){
                    builder.append('\t');
                }
            }

            builder.append(c);
            if (c == '{' || c == '['){
                builder.append('\n');
                tabLevel++;
                for (int i = 0; i < tabLevel; i++){
                    builder.append('\t');
                }
            }

            if (c == ','){
                builder.append('\n');
                for (int i = 0; i < tabLevel; i++){
                    builder.append('\t');
                }
            }
        }
        return builder.toString();
    }


    final JSONParser parser = new JSONParser();

    public JSONObject parse(String string) throws ParseException {
        return (JSONObject) parser.parse(string);
    }
}
