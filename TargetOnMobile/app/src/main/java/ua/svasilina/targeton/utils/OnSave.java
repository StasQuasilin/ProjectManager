package ua.svasilina.targeton.utils;

public interface OnSave<T> {
    void handle(T t);
}
