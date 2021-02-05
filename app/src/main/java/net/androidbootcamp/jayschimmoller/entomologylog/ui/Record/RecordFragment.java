package net.androidbootcamp.jayschimmoller.entomologylog.ui.Record;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import net.androidbootcamp.jayschimmoller.entomologylog.FileManager;
import net.androidbootcamp.jayschimmoller.entomologylog.Log;
import net.androidbootcamp.jayschimmoller.entomologylog.MainActivity;
import net.androidbootcamp.jayschimmoller.entomologylog.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class RecordFragment extends Fragment
{

    private RecordViewModel recordViewModel;
    private int year, month, day;
    private DatePicker datePicker;
    private TextView dateField, nameField, speciesField, genusField, familyField, notesField, methodField, latField, longField;
    private Calendar calendar;
    DatePickerDialog picker;
    FileManager fileManager;
    public static final String FILE_NAME = "logFile";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_record, container, false);

        /*
        final TextView textView = root.findViewById(R.id.text_record);
        recordViewModel.getText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText(s);
            }
        });

         */

        nameField = (TextView) root.findViewById(R.id.nameField);
        familyField = (TextView) root.findViewById(R.id.familyField);
        genusField = (TextView) root.findViewById(R.id.genusField);
        speciesField = (TextView) root.findViewById(R.id.speciesField);
        methodField = (TextView) root.findViewById(R.id.methodField);
        latField = (TextView) root.findViewById(R.id.latField);
        longField = (TextView) root.findViewById(R.id.longField);
        dateField = (TextView) root.findViewById(R.id.dateField);
        notesField = (TextView) root.findViewById(R.id.notesField);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dateField.setText(showDate(year, month + 1, day));

        ImageButton imageButton = (ImageButton) root.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                picker = new DatePickerDialog(root.getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        dateField.setText(showDate(year, month + 1, dayOfMonth));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        initializeFM();

        Button clearButton = (Button) root.findViewById(R.id.clearBtn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearFields();
                Toast.makeText(getContext(), "Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        Button saveButton = (Button) root.findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(goodData())
                {
                    addLog();
                    updateFile();
                    clearFields();
                    Toast.makeText(getContext(), "Log Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "All fields must be full", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearFields();

        return root;
    }

    private void initializeFM() {
        fileManager = new FileManager();
        try
        {
            Scanner scanner = new Scanner(getContext().getApplicationContext().openFileInput(FILE_NAME));
            String input;

            while (scanner.hasNextLine())
            {
                input = scanner.nextLine();
                Log log = new Log(input);

                if(!fileManager.contains(log)) {
                    fileManager.addLog(log);
                    System.out.println("LOG " + fileManager.toString(log));
                }
            }
            scanner.close();
        }
        catch(IOException e) {
              e.printStackTrace();
        }
    }

    private void addLog() {
        Log newLog = new Log(nameField.getText().toString() + "," + familyField.getText().toString() + "," +
                             genusField.getText().toString() + "," + speciesField.getText().toString() + "," + methodField.getText().toString() + "," +
                             latField.getText().toString() + "," + longField.getText().toString() + "," + dateField.getText().toString() + "," +
                             notesField.getText().toString());
        fileManager.addLog(newLog);
    }

    private void updateFile() {
        FileOutputStream outputStream;
        String fileContents = "";
        int numberEles = fileManager.size();

        for(int i = 0; i < numberEles; i++) {
            fileContents += fileManager.toString(i) + "\n";
        }
        try {
            outputStream = getContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private boolean goodData() {
        if(!nameField.getText().toString().equals("") && !familyField.getText().toString().equals("") && !genusField.getText().toString().equals("") &&
           !speciesField.getText().toString().equals("") && !methodField.getText().toString().equals("") && !latField.getText().toString().equals("") &&
           !longField.getText().toString().equals("") && !dateField.getText().toString().equals("") && !notesField.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void clearFields() {
        nameField.setText("");
        familyField.setText("");
        genusField.setText("");
        speciesField.setText("");
        methodField.setText("");
        latField.setText("");
        longField.setText("");
        notesField.setText("");
    }

    private String showDate(int year, int month, int day) {
        String date = (new StringBuilder().append(month).append("/").append(day).append("/").append(year)).toString();
        return date;
    }
}