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

    private EditText edtWebsite;
    private EditText edtIdTrungTam;
    private EditText edtIdMay;
    private EditText edtNgonNgu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_config);
        mContext = this;

        initLayout();
        initData();
    }

    private void initLayout()
    {
        edtWebsite = (EditText)findViewById(R.id.edt_web_site);
        edtIdTrungTam = (EditText)findViewById(R.id.edt_id_trung_tam);
        edtIdMay = (EditText)findViewById(R.id.edt_id_may);
        edtNgonNgu = (EditText)findViewById(R.id.edt_id_ngon_ngu);

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

    private void initData()
    {
        String website = SharedPreferenceUtils.getWebSite(mContext);
        String idTT = SharedPreferenceUtils.getIdTrungTam(mContext);
        String idMay = SharedPreferenceUtils.getIdMay(mContext);
        String ngonNgu = SharedPreferenceUtils.getNgonNgu(mContext);
        if(website.equalsIgnoreCase("")){
            edtWebsite.setText("http://demo.ronviet.vn");
        } else {
            edtWebsite.setText(website);
        }

        if(idTT.equalsIgnoreCase("")){
            edtIdTrungTam.setText("3");
        } else {
            edtIdTrungTam.setText(idTT);
        }

        if(idMay.equalsIgnoreCase("")){
            edtIdMay.setText("3");
        } else {
            edtIdMay.setText(idMay);
        }

        if(ngonNgu.equalsIgnoreCase("")){
            edtNgonNgu.setText("1");
        } else {
            edtNgonNgu.setText(ngonNgu);
        }
    }
}
