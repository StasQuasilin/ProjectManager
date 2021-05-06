package ua.svasilina.targeton.dialogs.transactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import org.json.JSONObject;

import java.util.Calendar;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.adapters.SimpleListAdapter;
import ua.svasilina.targeton.dialogs.ListBuilder;
import ua.svasilina.targeton.dialogs.SearchDialog;
import ua.svasilina.targeton.dialogs.common.DateDialog;
import ua.svasilina.targeton.entity.Account;
import ua.svasilina.targeton.entity.Category;
import ua.svasilina.targeton.entity.transactions.Transaction;
import ua.svasilina.targeton.entity.transactions.TransactionDetail;
import ua.svasilina.targeton.entity.transactions.TransactionType;
import ua.svasilina.targeton.entity.transactions.UserData;
import ua.svasilina.targeton.utils.FloatDecorator;
import ua.svasilina.targeton.utils.NameUtil;
import ua.svasilina.targeton.utils.builders.DateTimeBuilder;
import ua.svasilina.targeton.utils.constants.API;
import ua.svasilina.targeton.utils.constants.Keys;
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
    private ListView detailList;

    private final Context context;
    private final LayoutInflater inflater;
    private final Transaction transaction;
    private final UserData userData;
    private final FragmentManager manager;
    final SearchDialog<Category> findCategoryDialog;
    SimpleListAdapter<TransactionDetail> detailListAdapter;

    private final FloatDecorator decorator;

    public TransactionEditInitializer(Context context, final Transaction transaction, UserData userData, FragmentManager manager) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.transaction = transaction;
        this.userData = userData;
        this.manager = manager;
        decorator = new FloatDecorator(context);

        detailListAdapter = new SimpleListAdapter<>(context, R.layout.transaction_detail_view, inflater, new ListBuilder<TransactionDetail>() {
            @Override
            public void build(TransactionDetail item, View view) {

                final TextView detailPathView = view.findViewById(R.id.detailPath);
                StringBuilder builder = new StringBuilder();
                for (String s : item.getPath()) {
                    builder.append(s).append(Keys.SLASH);
                }
                detailPathView.setText(builder.toString());

                final TextView detailTitleView = view.findViewById(R.id.detailTitle);
                final Category category = item.getCategory();
                detailTitleView.setText(category.getTitle());

                final float count = item.getCount();
                final float price = item.getPrice();
                StringBuilder b = new StringBuilder();
                if (count > 1) {
                    b.append(decorator.prettify(count));

                }
                if (count > 1 && price > 0){
                    b.append(Keys.SPACE).append(Keys.TIMES).append(Keys.SPACE);
                }
                if (price > 0) {
                    b.append(decorator.prettify(price));
                }
                if(count > 1){
                    b.append(Keys.EQUALS);
                    b.append(count * price);
                }
                if (price > 0) {
                    b.append(Keys.SPACE).append(transaction.getCurrency());
                }

                final TextView amountView = view.findViewById(R.id.detailAmount);
                if (b.length() > 0) {
                    amountView.setText(b.toString());
                }
            }
        }, null);
        findCategoryDialog = new SearchDialog<>(context, inflater, API.FIND_CATEGORY,
                new ItemBuilder<Category>() {
                    @Override
                    public Category create(JSONObject json) {
                        System.out.println(json);
                        return new Category(json);
                    }
                },
                new OnChangeListener<Category>() {
                    @Override
                    public void onChange(Category item) {
                        TransactionDetail detail = new TransactionDetail();
                        detail.setCategory(item);
                        detailListAdapter.add(detail);
                    }
                },
                new ListBuilder<Category>() {
                    @Override
                    public void build(Category item, View view) {
                        final TextView detailView = view.findViewById(R.id.itemDetails);
                        final String[] path = item.getPath();
                        StringBuilder builder = new StringBuilder();
                        for (String s : path) {
                            builder.append(s).append(Keys.SLASH);
                        }
                        detailView.setText(builder.toString());
                        final TextView itemValue = view.findViewById(R.id.itemValue);
                        itemValue.setText(item.getTitle());
                    }
                }, context.getResources().getString(R.string.find_category_title));
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
        detailList = view.findViewById(R.id.detailList);

        if(transaction != null) {
            initTypeButton();
            initDateButton();
            initCategoryButton();
            initAccountFrom();
            initAccountTo();
            initTransactionAmount();
            initCurrency();
            initDetailList();
            showRate();
        }
    }

    private void initDetailList() {

        detailListAdapter.addAll(transaction.getDetails());
        detailList.setAdapter(detailListAdapter);
    }

    private void initTransactionAmount() {
        amount.setText(decorator.prettify(transaction.getAmount()));
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
            show = !currency.equals(accountFrom.getCurrency());
        } else if (type == TransactionType.income){
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
        if (type == TransactionType.spending || type == TransactionType.income){
            categoryGroup.setVisibility(View.VISIBLE);
            detailList.setVisibility(View.VISIBLE);
            amount.setEnabled(false);
        }

        if (type == TransactionType.spending){
            accountFromGroup.setVisibility(View.VISIBLE);
            accountToGroup.setVisibility(View.GONE);
        } else if (type == TransactionType.income){
            accountFromGroup.setVisibility(View.GONE);
            accountToGroup.setVisibility(View.VISIBLE);
        } else if (type == TransactionType.transfer){
            categoryGroup.setVisibility(View.GONE);
            accountFromGroup.setVisibility(View.VISIBLE);
            accountToGroup.setVisibility(View.VISIBLE);
            detailList.setVisibility(View.GONE);
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

    private void initCategoryButton() {
        transactionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findCategoryDialog.show(manager, null);
            }
        });
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
