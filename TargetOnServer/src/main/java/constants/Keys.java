package constants;

import java.util.HashMap;

public interface Keys {
    String ENCODING = "UTF-8";
    String CONTEXT = "context";
    String LOCALE = "locale";
    String LOCALES = "locales";
    String CONTENT = "content";
    String ACTION = "action";
    String ID= "id";
    String DATE = "date";
    String TIME = "time";
    String NAME = "name";
    String UID = "uid";
    String SUBSCRIBER = "subscriber";
    String SUBSCRIBE = "subscribe";
    String TREE_SUBSCRIBE = "treeSubscribe";
    String GOAL_SUBSCRIBE = "goalSubscribe";
    String GOAL_EDIT = "goalEdit";
    String WELCOME = "welcome";
    String TASK_SUBSCRIBE = "taskSubscribe";
    String SLASH = "/";
    String ASTERISK = "*";
    String GOAL = "goal";
    String GOALS = "goals";
    String TREE = "tree";
    String CALENDAR = "calendar";
    String FINANCES = "finances";
    String PERSONAL = "personal";
    String YEAR = "year";
    String CATEGORY = "category";
    String HEADER = "header";

    String TASK = "task";
    String TITLE = "title";
    String TITLE_OWNER = TITLE + SLASH + "owner";
    String OWNER = "owner";
    String CATEGORY_OWNER = HEADER + SLASH + OWNER;
    String _OWNER = "_owner";
    String MEMBER = "member";

