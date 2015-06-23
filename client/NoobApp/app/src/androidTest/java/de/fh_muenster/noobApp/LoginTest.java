package de.fh_muenster.noobApp;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import de.fh_muenster.noobApp.CitySelectionActivity;
import de.fh_muenster.noobApp.LoginActivity;
import de.fh_muenster.noobApp.R;

/**
 * Created by marco
 * @author marco
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private String passwordStringHash;
    private Switch testMode;
    private TextView register;
    private static final String TAG = LoginActivity.class.getName();
    String emailString;
    String passwordString;
    private LoginActivity nLoginActivity;

    public LoginTest() {
        super(LoginActivity.class);
    }

    //Initialiseriung der Variablen
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        nLoginActivity = getActivity();
        register = (TextView) nLoginActivity.findViewById(R.id.textView2);
        email = (EditText) nLoginActivity.findViewById(R.id.editText3);
        password = (EditText) nLoginActivity.findViewById(R.id.editText4);
        loginButton = (Button) nLoginActivity.findViewById(R.id.button2);
    }

    //Testen ob die Hints gesetzt worden sind
    public void testMyFirstTestTextView_labelText() {
        final String expectedEmail =
                nLoginActivity.getString(R.string.username);
        final String expectPassword = "Passwort";
        final String emailE = email.getHint().toString();
        final String passwort = password.getHint().toString();
        assertEquals(expectedEmail, emailE);
        assertEquals(expectPassword, passwort);
    }
    //Testen ob der LoginButton clickable ist
    public void testClick() {
        nLoginActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                assertTrue(loginButton.performClick());
                assertTrue(register.performClick());
            }
        });


    }
}