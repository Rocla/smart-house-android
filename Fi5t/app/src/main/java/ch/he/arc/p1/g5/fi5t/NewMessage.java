package ch.he.arc.p1.g5.fi5t;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NewMessage extends Activity {

    String Destinataire;
    String Message;
    EditText eMessage;
    EditText eDestinataire;
    Button bEnvoyer;
    Integer Compteur=0;

    //---------------------------Valeur d'envoie pour l'Intent Post-its-------------------------------//
    String DateEnvoie;
    String HeureEnvoie;
    String MessageEnvoie;
    String DestinataireEnvoie;
    String UtilisateurActuelleEnvoie;
    //---------------------------Valeur d'envoie pour l'Intent Post-its-------------------------------//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        eMessage=(EditText)findViewById(R.id.extMessage);
        eDestinataire=(EditText)findViewById(R.id.extDestinataire);
        bEnvoyer=(Button)findViewById(R.id.bEnvoyer);

        //------------------------------------------Ajout des destinataires existant--------------------------------------------------//
        final List<String> list = new ArrayList<String>();
        list.add("Seb");
        list.add("Jordane");
        list.add("SuperMama");
        //------------------------------------------Ajout des destinataires existant--------------------------------------------------//

        bEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] f= (String[]) list.toArray(new String[0]);
                for( int i = 0 ; i < f.length;i++)
                {
                    if(f[i].matches(eDestinataire.getText().toString())==true)
                    {
                        Compteur++;
                    }
                }
                if(Compteur >=1) {
                    if(eMessage.getText().toString()!="") {

                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, "Message Envoyé", duration);
                        toast.show();
                        Compteur = 0;
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}