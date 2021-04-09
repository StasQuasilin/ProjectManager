package ua.svasilina.targeton.utils.constants;

public interface API {
    String WS = "ws";
    String HTTP = "http";
//    String ADDRESS = "134.249.155.33";
    String ADDRESS = "10.10.10.45";
//    int PORT = 32332;
    int PORT = 8080;
    String CONTEXT = "/targeton";
    String HOME = HTTP + "://" + ADDRESS + ":" + PORT + CONTEXT;
    String API = HOME + "/api/v1";
    String SIGN_IN = HOME + "/sign/in";
    String SUBSCRIBER = WS + "://" + ADDRESS + ":" + PORT + CONTEXT + "/socket";
    String FIND_CATEGORY = API + "/find/transaction/category";
    String USER_DATA = API + "/user/data";
    String SAVE_TRANSACTION = API + "/transaction/save";
    String GET_TREE_ITEMS = API + "/get/task";
}
