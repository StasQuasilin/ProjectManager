package utils;

import entity.transactions.Transaction;

public class CategoryCalculator extends Calculator {

    public void calculate(Transaction transaction) {
        calculatePointRoot(
                transaction.getId(),
                transaction.getDate(),
                transaction.getCategory().getId(),
                transaction.getTotalSum()
        );
    }
}
