package com.example.lap_lenovo.threads;

import android.app.NotificationManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn,btn2;
    ProgressBar progressBar,progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        btn2=(Button)findViewById(R.id.button2);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar2=(ProgressBar)findViewById(R.id.progressBar2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subProceso();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TareaAsync tarea=new TareaAsync();
                tarea.execute();
                TareaAsync2 tareaAsync2=new TareaAsync2();
                tareaAsync2.execute();
            }
        });
    }

    class TareaAsync extends AsyncTask<Integer,Integer,Void>{
        public TareaAsync() {
            super();
        }

        //antes de la ejecucion del hilo
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);
        }
        // despues de la ejecucion del hilo
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TareaAsync2 tareaAsync2=new TareaAsync2();
            tareaAsync2.execute();
        }

        //cuando existe un cambio relevante en el hilo
        // ya no muy utilizado
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        // aqui van las tareas del hilo

        @Override
        protected Void doInBackground(Integer... integers) {
            for(int i=1;i<11;i++)
                try {
                    Thread.sleep(1000);
                    update( i*10);
                    //--- aqui--
                    TareaAsync2 t2=new TareaAsync2();
                    t2.execute();
                }catch (InterruptedException e){}

            return null;
        }
    }
    void update(int i){
        progressBar.setProgress(i);
    }

    class TareaAsync2 extends AsyncTask<Integer,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar2.setBackgroundColor(Color.BLUE);
            progressBar2.setMax(100);
            progressBar2.setProgress(0);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar2.setProgress(0);
            progressBar2.setBackgroundColor(Color.WHITE);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            for(int i=1;i<11;i++){
                try{
                Thread.sleep(1000);
                upDate2(1*10);
                }
                catch (Exception e){}
            }
            return null;
        }
    }

    void upDate2(int i){
        progressBar2.setProgress(i);
    }

    void subProceso(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               for(int i=0;i<5;i++)
               try {
                   Thread.sleep(1000);
               }catch (InterruptedException e){}
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(getApplicationContext(),"tiempo terminado",Toast.LENGTH_SHORT).show();
                   }
               });
           }
       }).start();

        NotificationManager l=getSystemService(SENSOR_SERVICE);

    }
}









