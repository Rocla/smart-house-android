package ch.he.arc.p1.g5.fi5t;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;


public class Services extends Activity {

    public static final String MyProfile = "MyProfileKey" ;
    public static final String NombreUtilisateurs="NombreUtilisateursKey";
    public static final String NombrePOSTIT="NombrePOSTITKey";

    public static final String FirstName = "FirstNameKey";
    public static final String LastName = "LastNameKey";
    public static final String UserName = "UserNameKey";
    public static final String UserRole = "UserRoleKey";
    public static final String Password = "PasswordKey" ;
    public static final String RememberMeCheckbox = "RememberMeCheckboxKey" ;
    public static final String SentMessage = "SentMessageKey" ;

    public static final String OfflineUsername = "OfflineUsernameKey";
    public static final String OfflinePassword = "OfflinePasswordKey";

    public static final String ExterneFirstName = "ExterneFirstNameKey";
    public static final String ExterneLastName = "ExterneLastNameKey";
    public static final String ExterneUserName = "ExterneUserNameKey";
    public static final String ExterneUserRole = "ExterneUserRoleKey";
    public static final String ExternePassword = "ExternePasswordKey";
    public static final String ExterneUserID = "ExterneUserIDKey" ;

    public static final String TemperatureChauffage = "TemperatureChauffageKey" ;
    public static final String TemperatureChauffageMin = "TemperatureChauffageMinKey" ;
    public static final String TemperatureChauffageMax = "TemperatureChauffageMaxKey" ;



    public static final String DeleteUserID="DeleteUserIDKey";

    public static final String ModifyUserID="ModifyUserIDKey";
    public static final String ModifyUserName="ModifyUserNameKey";
    public static final String ModifyUserFirstName="ModifyUserFirstNameKey";
    public static final String ModifyUserPseudo="ModifyUserPseudoKey";
    public static final String ModifyUserMDP="ModifyUserMDPKey";
    public static final String ModifyUserRole="ModifyUserRoleKey";



    public static final String NEWUserName="NEWUserNameKey";
    public static final String NEWUserFirstName="NEWUserFirstNameKey";
    public static final String NEWUserPseudo="NEWUserPseudoKey";
    public static final String NEWUserMDP="NEWUserMDPKey";
    public static final String NEWUserRole="NEWUserRoleKey";

    public static final String ExternePOSTITDATE = "ExternePOSTITDATEKey";
    public static final String ExternePOSTITHEURE = "ExternePOSTITHEUREKey";
    public static final String ExternePOSTITENVOYERPSEUDO = "ExternePOSTITENVOYERPSEUDOKey";
    public static final String ExternePOSTITSTATUS = "ExternePOSTITSTATUS";
    public static final String ExternePOSTITMESSAGE = "ExternePOSTITMESSAGEKey";
    public static final String ExternePOSTITID = "ExternePOSTITIDKey" ;









    // Déclaration TAG
    private static final String TAG ="Menu" ;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        SharedPreferences UserProfile = getSharedPreferences(MyProfile,MODE_PRIVATE);

        if(!UserProfile.getString(UserRole,"").matches("Admin"))
        {
            MenuItem item3  = menu.findItem(R.id.Users);
            item3.setVisible(false);
        }

        MenuItem itemMateriel  = menu.findItem(R.id.Material);
        itemMateriel.setVisible(false);


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Context context = getApplicationContext();

        //String Test = MonProfil.FirstName;
        //SharedPreferences sharedProfile = getSharedPreferences(MyProfile,MODE_PRIVATE);
        //String string = sharedProfile.getString(LastName,"default_value_here_if_string_is_missing");

        //CharSequence text = "Hello toast!";
        //int duration = Toast.LENGTH_SHORT;
        //Toast.makeText(context, string, duration).show();

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem Item)
    {
        Intent intent = null;
        switch (Item.getItemId())
        {
            case R.id.Users:
                // Récupération du nom;
                Class Nom=this.getClass();

                //Context context = getApplicationContext();
                //int duration = Toast.LENGTH_SHORT;
                //Toast toast = Toast.makeText(context,this.getLocalClassName().toString(), duration);
                //toast.show();
                if(Nom!=Users.class)
                {intent = new Intent(getApplicationContext(),Users.class);
                    startActivity(intent);}
                if(Nom!=Main.class&&Nom!=Users.class)
                {finish();
                }

                return true;

            case R.id.PageAccueil:
                Class Nom1=this.getClass();
                if(Nom1!=Main.class)
                {

                    onBackPressed();
                }
                return true;
            case R.id.PostIt:
                Class Nom2=this.getClass();
                if(Nom2!=POSTIT.class)
                {intent = new Intent(getApplicationContext(),POSTIT.class);
                    startActivity(intent);}
                if(Nom2!=Main.class&&Nom2!=POSTIT.class)
                {finish();
                }
                return true;
            case R.id.Material:
                Class Nom3=this.getClass();
                if(Nom3!=Materiel.class)
                {intent = new Intent(getApplicationContext(),Materiel.class);
                    startActivity(intent);}
                if(Nom3!=Main.class&&Nom3!=Materiel.class)
                {finish();
                }


                return true;
            case R.id.Parameter:
                Class Nom4=this.getClass();
                if(Nom4!=Parametre.class)
                {intent = new Intent(getApplicationContext(),Parametre.class);
                    startActivity(intent);}
                if(Nom4!=Main.class&&Nom4!=Parametre.class)
                {finish();
                }

                return true;
            case R.id.MonProfil:
                Class Nom5=this.getClass();
                if(Nom5!=MonProfil.class)
                {intent = new Intent(getApplicationContext(),MonProfil.class);
                    startActivity(intent);}
                if(Nom5!=Main.class&&Nom5!=MonProfil.class)
                {finish();
                }

                return true;

            default:
                return super.onOptionsItemSelected(Item);

        }
    }

}
