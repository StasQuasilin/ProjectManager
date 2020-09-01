package ua.svasilina.targeton.dialogs.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import ua.svasilina.targeton.R;
import ua.svasilina.targeton.utils.listeners.ChangeListener;

public class DateDialog extends DialogFragment {

    private final LayoutInflater inflater;
    private final DialogType dialogType;
    private final Calendar calendar;
    private final Calendar inner;
    private final ChangeListener changeListener;


    public DateDialog(Calendar calendar, LayoutInflater inflater, DialogType dialogType, ChangeListener changeListener) {
        this.calendar = calendar;
        this.inflater = inflater;
        this.dialogType = dialogType;
        this.changeListener = changeListener;
        inner = Calendar.getInstance();
        inner.setTime(calendar.getTime());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (dialogType == DialogType.calendar) {
            final View view = inflater.inflate(R.layout.date_dialog, null);
            final CalendarView calendar = view.findViewById(R.id.calendar);
            calendar.setDate(inner.getTime().getTime());
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    inner.set(year, month, dayOfMonth);
                }
            });
            builder.setView(view);
            builder.setTitle(R.string.date_select);
        }

        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        return builder.create();
    }

    private void save(){
        calendar.setTime(inner.getTime());
        if (changeListener != null){
            changeListener.onChange();
        }
    }

    public enum DialogType{
        calendar,
        time
    }
}