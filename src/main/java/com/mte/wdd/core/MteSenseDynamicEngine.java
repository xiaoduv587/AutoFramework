//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mte.wdd.core;

import java.io.File;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.*;
import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;

public class MteSenseDynamicEngine {
    private static MteSenseDynamicEngine ourInstance = new MteSenseDynamicEngine();
    private ClassLoader parentClassLoader = this.getClass().getClassLoader();


    public static MteSenseDynamicEngine getInstance() {
        return ourInstance;
    }

    private MteSenseDynamicEngine() {
    }

    public Class<?> javaCodeToObject(String className, String sourceCode) throws Exception {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MteSenseInstanceManager fileManager = new MteSenseInstanceManager(compiler.getStandardFileManager(null, (Locale) null, (Charset) null));

        Iterable<? extends JavaFileObject> javaFileObjects = Arrays.asList(new MteSenseStringInstance(className, sourceCode));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, javaFileObjects);


        boolean success = task.call();
        fileManager.close();
        Class clazz = null;

        if (success) {
            MteSenseInstanceObject mteSenseInstanceObject = fileManager.getJavaClassObject();
            MteSenseInstanceLoader dynamicClassLoader = new MteSenseInstanceLoader(this.parentClassLoader);
            clazz = dynamicClassLoader.loadInstance(className, mteSenseInstanceObject);

        } else {
            throw new Exception("Compilation failed.");
        }

        return clazz;
    }


    private String compilePrint(Diagnostic diagnostic) {
        StringBuffer res = new StringBuffer();
        res.append("Code:[" + diagnostic.getCode() + "]\n");
        res.append("Kind:[" + diagnostic.getKind() + "]\n");
        res.append("Position:[" + diagnostic.getPosition() + "]\n");
        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
        res.append("Source:[" + diagnostic.getSource() + "]\n");
        res.append("Message:[" + diagnostic.getMessage((Locale) null) + "]\n");
        res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
        return res.toString();
    }
}
