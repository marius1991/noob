package de.fh_muenster.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import de.fh_muenster.noobApp.CategorySelectionActivity;
import de.fh_muenster.noobApp.CitySelectionActivity;
import de.fh_muenster.noobApp.R;

/**
 * Created by marius on 18.06.15.
 */
public class CitySelectionTest extends ActivityInstrumentationTestCase2<CitySelectionActivity> {

    private CitySelectionActivity citySelectionActivity;
    private TextView mtextView4;
    private Button button2;
    private Intent intent;

    public CitySelectionTest() {
        super(CitySelectionActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        citySelectionActivity = getActivity();
        mtextView4 = (TextView) citySelectionActivity.findViewById(R.id.textView4);
        button2 = (Button) citySelectionActivity.findViewById(R.id.button2);
        intent = new Intent(getInstrumentation().getTargetContext(), CategorySelectionActivity.class);
        citySelectionActivity.startActivity(intent);
    }


    @SmallTest
    public void testPreconditions() {
        assertNotNull("mCitySelectionActivity is null", citySelectionActivity);
        assertNotNull("mtextView4 is null", mtextView4);
        assertNotNull("button2 is null", button2);
    }

    @SmallTest
    public void testTextView4() {
        final String expected = citySelectionActivity.getString(R.string.activity_city_selection_staedteauswahl);
        final String actual = mtextView4.getText().toString();
        assertEquals(expected, actual);
    }

    @MediumTest
    public void testButton2layout() {
        final View decorView = citySelectionActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, button2);
        final ViewGroup.LayoutParams layoutParams =
                button2.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @SmallTest
    public void testOpenNextActivity() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(CategorySelectionActivity.class.getName(), null, false);

        // open current activity.
        CitySelectionActivity myActivity = getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.button2);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                button.performClick();
            }
        });

        CategorySelectionActivity nextActivity = (CategorySelectionActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity .finish();
    }
}