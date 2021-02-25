package constants;

public interface ApiLinks {
    String API = "/api/v1";
    String SUBSCRIBE = "/socket";
    String GET_GOALS = API + "/get/goals";
    String GOAL_SAVE = API + "/goals/save";
    String TRANSACTION_SAVE = API + "/transaction/save";
    String BUY_LIST_EDIT = API + "/buy/list/save";
    String ACCOUNT_EDIT = API + "/account/edit";
    String FIND_CATEGORY = API + "/find/transaction/category";
    String FIND_COUNTERPARTY = API + "/find/counterparty";
    String GET_TASK = API + "/get/task";
    String TASK_EDIT = API + "/task/edit";
    String TREE_BUILDER = API + "/tree/builder";
    String GET_CALENDAR = API + "/get/calendar";
    String CALENDAR_EDIT = API + "/calendar/save";
    String TRANSACTION_REMOVE = API + "/transaction/remove";
    String CALENDAR_REMOVE = API + "/calendar/remove";
    String GOAL_REMOVE = API + "/goal/remove";
    String FIND_BUY_LIST = API + "/find/buy/list";
    String LOGOUT = API + "/logout";
    String LOGIN = "/sign/in";
    String DEMO = "/user/demo";
    String REGISTRATION = "/sign/up";
    String TIMER_START = API + "/task/timer/start";
    String FIND_TASK = API + "/find/task";
    String TIMER_STOP = API + "task/timer/stop";
    String USER_DATA = API + "/user/data";
    String ACCOUNT_REMOVE = API + "/account/remove";
    String GET_CATEGORIES = API + "/get/category";
    String FIND_USER = API + "/find/user";
    String FRIENDSHIP_REQ = API + "/friendship/req";
    String SAVE_GOAL_MEMBERS = API + "/goal/members/save";
    String SAVE_ACCOUNT_MEMBERS = API + "/account/members/save";
    String TASK_DELETE = API + "/task/delete";
}
