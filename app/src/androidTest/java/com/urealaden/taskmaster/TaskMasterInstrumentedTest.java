package com.urealaden.taskmaster;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TaskMasterInstrumentedTest {
    @Test
    public void testIntent(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getContext();
    }
}
