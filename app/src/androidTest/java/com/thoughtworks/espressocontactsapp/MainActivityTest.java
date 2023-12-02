
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.thoughtworks.espressocontactsapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        }
    }



    @Test
    public void validateIfNoContactsFound(){
        onView(withId(R.id.textNoContacts))
                .perform(click());

        onView(withId(R.id.textNoContacts))
                .check(matches(isDisplayed()));

    }
    @Test
    public void addContact(){
        onView(withId(R.id.btnAddContact))
                .perform(click());

        onView(withId(R.id.editFirstName))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editFirstName))
                .perform(typeText("Sayan"), closeSoftKeyboard());

        onView(withId(R.id.editLastName))
                .perform(typeText("NANA"), closeSoftKeyboard());

        onView(withId(R.id.editPhoneNumber))
                .perform(typeText("9898989999"), closeSoftKeyboard());

        onView(withId(R.id.editEmail))
                .perform(typeText("sayan.b@abc.com"), closeSoftKeyboard());

        onView(withId(R.id.editEmail))
                .perform(click());

    }


    @Test
    public void editContact(){
        onView(withId(R.id.btnAddContact))
                .perform(click());

        onView(withId(R.id.editFirstName))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editFirstName))
                .perform(typeText("Sayan"), closeSoftKeyboard());

        onView(withId(R.id.editLastName))
                .perform(typeText("NANA"), closeSoftKeyboard());

        onView(withId(R.id.editPhoneNumber))
                .perform(typeText("9898989999"), closeSoftKeyboard());

        onView(withId(R.id.editEmail))
                .perform(typeText("sayan.b@abc.com"), closeSoftKeyboard());

        onView(withId(R.id.editEmail))
                .perform(click());

    }

    @Test
    public void deleteContact(){

        onView(withId(R.id.imgEditContact))
                .check(matches(isDisplayed()));

        onView(withId(R.id.imgEditContact))
                .perform(click());

        onView(withId(R.id.btnDelete))
                .perform(click());

    }