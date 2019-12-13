package com.baiiu.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class MyClass {

    public static void main(String[] args) throws IOException {
        System.out.println("==================================================");

        //读取
        ClassReader classReader = new ClassReader("com/baiiu/java/Base");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //处理
        ClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
        //输出

        File f = new File("JavaLib/build/classes/java/main/com/baiiu/java/Base.class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(data);
        fout.close();
        System.out.println("now generator cc success!!!!!");


        System.out.println("==================================================");

        new Base().process();
    }

}
