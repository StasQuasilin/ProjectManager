package ua.svasilina.targeton.utils.settings;

import android.content.Context;

import ua.svasilina.targeton.ui.main.Pages;
import ua.svasilina.targeton.utils.storage.StorageUtil;

public class LastPageUtil {

    private final StorageUtil storageUtil = new StorageUtil();
    public static final String FILE_NAME = "las_page";
    private static final Pages DEFAULT_PAGE = Pages.tree;

    public void savePage(Context context, Pages pages){
        storageUtil.save(FILE_NAME, pages.name(), context);
    }
    public Pages loadPage(Context context){
        final String read = storageUtil.read(FILE_NAME, context);
        if (read != null && !read.isEmpty()){
            return Pages.valueOf(read);
        } else {
            return DEFAULT_PAGE;
        }
    }
}
