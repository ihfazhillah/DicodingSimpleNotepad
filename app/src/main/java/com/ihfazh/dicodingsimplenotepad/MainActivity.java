package com.ihfazh.dicodingsimplenotepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etTitle, etBody;
    private Button btnOpen, btnNew, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.edit_title);
        etBody = findViewById(R.id.edit_file);
        btnOpen = findViewById(R.id.btn_open);
        btnNew = findViewById(R.id.btn_new);
        btnSave = findViewById(R.id.btn_save);

        btnOpen.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_new:
                newfile();
                break;
            case R.id.btn_save:
                saveFile();
                break;
            case R.id.btn_open:
                showList();
                break;
        }

    }

    private void saveFile() {
        if (etBody.getText().toString().isEmpty()){
            etBody.setError("Isi text dulu...");
        } else if (etTitle.getText().toString().isEmpty()){
            etTitle.setError("Isi title dulu...");
        } else {
            String title = etTitle.getText().toString();
            String body = etBody.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFileName(title);
            fileModel.setData(body);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving " + fileModel.getFileName()  + " file", Toast.LENGTH_SHORT).show();
        }
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, getFilesDir().list());

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loadData(items[i].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData(String filename) {
        FileModel fileModel = FileHelper.readFromFile(this, filename);
        Toast.makeText(this, "Loading " + fileModel.getFileName() + " data", Toast.LENGTH_SHORT).show();
        etTitle.setText(fileModel.getFileName());
        etBody.setText(fileModel.getData());
    }

    private void newfile() {
        etTitle.setText("");
        etBody.setText("");
        Toast.makeText(this, "Clearing text", Toast.LENGTH_SHORT).show();
    }
}