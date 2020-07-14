package constants;

public interface ApiLinks {
    String API = "/controllers/api/v1";
    String SUBSCRIBE = API + "/subscribes";
    String GET_GOALS = API + "/get/goals";
    String GOAL_SAVE = API + "/goals/save";
    String TRANSACTION_SAVE = API + "/transaction/save";
    String BUY_LIST_SAVE = API + "/buy/list/save";
    String ACCOUNT_EDIT = API + "/account/edit";
    String FIND_TRANSACTION_CATEGORY = API + "/find/transaction/category";
    String FIND_COUNTERPARTY = API + "/find/counterparty";
    String GET_TASK = API + "/get/task";
    String TASK_EDIT = API + "/task/edit";
}
