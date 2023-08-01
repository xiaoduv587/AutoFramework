//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.core;

import java.io.IOException;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject.Kind;

public class MteSenseInstanceManager extends ForwardingJavaFileManager {
    private MteSenseInstanceObject mteSenseInstanceObject;

    public MteSenseInstanceManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
        if (this.mteSenseInstanceObject == null) {
            this.mteSenseInstanceObject = new MteSenseInstanceObject(className, kind);
        }

        return this.mteSenseInstanceObject;
    }

    public MteSenseInstanceObject getJavaClassObject() {
        return this.mteSenseInstanceObject;
    }
}
