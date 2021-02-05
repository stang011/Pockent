package net.androidbootcamp.jayschimmoller.entomologylog.ui.Print;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.androidbootcamp.jayschimmoller.entomologylog.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PrintFragment extends Fragment
{

    private PrintViewModel printViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        printViewModel = ViewModelProviders.of(this).get(PrintViewModel.class);
        View root = inflater.inflate(R.layout.fragment_print, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_print);
        printViewModel.getText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText(s);
            }
        });

         */
        return root;
    }
}
