package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_REG = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final String NAME_REG = "[A-Za-zА-Яа-яІі]{4,}";
    public boolean emailValid(String email){
        Pattern pattern = Pattern.compile(EMAIL_REG);
        final Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    static Validator validator = new Validator();

    public static void main(String[] args) {
        checkName("");
        checkName("<script>alert('O kurwa !');</script>");
        checkName("ійа");
        checkName("іволіваіsdfsdfs");
        checkName("0SDFSDFdfsgdfgdfgd@");
        checkName("DFSDFdfsgdfgdfgd0@sdfsdf.com.ua");
    }

    private static void checkEmail(String s) {
        System.out.println("'" + s + "' is valid=" + validator.emailValid(s));
    }

    private static void checkName(String s) {
        System.out.println("Name '" + s + "' is valid=" + validator.nameValidator(s));
    }

    public boolean nameValidator(String name) {
        Pattern pattern = Pattern.compile(NAME_REG, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(name);
        if (matcher.find()){
            return matcher.group().length() == name.length();
        }
        return false;
    }

    public boolean passwordValid(String password) {
        return password.length() > 4;
    }
}
