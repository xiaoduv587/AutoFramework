//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.tools.SimpleJavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class MteSenseStringInstance extends SimpleJavaFileObject {
    private String content = null;

    public MteSenseStringInstance(String name, String content) throws URISyntaxException {
        super(URI.create("string:///" + name.replace(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return this.content;
    }
}
