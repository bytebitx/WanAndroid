package com.bbgo.login_plugin;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/9/18 5:36 下午
 */
class LoginMethodVisitor extends AdviceAdapter {

    private String className;
    private String superName;

    protected LoginMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String className, String superName) {
        super(api, methodVisitor, access, name, descriptor);
        this.className = className;
        this.superName = superName;
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    protected void onMethodEnter() {
        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn("1631966148193");
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        super.onMethodEnter();

    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
    }
}
