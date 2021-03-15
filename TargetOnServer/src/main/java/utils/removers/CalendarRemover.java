package utils.removers;

import entity.calendar.CalendarItem;
import utils.Updater;
import utils.db.hibernate.Hibernator;

public class CalendarRemover {

    private final Hibernator hibernator = Hibernator.getInstance();

    private final CategoryRemover categoryRemover = new CategoryRemover();
    private final Updater updater = new Updater();

    public void remove(CalendarItem item) {
        hibernator.remove(item);
//        categoryRemover.remove(item.getHeader());

//        updater.remove(Subscribe.calendar, item.getId(), item.getOwner());
    }
}
