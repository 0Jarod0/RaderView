package com.example.haha.asm;

import junit.framework.TestCase;

import org.junit.Test;

public class ASMTestTest extends TestCase {
    @Test
    public void testASM() {
        ASMTest.redefineHelloWorldClass();
    }
}