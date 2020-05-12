package constants;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
public class API {
    public static final String LOGIN = "/a/login";
    public static final String REGISTRATION = "/a/registration";
    public static final String RESTORE = "/a/restore";
    public static final String API = "/api/v1";
    public static final String TASK_EDIT = API + "/task/edit";
    public static final String TRANSACTION_EDIT = API + "/transaction/edit";
    public static final String FIND_TRANSACTION_CATEGORY = API + "/find/transaction/category";
    public static final String FIND_PERSON = API + "/find/person";
    public static final String BUDGET_EDIT = API + "/budget/edit";
    public static final String GET_SUB_TASK = API + "/get/sub/task";
    public static final String CHANGE_TASK_STATUS = API + "/change/status";
    public static final String PROJECT_DELETE = API + "/project/delete";
    public static final String TASK_TIME_EDIT = API + "/task/time/edit";
    public static final String GET_CALENDAR_ITEMS = API + "/calendar/items";
    public static final String TASK_REMOVE = API + "/task/remove";
    public static final String CURRENCY_EDIT = API + "/currency/add";
    public static final String CURRENCY_REMOVE = API + "/currency/remove";
    public static final Object TRANSACTION_SETTINGS_EDIT = API + "/transaction/settings/edit";
    public static final String TIMER_START = API + "/timer/start";
    public static final String TIMER_STOP = API + "/timer/stop";
    public static final String FIND_COUNTERPARTY = API + "/find/counterparty";
    public static final String BUY_LIST_EDIT = API + "/buy/list/edit";


    public class INFO {
        public static final String PROJECT_LENGTH = "/api/info/project/length";
        public static final String ADD_MONTH = "/api/info/plus/month";
    }

    public class PROJECT{
        public static final String LIST = "/api/project/list";
        public static final String SAVE = "/api/project/save";
    }

    public class Tree {
        public static final String LIST = "/api/tree/list";
        public static final String NEW_TASK = "/api/tree/new/task";
        public static final String EDIT_TASK = "/api/tree/edit/task";

    }

    public class Budget {
        public static final String EDIT = "/api/budget/edit";
        public static final String UPDATE = "/api/budget/list";
    }

    public class Payments {
        public static final String EDIT = "/api/payments/edit";
        public static final String UPDATE = "/api/payments/list";
    }

    public class Task {
        public static final String REMOVE = "/api/task/delete";
        public static final String STATUS = "/api/task/status";
    }
}
