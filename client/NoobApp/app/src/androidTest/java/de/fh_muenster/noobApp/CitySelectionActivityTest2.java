package de.fh_muenster.noobApp;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.ContextThemeWrapper;
import android.widget.Button;

/**
 * Created by marius on 22.06.15.
 */
public class CitySelectionActivityTest2 extends ActivityUnitTestCase<CitySelectionActivity> {
    private Intent intent;

    public CitySelectionActivityTest2() {
        super(CitySelectionActivity.class);
    }

    @Override
    protected void setUp() throws  Exception {
        super.setUp();
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.noob_theme);
        setActivityContext(context);
        intent = new Intent(getInstrumentation().getTargetContext(), CitySelectionActivity.class);
    }

    @MediumTest
    public void testPreconditions() {
        startActivity(intent, null, null);
        final Button button2 = (Button) getActivity().findViewById(R.id.button2);
        assertNotNull("citySelectionActivity is null", getActivity());
        assertNotNull("button2 is null", button2);
    }

    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(intent, null, null);
        final Button button2 = (Button) getActivity().findViewById(R.id.button2);
        button2.performClick();
        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull(launchIntent);
        assertTrue(isFinishCalled());
    }
}
