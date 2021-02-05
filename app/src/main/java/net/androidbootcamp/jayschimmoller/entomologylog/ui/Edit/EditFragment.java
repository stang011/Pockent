package net.androidbootcamp.jayschimmoller.entomologylog.ui.Edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.androidbootcamp.jayschimmoller.entomologylog.FileManager;
import net.androidbootcamp.jayschimmoller.entomologylog.Log;
import net.androidbootcamp.jayschimmoller.entomologylog.R;

import java.io.IOException;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class EditFragment extends Fragment
{

    FileManager fileManager;
    public static final String FILE_NAME = "logFile";
    public static final String FILE_TWO = "editFile";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        //preLoadFields();
        //initialFM();

        return root;
    }

    // Load initial editable field
    private void preLoadFields() {

    }

    private void initialFM() {
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

    private void updateFMAtIndex(Log l, int i) {

    }

    private void updateFile() {

    }
}
