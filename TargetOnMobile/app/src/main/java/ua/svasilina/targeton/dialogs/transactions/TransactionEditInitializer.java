package ua.svasilina.targeton.dialogs.transactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import org.json.JSONObject;

import java.util.Calendar;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.dialogs.ListBuilder;
import ua.svasilina.targeton.dialogs.SearchDialog;
import ua.svasilina.targeton.dialogs.common.DateDialog;
import ua.svasilina.targeton.entity.Account;
import ua.svasilina.targeton.entity.Category;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.TransactionType;
import ua.svasilina.targeton.entity.transactions.UserData;
import ua.svasilina.targeton.utils.NameUtil;
import ua.svasilina.targeton.utils.builders.DateTimeBuilder;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.listeners.ChangeListener;

import static ua.svasilina.targeton.utils.constants.Constants.DATE_PATTERN;

public class TransactionEditInitializer {

    private Button typeButton;
    private Button dateButton;
    private Button transactionCategory;
    private View categoryGroup;
    private View accountFromGroup;
    private View accountToGroup;
    private Spinner accountFrom;
    private Spinner accountTo;
    private EditText amount;
    private Spinner currencies;
    private View rateGroup;
    private EditText rate;

    private final Context context;
    private final LayoutInflater inflater;
    private final Transaction transaction;
    private final UserData userData;
    private final FragmentManager manager;

