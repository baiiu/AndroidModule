package com.baiiu.logkotplugin;


import org.gradle.internal.impldep.org.objectweb.asm.*;
import org.gradle.internal.impldep.org.objectweb.asm.commons.*;

/**
 * author: zhuzhe
 * time: 2019-12-20
 * description:
 */
public class LogVisitor extends ClassVisitor {

    /**
     * 类名
     */
    private String className;

    /**
     * 父类名
     */
    private String superName;

    /**
     * 该类实现的接口
     */
    private String[] interfaces;

    public LogVisitor(String className, ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    /**
     * ASM进入到类的方法时进行回调
     *
     * @param name 方法名
     */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
            String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        methodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {

            private boolean isInject() {
                //如果父类名是AppCompatActivity则拦截这个方法,实际应用中可以换成自己的父类例如BaseActivity
                if (superName.contains("AppCompatActivity")) {
                    return true;
                }
                return false;
            }

            @Override
            public void visitCode() {
                super.visitCode();

            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return super.visitAnnotation(desc, visible);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                super.visitFieldInsn(opcode, owner, name, desc);
            }


            /**
             * 方法开始之前回调
             */
            @Override
            protected void onMethodEnter() {
                if (isInject()) {
                    if ("onCreate".equals(name)) {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitMethodInsn(INVOKESTATIC,
                                           "com/baiiu/pluginapplication/TraceUtil",
                                           "onActivityCreate", "(Landroid/app/Activity;)V",
                                           false);
                    } else if ("onDestroy".equals(name)) {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitMethodInsn(INVOKESTATIC, "com/baiiu/pluginapplication/TraceUtil"
                                , "onActivityDestroy", "(Landroid/app/Activity;)V", false);
                    }
                }
            }

            /**
             * 方法结束时回调
             * @param i
             */
            @Override
            protected void onMethodExit(int i) {
                super.onMethodExit(i);
            }
        };
        return methodVisitor;

    }

    /**
     * 当ASM进入类时回调
     *
     * @param name 类名
     * @param superName 父类名
     * @param interfaces 实现的接口名
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
        this.interfaces = interfaces;
    }
}
