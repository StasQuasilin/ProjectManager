package ua.svasilina.targeton.utils.storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ua.svasilina.targeton.utils.constants.Constants;

import static android.content.Context.MODE_PRIVATE;

public class StorageUtil {

    public void save(String fileName, String data, Context context){
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            outputStream.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(String fileName, Context context){
        try {
            final FileInputStream fileInputStream = context.openFileInput(fileName);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            final StringBuilder builder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null){
                builder.append(s);
            }
            fileInputStream.close();
            reader.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Constants.EMPTY;
    }

}
