package aviee.footballscout;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("aviee.footballscout", appContext.getPackageName());
    }

    @Test
    public void testNewPlayerProfileImageButtonIsDisplayed() {

        onView(withId(R.id.newPlayerProfileButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testSavedPlayerProfileImageButtonIsDisplayed() {

        onView(withId(R.id.savedPlayerProfilesButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testSettingsButtonIsDisplayed() {

        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testSettingsButtonContentDescription() {
        onView(withId(R.id.settingsButton)).check(matches(withContentDescription(R.string.settingsImageButtonDescription)));
    }


    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}
