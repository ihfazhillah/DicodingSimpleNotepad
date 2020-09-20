package com.ihfazh.dicodingsimplenotepad;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    private static String TAG = FileHelper.class.getSimpleName();

    static void writeToFile(FileModel fileModel, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileModel.getFileName(), Context.MODE_PRIVATE));
            outputStreamWriter.write(fileModel.getData());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    static FileModel readFromFile(Context context, String filename){
        FileModel fileModel = new FileModel();
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String receivedString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receivedString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receivedString);
                }

                inputStream.close();
                fileModel.setData(stringBuilder.toString());
                fileModel.setFileName(filename);

            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File Not Found: ", e);
        } catch (IOException e){
            Log.e(TAG, "Can not read file: ", e);
        }

        return fileModel;
    }
}
