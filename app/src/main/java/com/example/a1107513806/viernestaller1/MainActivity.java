package com.example.a1107513806.viernestaller1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a1107513806.viernestaller1.serializable.Instrucciones;

public class MainActivity extends AppCompatActivity {

    EditText tamano;
    EditText posX;
    EditText posY;
    EditText rcol,g,b;
    EditText ip;
    Button enviarsote;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tamano=(EditText) findViewById(R.id.editText1);
        posX=(EditText) findViewById(R.id.editText2);
        posY=(EditText) findViewById(R.id.editText3);
        rcol=(EditText) findViewById(R.id.editText4);
        g=(EditText) findViewById(R.id.editText5);
        b=(EditText) findViewById(R.id.editText6);
        enviarsote= (Button) findViewById(R.id.button);
        ip=(EditText) findViewById(R.id.editText7);




        enviarsote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(enviarCom()).start();
            }
        });


    }

    private Runnable enviarCom(){
         Runnable r= new Runnable() {
            @Override
            public void run() {
                Instrucciones ins;
             ins= new Instrucciones(Float.parseFloat(posX.getText().toString()),Float.parseFloat(posY.getText().toString()),Float.parseFloat(tamano.getText().toString()),Float.parseFloat(rcol.getText().toString()),Float.parseFloat(g.getText().toString()),Float.parseFloat(b.getText().toString()));
                Comunicacion.getInstance(ip.getText().toString()).enviar(ins);
            }
        };

        return r;
    }
}
