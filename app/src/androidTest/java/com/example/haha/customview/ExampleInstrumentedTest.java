package com.example.haha.customview;

import android.content.Context;

import androidx.test.runner.AndroidJUnit4;

import com.example.haha.asm.ASMTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void testASM(){
        ASMTest.redefineHelloWorldClass();
    }
}
