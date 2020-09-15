package ua.svasilina.targeton.utils.subscribes.updaters;

import org.json.JSONObject;

import ua.svasilina.targeton.ui.main.tree.TreeFragment;

public class TreeUpdater implements DataUpdater {

    private final TreeFragment treeFragment;

    public TreeUpdater(TreeFragment treeFragment) {
        super();
        this.treeFragment = treeFragment;
    }

    @Override
    public void update(JSONObject object) {
        treeFragment.update(object);
    }

    @Override
    public void sort() {

    }
}
