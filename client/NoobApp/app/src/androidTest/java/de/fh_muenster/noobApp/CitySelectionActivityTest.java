package de.fh_muenster.noobApp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Testet die Grundlegenden Eigenschaften der CitySelectionActivity
 * @author marius
 */
public class CitySelectionActivityTest extends ActivityInstrumentationTestCase2<CitySelectionActivity> {
    private CitySelectionActivity citySelectionActivity;
    private TextView textView4;
    private Button button2;



    public CitySelectionActivityTest() {
        super(CitySelectionActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        citySelectionActivity = getActivity();
        textView4 = (TextView) citySelectionActivity.findViewById(R.id.textView4);
        button2 = (Button) citySelectionActivity.findViewById(R.id.button2);
    }

    @SmallTest
    public void testPreconditions() {
        assertNotNull("textView4 is null", textView4);
        assertNotNull("button2 is null", button2);
    }

    @MediumTest
    public void testTextView4() {
        final View decorView = citySelectionActivity.getWindow().getDecorView();
        final String expected = citySelectionActivity.getString(R.string.activity_city_selection_staedteauswahl);
        final String actual = textView4.getText().toString();
        assertEquals("textView4 contains wrong text", expected, actual);
        ViewAsserts.assertOnScreen(decorView, textView4);
        assertTrue(View.VISIBLE == textView4.getVisibility());
    }

    @MediumTest
    public void testButton2() {
        final View decorView = citySelectionActivity.getWindow().getDecorView();
        final String expectedButton2Text = citySelectionActivity.getString(R.string.activity_city_selection_uebernehmenbutton);
        final String actualButton2Text = button2.getText().toString();
        ViewAsserts.assertOnScreen(decorView, button2);
        final ViewGroup.LayoutParams layoutParams = button2.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(expectedButton2Text, actualButton2Text);
    }
}