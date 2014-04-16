package com.human.expert.fragments;

import android.*;
import android.R;
import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.human.expert.*;
import com.human.expert.fragments.MainActivity;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class MainActivityTest \
 * com.human.expert.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public MainActivityTest() {
        super("com.human.expert.fragments", MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }
    public void testNavigate() throws Exception {
        solo.waitForView(ListView.class);
        ListView list = solo.getCurrentViews(ListView.class).get(0);
        for(int i=1; i<= list.getAdapter().getCount(); i++)    {
            solo.clickInList(i) ;
            clickBtn();
        }

    }

    private void clickBtn(){
        solo.waitForView(Button.class);
        ArrayList<Button> buttons = solo.getCurrentViews(Button.class);
        for(int i=0; i< buttons.size(); i++) {
            if(buttons.get(i).getVisibility() == View.VISIBLE) {
                solo.clickOnButton(i);
                clickBtn();
            }
        }
        solo.waitForView(R.id.home);
        solo.goBack();
    }
}
