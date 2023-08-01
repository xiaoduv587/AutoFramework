//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.SimpleJavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class MteSenseInstanceObject extends SimpleJavaFileObject {
    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public MteSenseInstanceObject(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    public byte[] getBytes() {
        return this.bos.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return this.bos;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.bos.close();
    }
}
