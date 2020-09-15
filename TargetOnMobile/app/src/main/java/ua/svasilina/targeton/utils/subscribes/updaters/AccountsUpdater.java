package ua.svasilina.targeton.utils.subscribes.updaters;

import org.json.JSONObject;

import ua.svasilina.targeton.ui.main.accounts.AccountsFragment;

public class AccountsUpdater extends DataUpdater {

    private final AccountsFragment accountsFragment;

    public AccountsUpdater(AccountsFragment accountsFragment) {
        this.accountsFragment = accountsFragment;
    }

    @Override
    public void update(JSONObject object) {
        accountsFragment.update(object);
    }

    @Override
    public void sort() {

    }
}
