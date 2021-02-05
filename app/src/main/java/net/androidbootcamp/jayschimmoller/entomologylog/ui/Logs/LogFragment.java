package net.androidbootcamp.jayschimmoller.entomologylog.ui.Logs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import net.androidbootcamp.jayschimmoller.entomologylog.FileManager;
import net.androidbootcamp.jayschimmoller.entomologylog.Log;
import net.androidbootcamp.jayschimmoller.entomologylog.R;
import net.androidbootcamp.jayschimmoller.entomologylog.ui.Edit.EditFragment;
import net.androidbootcamp.jayschimmoller.entomologylog.ui.Print.PrintFragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class LogFragment extends Fragment
{

    private LogViewModel logViewModel;
    private FileManager fileManager;
    public static final String FILE_NAME = "logFile";
    public static final String FILE_TWO = "editFile";

    TextView name = null, family = null, genius = null, species = null, method = null, location = null, date = null, notes = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        logViewModel = ViewModelProviders.of(this).get(LogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logs, container, false);

        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog, null);

        name = (TextView) customLayout.findViewById(R.id.alertName);
        family = (TextView) customLayout.findViewById(R.id.alertFamily);
        genius = (TextView) customLayout.findViewById(R.id.alertGenus);
        species = (TextView) customLayout.findViewById(R.id.alertSpecies);
        method = (TextView) customLayout.findViewById(R.id.alertMethod);
        location = (TextView) customLayout.findViewById(R.id.alertLocation);
        date = (TextView) customLayout.findViewById(R.id.alertDate);
        notes = (TextView) customLayout.findViewById(R.id.alertNotes);

        initializeFM();

        List<HashMap<String, String>> aList = new ArrayList<>();

        for(int i =  0; i < fileManager.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_title", fileManager.getLogAt(i).displayString());
            hm.put("listview_count", Integer.toString(i + 1));
            aList.add(hm);
        }

        String[] from = {"listview_title", "listview_count"};
        int[] to = {R.id.custom, R.id.count};

        SimpleAdapter simpleAdapter = new SimpleAdapter(root.getContext(), aList, R.layout.item_list_custom, from, to);
        ListView androidListView = (ListView) root.findViewById(android.R.id.list);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //String message = "Do you want to delete this log?";
                final int p = position;

                Log insect = fileManager.getLogAt(p);

                //initializeAlert(p);
                LayoutInflater inflater = getLayoutInflater();
                //View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);
                //initializeAlert(p);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
                //initializeAlert(p);
                //builder.setView(dialogLayout);
                final TextView input = new TextView(getContext());
                input.setTextAppearance(getActivity(), R.style.fontForAlertDialog);
                input.setPadding(60,50,30,50);
                input.setGravity(Gravity.CENTER);
                //initializeAlert(p);
                builder.setView(input);

                input.setText("");
                //input.setText("Selected Insect Log\n\n");
                input.setGravity(Gravity.LEFT);
                input.setTextSize(20);
                input.append(insect.getName() + "\n\n");
                input.append(insect.getFamily() + "\n");
                input.append(insect.getGenus() + " ");
                input.append(insect.getSpecies() + "\n\n");
                input.append(insect.getMethod() + "\n");
                input.append(insect.getLatitude() + "N, " + insect.getLongitude() + "W\n");
                input.append(insect.getDate() + "\n\n");
                input.append(insect.getNotes());

                //initializeAlert(p);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        fileManager.removeLogAt(p);
                        updateFile();
                        reloadList();
                        Toast.makeText(getContext(), "Log Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Do nothing
                    }
                });
                //builder.show();
                //initializeAlert(p);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#25371d"));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTypeface(null, Typeface.BOLD);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#25371d"));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTypeface(null, Typeface.BOLD);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(18);

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                LinearLayout parentLayout = (LinearLayout) positiveButton.getParent();
                parentLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                View leftSpacer = parentLayout.getChildAt(1);
                leftSpacer.setVisibility(View.GONE);

                /*
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Insect")
                        .setMessage(message)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                fileManager.removeLogAt(p);
                                updateFile();
                                reloadList();

                                //updateEditFile(p);
                                //launchEditFrag();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        })
                        .show();
                        */
            }
        });

        /*
        final TextView textView = root.findViewById(R.id.text_logs);
        logViewModel.getText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText(s);
            }
        });
         */

        /*
        Button check = (Button) root.findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), fileManager.size() + "", Toast.LENGTH_SHORT).show();
            }
        });
         */

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
                    // System.out.println("LOG " + fileManager.toString(log));
                }
            }
            scanner.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeAlert(int p) {
        //name.setText("Test1");
        //family.setText("Test2");
        Toast.makeText(getContext(), name.getText().toString(), Toast.LENGTH_SHORT).show();
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

    private void updateEditFile(int i) {
        FileOutputStream outputStream;
        String fileContents = i + "";
        try {
            outputStream = getContext().openFileOutput(FILE_TWO, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void launchEditFrag() {
        /*
        EditFragment nextFrag = new EditFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.logFragmentContainer, nextFrag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        */
    }


    private void reloadList() {
        LogFragment fragment = (LogFragment) getFragmentManager().findFragmentById(R.id.nav_host_fragment);
        getFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
    }
}