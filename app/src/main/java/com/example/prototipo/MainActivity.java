package com.example.prototipo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout listS;
    ImageView add;
    Button save;

    ArrayList<Data> listD =new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listS = findViewById(R.id.listStudents);

        add = findViewById(R.id.Add);

        save = findViewById(R.id.Save);

        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Add:

                addView();

                break;

            case R.id.Save:

                if (checkIfValidAndRead()){
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("list", listD);
                    i.putExtras(b);
                    startActivity(i);
                }

                break;

        }
    }

    private boolean checkIfValidAndRead(){
        listD.clear();

        boolean result = true;

        for(int i = 0; i < listS.getChildCount(); i++){
            View formView= listS.getChildAt(i);

            AppCompatSpinner degree = findViewById(R.id.Degrees);
            EditText name = findViewById(R.id.Name);
            EditText lNameP  = formView.findViewById(R.id.LnameP);
            EditText lNameM = formView.findViewById(R.id.LnameM);
            EditText enrrollment = formView.findViewById(R.id.Enrrollment);

            Data d = new Data();

            if (degree.getSelectedItemPosition()!=0){
                d.setDegree(degree.toString());
            }else{
                result = false;
                break;
            }

            if (!name.getText().toString().equals("")){
                d.setName(name.getText().toString());
            }else{
                result = false;
                break;
            }

            if (!lNameP.getText().toString().equals("")){
                d.setLnameP(lNameP.getText().toString());
            }else{
                result = false;
                break;
            }

            if (!lNameM.getText().toString().equals("")){
                d.setLnameM(lNameM.getText().toString());
            }else{
                result = false;
                break;
            }

            if (!enrrollment.getText().toString().equals("")){
                d.setEnrrollment(enrrollment.getText().toString());
            }else{
                result = false;
                break;
            }

            listD.add(d);

        }

        if(listD.size() == 0){
            result = false;
            Toast.makeText(this, "No dejes ningun campo vacio", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "No se aceptan campos vación.", Toast.LENGTH_SHORT).show();
        }

        return result;

        }

        private void addView(){

            View formView = getLayoutInflater().inflate(R.layout.add_student, null, false);

            EditText name = formView.findViewById(R.id.Name);
            EditText lnameP = formView.findViewById(R.id.LnameP);
            EditText lnameM = formView.findViewById(R.id.LnameM);
            EditText enrrollment = formView.findViewById(R.id.Enrrollment);
            ImageView delete = formView.findViewById(R.id.Delete);
            ImageView keyE = formView.findViewById(R.id.Key);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (name.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ingrése su nombre.", Toast.LENGTH_SHORT).show();
                    } else if (lnameP.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ingrése el apellido", Toast.LENGTH_SHORT).show();
                    } else if (enrrollment.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Matrícula vacía.", Toast.LENGTH_SHORT).show();
                    } else {
                        SignUp("https://garcontech.com/alumno.php");
                    }
                }

            });

            enrrollment.setEnabled(false);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeView(formView);
                }
            });

            keyE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int len = 12;
                    enrrollment.setText(generateRandomPassword(len));
                }
            });

            listS.addView(formView);

        }

    private void removeView(View v){
        listS.removeView(v);
    }

    public static String generateRandomPassword(int len){
        final String chars = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<len; i++){
            int randomIndex = random.nextInt(chars.length());
            sb.append((chars.charAt(randomIndex)));
        }
        return  sb.toString();
    }

    EditText n, ap, am, m;
    private void SignUp(String URL) {

        n = findViewById(R.id.Name);
        ap = findViewById(R.id.LnameP);
        am = findViewById(R.id.LnameM);
        m = findViewById(R.id.Enrrollment);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro Exitoso.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> signup = new HashMap<String, String>();

                //signup.put("course", Materia.getEditText().getText().toString());

                signup.put("name", n.getEditableText().toString());
                signup.put("lnameP", ap.getEditableText().toString());
                signup.put("lnameM", am.getEditableText().toString());
                signup.put("enrrollment", m.getEditableText().toString());

                return signup;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}