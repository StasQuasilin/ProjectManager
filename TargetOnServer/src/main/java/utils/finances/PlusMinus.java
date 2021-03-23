package utils.finances;

import entity.finance.accounts.AccountPoint;

public class PlusMinus {
    public float plus = 0;
    public float minus = 0;

    public boolean any() {
        return plus != 0 || minus != 0;
    }

    public void add(AccountPoint point) {
        plus += point.getPlus();
        minus += point.getMinus();
    }
}
