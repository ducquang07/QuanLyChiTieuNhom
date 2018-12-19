package vn.edu.uit.quanlychitieunhom.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import vn.edu.uit.quanlychitieunhom.R;

public class ThongKe extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thong_ke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            //process your onClick here
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