    public TransactionEditInitializer(Context context, Transaction transaction, UserData userData, FragmentManager manager) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.transaction = transaction;
        this.userData = userData;
        this.manager = manager;
    }

    public void initView(View view) {
        typeButton = view.findViewById(R.id.typeButton);
        dateButton = view.findViewById(R.id.dateButton);
        transactionCategory = view.findViewById(R.id.transactionCategory);
        categoryGroup = view.findViewById(R.id.categoryGroup);
        accountFromGroup = view.findViewById(R.id.accountFromGroup);
        accountFrom = view.findViewById(R.id.transactionAccountFrom);
        accountToGroup = view.findViewById(R.id.accountToGroup);
        accountTo = view.findViewById(R.id.transactionAccountTo);
        currencies = view.findViewById(R.id.currency);
        rateGroup = view.findViewById(R.id.rateGroup);
        amount = view.findViewById(R.id.transactionAmount);
        rate = view.findViewById(R.id.transactionRate);

        initTypeButton();
        initDateButton();
        initTransactionCategory();
        initAccountFrom();
        initAccountTo();
        initTransactionAmount();
        initCurrency();
        showRate();
    }

    private void initTransactionAmount() {
        amount.setText(String.valueOf(transaction.getAmount()));
        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.selectAll();
            }
        });
    }

    private void initCurrency() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, userData.getCurrencies());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencies.setAdapter(adapter);
        final String currency = transaction.getCurrency();
        if (currency != null){
            currencies.setSelection(adapter.getPosition(currency));
        } else {
            transaction.setCurrency(adapter.getItem(currencies.getSelectedItemPosition()));
        }
        currencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transaction.setCurrency(adapter.getItem(position));
                showRate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showRate() {
        final TransactionType type = transaction.getType();
        final Account accountFrom = transaction.getAccountFrom();
        final Account accountTo = transaction.getAccountTo();
        final String currency = transaction.getCurrency();
        boolean show;

        if (type == TransactionType.spending){
            System.out.println(currency + ":" + accountFrom.getCurrency());
            show = !currency.equals(accountFrom.getCurrency());
        } else if (type == TransactionType.income){
            System.out.println(currency + ":" + accountTo.getCurrency());
            show = !currency.equals(accountTo.getCurrency());
        } else {
            show = !accountFrom.getCurrency().equals(accountTo.getCurrency());
        }

        if (show){
            rateGroup.setVisibility(View.VISIBLE);
            rate.setText(String.valueOf(transaction.getRate()));
        } else {
            rateGroup.setVisibility(View.GONE);
        }

    }

    private void initAccountTo() {
        final ArrayAdapter<Account> accountAdapter = createAccountAdapter();
        accountTo.setAdapter(accountAdapter);
        final Account to = transaction.getAccountTo();
        if (to != null){
            accountTo.setSelection(accountAdapter.getPosition(to));
        } else {
            transaction.setAccountTo(accountAdapter.getItem(accountTo.getSelectedItemPosition()));
        }
        accountTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Account account = accountAdapter.getItem(position);
                transaction.setAccountTo(account);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                transaction.setAccountTo(null);
            }
        });
    }

    private ArrayAdapter<Account> createAccountAdapter() {
        final ArrayAdapter<Account> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, userData.getAccounts()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void initAccountFrom() {
        final ArrayAdapter<Account> accountAdapter = createAccountAdapter();
        accountFrom.setAdapter(accountAdapter);
        final Account from = transaction.getAccountFrom();
        if (from != null){
            accountFrom.setSelection(accountAdapter.getPosition(from));
        } else {
            transaction.setAccountFrom(accountAdapter.getItem(accountFrom.getSelectedItemPosition()));
        }
        accountFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Account account = accountAdapter.getItem(position);
                transaction.setAccountFrom(account);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                transaction.setAccountFrom(null);
            }
        });
    }

    private void initTypeButton() {
        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TransactionTypeDialog(context, inflater, transaction, new ChangeListener() {
                    @Override
                    public void onChange() {
                        updateTypeButton();
                    }
                }).show(manager, "TransactionTypeDialog");
            }
        });
        updateTypeButton();
    }

    private void updateTypeButton() {
        if (transaction != null) {
            typeButton.setText(NameUtil.getNameByString(context, transaction.getType().toString()));
            updateDialogView();
        }
    }

    private void updateDialogView() {
        final TransactionType type = transaction.getType();
        if (type == TransactionType.spending){
            categoryGroup.setVisibility(View.VISIBLE);
            accountFromGroup.setVisibility(View.VISIBLE);
            accountToGroup.setVisibility(View.GONE);
        } else if (type == TransactionType.income){
            categoryGroup.setVisibility(View.VISIBLE);
            accountFromGroup.setVisibility(View.GONE);
            accountToGroup.setVisibility(View.VISIBLE);
        } else if (type == TransactionType.transfer){
            categoryGroup.setVisibility(View.GONE);
            accountFromGroup.setVisibility(View.VISIBLE);
            accountToGroup.setVisibility(View.VISIBLE);
        }
    }

    DateTimeBuilder dateTimeBuilder = new DateTimeBuilder(DATE_PATTERN);

    private void initDateButton() {
        final Calendar d = transaction.getDate();
        final Calendar date = d == null ? Calendar.getInstance() : d;

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateDialog(date, inflater, DateDialog.DialogType.calendar, new ChangeListener() {
                    @Override
                    public void onChange() {
                        updateDateButton();
                    }
                }).show(manager, "DateDialog");
            }
        });
        if (d == null){
            transaction.setDate(date);
        }
        updateDateButton();
    }

    private void initTransactionCategory() {
        transactionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SearchDialog<Category> dialog = new SearchDialog<>(
                        context,
                        inflater,
                        API.FIND_CATEGORY,
                        new ItemBuilder<Category>() {
                            @Override
                            public Category create(JSONObject json) {
                                return new Category(json);
                            }
                        },
                        new OnChangeListener<Category>() {
                            @Override
                            public void onChange(Category item) {
//                                transaction.setCategory(item);
                                updateTransactionCategory();
                            }
                        },
                        new ListBuilder<Category>() {
                            @Override
                            public void build(Category item, View view) {
                                final TextView itemValue = view.findViewById(R.id.itemValue);
                                itemValue.setText(item.getTitle());
                            }
                        }, context.getResources().getString(R.string.find_category_title));
                dialog.show(manager, "Find Category");

            }
        });
        updateTransactionCategory();
    }

    private void updateDateButton() {
        dateButton.setText(dateTimeBuilder.build(transaction.getDate()));
    }

    private void updateTransactionCategory() {
//        final Category category = transaction.getCategory();
//        if (category == null){
//            transactionCategory.setText(getResources().getString(R.string.without_category));
//        } else {
//            final String title = category.getTitle();
//            if (title.isEmpty()){
//                transactionCategory.setText(getResources().getString(R.string.without_category));
//            } else {
//                transactionCategory.setText(title);
//            }
//        }
    }
}
