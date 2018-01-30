package com.example.prabhdeep.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Translater extends AppCompatActivity {

    String SYMBOL;
    TextView tvLang;
    String texttobetranslated;
    public static String[] languages={"Select target language","Albanian", "Azerbaijani", "Belaurian", "Bulgarian", "Czech", "Dutch", "English","Finnish",
            "French", "German", "Hungarian", "Italian", "Latvian", "Lithuanian", "Norwegian",
            "Polish","Portuguese", "Romanian", "Russian", "Serbian", "Slovak", "Spanish", "Swedish", "Turkish", "Ukrainian"
    };
    public static String[] langsym={"","sq", "az", "be", "bg", "cs", "nl","en", "fi", "fr", "de", "hu", "it", "lv", "lt", "no", "pl", "pt", "ro", "ru", "sr", "sk", "es", "sv", "tr", "uk"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translater);
        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<String> languageadapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,languages);
        spinner.setAdapter(languageadapter);
        final EditText etLang = findViewById(R.id.etLang);
        Button btnTranslate= findViewById(R.id.btnTrans);
        tvLang=findViewById(R.id.tvLang);
        etLang.setText(getIntent().getStringExtra("result"));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SYMBOL=langsym[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texttobetranslated = etLang.getText().toString();
                Translate(texttobetranslated,SYMBOL);

            }
        });
    }
    void Translate(String ttbt,String symbol){
        TranslatorBackGroundTask tbt = new TranslatorBackGroundTask(new TranslatorBackGroundTask.MyAsyncTaskListener() {
            @Override
            public void onPostExecute(String s) {
                tvLang.setText(s);
            }
        });
        tbt.execute(ttbt,symbol);

    }
}
