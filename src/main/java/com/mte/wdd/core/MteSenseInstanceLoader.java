//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.core;

import java.net.URL;
import java.net.URLClassLoader;

public class MteSenseInstanceLoader extends URLClassLoader {
    public MteSenseInstanceLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    public Class findInstanceByClassName(String className) throws ClassNotFoundException {
        return super.findClass(className);
    }

    public Class loadInstance(String fullName, MteSenseInstanceObject mteSenseInstanceObject) {
        byte[] classData = mteSenseInstanceObject.getBytes();
        return super.defineClass(fullName, classData, 0, classData.length);
    }
}
