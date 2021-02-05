package net.androidbootcamp.jayschimmoller.entomologylog.ui.Print;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrintViewModel extends ViewModel
{
    private MutableLiveData<String> mText;

    public PrintViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is the print fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}
