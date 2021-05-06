package ua.svasilina.targeton.utils;

import android.content.Context;

import java.text.NumberFormat;
import java.util.Locale;

public class FloatDecorator {

    NumberFormat nf;

    public FloatDecorator (Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        nf = NumberFormat.getInstance(locale);
    }

    public String prettify(float f){
        return nf.format(f);
    }

    public String prettify(double amount) {
        return nf.format(amount);
    }
}
