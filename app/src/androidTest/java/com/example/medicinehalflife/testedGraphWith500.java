package com.example.medicinehalflife;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.danapplabs.medicinehalflife.R;
import com.danapplabs.medicinehalflife.graph.MainActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class testedGraphWith500 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testedGraphWith500() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.dosage_edittext),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinatorLayout_main),
                                        1),
                                7),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.dosage_edittext), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinatorLayout_main),
                                        1),
                                7),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.dosage_edittext), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinatorLayout_main),
                                        1),
                                7),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.dosage_edittext), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinatorLayout_main),
                                        1),
                                7),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("500"));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.dosage_edittext), withText("500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinatorLayout_main),
                                        1),
                                7),
                        isDisplayed()));
        appCompatEditText5.perform(closeSoftKeyboard());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_graph), withText("Graph"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction webView = onView(
                allOf(withId(R.id.webview_graph),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinatorLayout_main),
                                        1),
                                11),
                        isDisplayed()));
        webView.check(matches(isDisplayed()));

        ViewInteraction webView2 = onView(
                allOf(withId(R.id.webview_graph),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.coordinatorLayout_main),
                                        1),
                                11),
                        isDisplayed()));
        webView2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
