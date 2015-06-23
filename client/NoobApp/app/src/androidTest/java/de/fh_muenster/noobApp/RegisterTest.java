package de.fh_muenster.noobApp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by marco
 * @author marco
 */
public class RegisterTest extends ActivityInstrumentationTestCase2<RegisterActivity> {
    private EditText email;
    private EditText password;
    private EditText passwordwdh;
    private EditText benutzername;
    private Button registerButton;
    private RegisterActivity nRegisterActivity;

    public RegisterTest() {
        super(RegisterActivity.class);
    }

    //Initialisierung der Variablen
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        nRegisterActivity = getActivity();
        benutzername = (EditText) nRegisterActivity.findViewById(R.id.editText6);
        email = (EditText) nRegisterActivity.findViewById(R.id.editText5);
        password = (EditText) nRegisterActivity.findViewById(R.id.editText7);
        passwordwdh = (EditText) nRegisterActivity.findViewById(R.id.editText8);
        registerButton = (Button) nRegisterActivity.findViewById(R.id.button6);
    }

    //Hint überprüfen ob diese in der App gesetzt werden
    public void testMyFirstTestTextView_labelText() {
        final String expectedEmail ="Email-Adresse";
        final String expectPassword = "Passwort";
        final String expectUsername="Benutzername";
        final String expectPasswordW="PasswortWdh.";
        final String emailE = email.getHint().toString();
        final String passwort = password.getHint().toString();
        final String benutzernamme1 = benutzername.getHint().toString();
        final String passwortW = passwordwdh.getHint().toString();
        assertEquals(expectedEmail, emailE);
        assertEquals(expectPassword, passwort);
        assertEquals(expectUsername, benutzernamme1);
        assertEquals(expectPasswordW, passwortW);
    }
    //Testen ob der registerButton clickable ist
    public void testClick() {
        nRegisterActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                assertTrue(registerButton.performClick());

            }
        });


    }
}
