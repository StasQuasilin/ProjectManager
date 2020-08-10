package constants;

public interface ApiLinks {
    String API = "/controllers/api/v1";
    String SUBSCRIBE = API + "/subscribes";
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
    String LOGIN = API + "/login";
    String REGISTRATION = API + "/registration";
    String TIMER_START = API + "/task/timer/start";

    String FIND_TASK = API + "/find/task";
    String TIMER_STOP = API + "task/timer/stop";
}
