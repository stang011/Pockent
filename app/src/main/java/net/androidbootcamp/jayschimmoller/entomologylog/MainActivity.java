package net.androidbootcamp.jayschimmoller.entomologylog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private AppBarConfiguration mAppBarConfiguration;
    private FileManager fileManager;
    public static final String FILE_NAME = "logFile";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_logs,
                R.id.nav_print,
                R.id.nav_record)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        fileManager = new FileManager();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        /*
        int id = item.getItemId();
        if(id == R.id.nav_record)
        {
            Toast.makeText(MainActivity.this, "Record", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.nav_home)
        {
            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.nav_logs)
        {
            Toast.makeText(MainActivity.this, "Logs", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.nav_print)
        {
            Toast.makeText(MainActivity.this, "Print", Toast.LENGTH_SHORT).show();
        }
         */
        NavigationUI.onNavDestinationSelected(item, navController);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            //Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Delete Data")
                    .setMessage("Do you want to delete all saved data?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            fileManager.clear();
                            updateFile();
                            NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                            NavigationView navigationView = findViewById(R.id.nav_view);
                            Menu menu = navigationView.getMenu();
                            MenuItem item = menu.findItem(R.id.nav_home);
                            NavigationUI.onNavDestinationSelected(item, navController);
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Do nothing.
                        }
                    }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void updateFile() {
        FileOutputStream outputStream;
        String fileContents = "";
        int numberEles = fileManager.size();

        for(int i = 0; i < numberEles; i++) {
            fileContents += fileManager.toString(i) + "\n";
        }
        try {
            outputStream = this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
