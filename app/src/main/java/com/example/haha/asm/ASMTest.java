package com.example.haha.asm;

import android.text.TextUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASMTest {

    public static void main(String[] args) {
        redefineHelloWorldClass();
    }

    public static void redefineHelloWorldClass(){
        try {
            InputStream inputStream = new
                    FileInputStream("C:\\Users\\haha\\Desktop\\HelloWorld.class");
            //1.创建ClassReader读入.class文件到内存中
            ClassReader reader = new ClassReader(inputStream);
            //2.创建ClassWriter对象，将操作之后的字节码的字节数组回写
            ClassWriter writer = new ClassWriter(reader,ClassWriter.COMPUTE_MAXS);
            //3.创建自定义ClassVisitor对象
            ClassVisitor change = new ChangeVisitor(writer);
            //4.将ClassVisitor对象传入ClassReader中
            reader.accept(change,ClassReader.EXPAND_FRAMES);

            System.out.println("Success!");
            byte[] code = writer.toByteArray();
            try {
                // 将二进制流写到本地磁盘上
                FileOutputStream fos = new
                        FileOutputStream("C:\\Users\\haha\\Desktop\\OutputHelloWorld.class");
                fos.write(code);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ChangeVisitor extends ClassVisitor{

        public ChangeVisitor(ClassVisitor classVisitor) {
            super(Opcodes.ASM5, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name
                , String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access
                    , name, desc, signature, exceptions);
            return new ChangeAdapter(Opcodes.ASM4,methodVisitor,access,name,desc);
        }
    }

    static class ChangeAdapter extends AdviceAdapter{

        private int startTimeId = -1;
        private String methodName = null;

        /**
         * Creates a new {@link AdviceAdapter}.
         *
         * @param api    the ASM API version implemented by this visitor. Must be one
         *               of {@link Opcodes#ASM4} or {@link Opcodes#ASM5}.
         * @param mv     the method visitor to which this adapter delegates calls.
         * @param access the method's access flags (see {@link Opcodes}).
         * @param name   the method's name.
         * @param desc   the method's descriptor.
         */
        protected ChangeAdapter(int api, MethodVisitor mv
                , int access, String name, String desc) {
            super(api, mv, access, name, desc);
            this.methodName = name;
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            mv.visitMethodInsn(INVOKESTATIC
                    ,"java/lang/System","currentTimeMillis"
                    ,"()J",false);
            mv.visitIntInsn(LSTORE,startTimeId);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            int durationId = newLocal(Type.LONG_TYPE);
            mv.visitMethodInsn(INVOKESTATIC
                    ,"java/lang/System","currentTimeMillis"
                    ,"()J",false);
            mv.visitVarInsn(LLOAD,startTimeId);
            mv.visitInsn(LSUB);
            mv.visitVarInsn(LSTORE, durationId);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("The cost time of " + methodName + "() is ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(LLOAD, durationId);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn(" ms");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
    }
}
