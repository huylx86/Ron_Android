package com.ronviet.ron.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ronviet.ron.R;
import com.ronviet.ron.utils.DialogUtiils;
import com.ronviet.ron.utils.SharedPreferenceUtils;

public class FirstConfigActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_config);
        mContext = this;

        initLayout();
    }

    private void initLayout()
    {
        final EditText edtWebsite = (EditText)findViewById(R.id.edt_web_site);
        final EditText edtIdTrungTam = (EditText)findViewById(R.id.edt_id_trung_tam);
        final EditText edtIdMay = (EditText)findViewById(R.id.edt_id_may);
        final EditText edtNgonNgu = (EditText)findViewById(R.id.edt_id_ngon_ngu);

        Button btnOk = (Button)findViewById(R.id.btn_ok);
        Button btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String website = edtWebsite.getText().toString();
                String idTT = edtIdTrungTam.getText().toString();
                String idMay = edtIdMay.getText().toString();
                String ngonNgu = edtNgonNgu.getText().toString();

                if(website.trim().equalsIgnoreCase("")){
                    new DialogUtiils().showDialog(mContext, getString(R.string.field_required), false);
                    edtWebsite.requestFocus();
                    return;
                }

                if(idTT.trim().equalsIgnoreCase("")){
                    new DialogUtiils().showDialog(mContext, getString(R.string.field_required), false);
                    edtIdTrungTam.requestFocus();
                    return;
                }

                if(idMay.trim().equalsIgnoreCase("")){
                    new DialogUtiils().showDialog(mContext, getString(R.string.field_required), false);
                    edtIdMay.requestFocus();
                    return;
                }

                if(ngonNgu.trim().equalsIgnoreCase("")){
                    new DialogUtiils().showDialog(mContext, getString(R.string.field_required), false);
                    edtNgonNgu.requestFocus();
                    return;
                }

                SharedPreferenceUtils.saveWebSite(mContext, website);
                SharedPreferenceUtils.saveIdTrungTam(mContext, idTT);
                SharedPreferenceUtils.saveIdMay(mContext, idMay);
                SharedPreferenceUtils.saveNgonNgu(mContext, ngonNgu);

                onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
