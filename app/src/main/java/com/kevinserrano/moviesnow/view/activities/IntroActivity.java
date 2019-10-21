package com.kevinserrano.moviesnow.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.kevinserrano.moviesnow.R;
import com.kevinserrano.moviesnow.utilities.ManagerSharedPreferences;
import com.kevinserrano.moviesnow.utilities.SystemUtils;

public class IntroActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ManagerSharedPreferences.getPreferences(
                "showIntro",true)){
            setContentView(R.layout.activity_intro);
            initViews();
        } else {
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }



    private void initViews(){

        View btnInit = findViewById(R.id.btnInit);
        CheckBox checkBox = findViewById(R.id.chb_term_and_cond);
        TextView txtPdP = findViewById(R.id.txtPtp);
        txtPdP.setText(Html.fromHtml(getString(R.string.p_de_p)));
        txtPdP.setMovementMethod(LinkMovementMethod.getInstance());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                btnInit.setAlpha(1f);
            } else {
                btnInit.setAlpha(0.7f);
            }
        });
        findViewById(R.id.btnInit).setOnClickListener(v -> {
            if(SystemUtils.getInstance().isNetworkAvailable(this)) {
                if(checkBox.isChecked()){
                    ManagerSharedPreferences.editPreferences(
                            "showIntro",false);
                    startActivity(new Intent(this,HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, R.string.message_warning_1,
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this,R.string.message_error_1,
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}