    String DEPENDENT = "dependent";
    String PRINCIPAL = "principal";
    String ITEM = "item";
    String ITEMS = "items";
    String ROOT = "root";
    String CHILDREN = "children";
    String DATE_BEGIN = "dateBegin";
    String DATE_END = "dateEnd";
    String PARENT = "parent";
    String HEADER_PARENT = HEADER + SLASH + PARENT;
    String SURNAME = "surname";
    String FORENAME = "forename";
    String STATUS = "status";
    String REASON = "reason";
    String STATUSES = "statuses";
    String DOER = "doer";
    String RESULT = "result";
    String BEGIN = "begin";
    String END = "end";
    String STATISTIC = "statistic";
    String BUDGET = "budget";
    String DATA = "data";
    String ADD = "add";
    String USER = "user";
    String AVATAR = "avatar";
    String EDIT = "edit";
    String TASK_EDIT = "taskEdit";
    String SAVE = "save";
    String TRANSACTION_EDIT = "transactionEdit";
    String TRANSACTION = "transaction";
    String TRANSACTION_DATE = TRANSACTION + SLASH + DATE;
    String FAST_TRANSACTION_EDIT = "fastTransactionEdit";
    String ACCOUNT_EDIT = "accountEdit";
    String ACCOUNT_EXTRACT = "accountExtract";
    String BUY_LIST_EDIT = "buyListEdit";
    String TYPE = "type";
    String TYPES = "types";
    String AMOUNT = "amount";
    String LIMIT = "limit";
    String ACCOUNT = "account";
    String ACCOUNTS = "accounts";
    String ACCOUNT_FROM = "accountFrom";
    String ACCOUNT_TO = "accountTo";
    String COUNTERPARTY = "counterparty";
    String CURRENCY = "currency";
    String RATE = "rate";
    String DETAILS = "details";
    String COMMENT = "comment";
    String FIND_CATEGORY = "findCategory";
    String FIND_DEPENDENCY = "findDependency";
    String FIND_COUNTERPARTY = "findCounterparty";
    String TRANSACTION_SUBSCRIBE = "transactionSubscribe";
    String ACCOUNT_SUBSCRIBE = "accountSubscribe";
    String BUY_LIST_SUBSCRIBE = "buyListSubscribe";
    String SUM = "sum";
    String COLON = ":";
    String SPACE = " ";
    String GET_TASK = "getTask";
    String TREE_BUILDER = "treeBuilder";
    String TASK_TIMER = "taskTimer";
    String RUNT_TIMER = "runTimer";
    String GET_CALENDAR = "getCalendar";
    String REPEAT = "repeat";
    String USE_DATE = "useDate";
    String USE_TIME = "useTime";
    String LENGTH = "length";
    String COUNT = "count";
    String PRICE = "price";
    String DONE = "done";
    String LIST = "list";
    String SCALE = "scale";
    String KEY = "key";
    String HIDDEN = "hidden";
    String PLUS = "plus";
    String MINUS = "minus";
    String SPEND = "spend";
    String COST = "cost";
    String ACTIVE = "active";
    String PROGRESSING = "progressing";
    String REMOVE = "remove";
    String FIND_BUY_LIST = "findBuyList";
    String BUY_LIST = "buyList";
    String ROOT_BUY_LIST = "rootBuyList";
    String SEPARATED = "separated";
    String LOGOUT = "logout";
    String LOGIN = "login";
    String REGISTRATION = "registration";
    String LANG = "lang";
    String LANGUAGE = "language";
    String EMAIL = "email";
    String PASSWORD = "password";
    String SUCCESS = "success";
    String ERROR = "error";
    String ERRORS = "errors";
    String MESSAGE = "message";
    String REDIRECT = "redirect";
    String TIMER_START = "timerStart";
    String UNITS = "units";
    String UNIT = "unit";
    String DEADLINE = "deadline";
    String TIME_LOG = "timeLog";
    String FIND_TASK = "findTask";
    String TIMER_STOP = "timerStop";
    String TIMER_SUBSCRIBE = "timerSubscribe";
    String USER_COUNT_SUBSCRIBE = "userCountSubscribe";
    String TASK_DELETE = "taskDelete";
    String TRANSACTION_LIMIT = "transactionLimit";
    String SHOW = "show";
    String DEPOSIT_SETTINGS = "depositSettings";
    String OPEN = "open";
    String PERIOD = "period";
    String CAPITALIZATION = "capitalization";
    String SETTINGS = "settings";
    String DONE_IF = "doneIf";
    String KANBAN = "kanban";
    String FRIENDS = "friends";
    String MESSAGES = "messages";
    String TOKEN = "token";
    String UNAUTHORISED = "unauthorised";
    String DEMO = "demo";
    String SHARP = "#";
    String ACCOUNT_REMOVE = "accountRemove";
    String CATEGORIES = "categories";
    String GET_CATEGORIES = "getCategories";
    String ADD_MEMBER = "addMember";
    String FIND_USER = "findUser";
    String NOTE = "note";
    String FRIENDS_SUBSCRIBE = "friendsSubscribe";
    String FRIENDSHIP_REQ_SUBSCRIBE = "friendshipReqSubscribe";
    String GOAL_MEMBERS = "goalMembers";
    String MEMBERS = "members";
    String MEMBER_LIST = "memberList";
    String NOW = "now";
    String PATH = "path";
    String EMPTY_BODY = "Empty req body";
    String EMPTY_TITLE = "Title is empty";
    String TITLE_ID = "titleId";
    String DELETE = "delete";
    String VALUE = "value";
    String HEADER_VALUE = HEADER + SLASH + VALUE;
    String HEADER_OWNER = HEADER + SLASH + OWNER;
    char COMMA = ',';

    String DESCRIPTION = "description";
    String FIND_CURRENCY = "findCurrency";
    String REMEMBER = "remember";
    String EXEMPTION = "exemption";
    String REPORT_OF_MONTH = "reportOfMonth";
    String REPORT_BUILD_API = "reportBuildApi";
    String DEPENDENCY = "dependency";
    String DEPENDENCY_LIST = "dependencyList";
    String DEPENDENCY_COUNT = "dependencyCount";
    String RUN_TIMER = "runTimer";
    String TASK_STATUS = "taskStatus";
    String REMOVE_BUY_LIST = "buyListRemove";
    String OTHER = "other";
    String DISCUSSION_EDIT = "discussionEdit";
    String TEXT = "text";
    String AUTHOR = "author";
    String DISCUSSIONS = "discussions";
    String PROGRESS = "progress";
    String TARGET = "target";
    String SAVE_TASK_PROGRESS = "saveTaskProgress";
    String COAST = "coast";
    String FRIEND = "friend";
    String DOERS = "doers";
    String WEEK_DAYS = "weekDays";
    String REPEATS = "repeats";
    String EMPTY = "";
    String BASE = "base";
    String PAIR = "pair";
    String RESPONSE_CODE = "responseCode";
    String EXCHANGE_RATE = "exchangeRate";
    String RANDOM_TASK = "randomTask";


    String LEND = "lend";
    String DOT = ".";
}
