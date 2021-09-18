package com.bbgo.apt_compiler;

import com.bbgo.apt_annotation.CheckLogin;
import com.bbgo.apt_annotation.InjectLogin;
import com.bbgo.apt_annotation.RequireLogin;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/9/17 7:21 下午
 */
@AutoService(Processor.class)
public class CheckLoginProcessor extends AbstractProcessor {

    private Messager mMessager;

    private Set<String> pageList;

    private final String clzNamePrefix = ProcConsts.PROJECT + ProcConsts.SEPARATOR + ProcConsts.MODULE_CHECK + ProcConsts.SEPARATOR;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        pageList = new HashSet<>();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(CheckLogin.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        mMessager.printMessage(Diagnostic.Kind.WARNING, "\nprocessing...\n");
        // 1，获取所有添加了注解的Activity，保存到List中
        parseAnnotation(roundEnv);

        for(String page: pageList) { // 由于使用组件化，所以需要为每个组件都生成对应的文件
            String clzName = page.substring(page.lastIndexOf(".") + 1);

            // 2，创建名为 Login$$类名 的类
            TypeSpec typeSpec = TypeSpec.classBuilder(clzNamePrefix + clzName)
                    .addModifiers(Modifier.PUBLIC)
                    // 3，添加获取类的list的方法
                    .addMethod(createCheckLoginFun())
                    .build();

            // 4，设置包路径：ProcConsts.PKG_NAME
            JavaFile javaFile = JavaFile.builder(ProcConsts.PKG_NAME, typeSpec).build();
            try {
                // 5，生成文件
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mMessager.printMessage(Diagnostic.Kind.WARNING, "\nprocess finish ...\n");
        return true;// 返回false则只会执行一次
    }

    private void parseAnnotation(RoundEnvironment roundEnv) {
        pageList.clear();
        mMessager.printMessage(Diagnostic.Kind.WARNING,"开始处理CheckLogin注解");
        // 得到所有注解为CheckLogin的元素
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(CheckLogin.class);
        for (Element element : elements) {
            // 检查元素是否是一个class.  注意：不能用instanceof TypeElement来判断，因为接口类型也是TypeElement.
            if (element.getKind() != ElementKind.METHOD) {
                mMessager.printMessage(Diagnostic.Kind.WARNING,
                        element.getSimpleName().toString() + "不是方法，不予处理");
                continue;
            }
            // 放心大胆地强转成TypeElement
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            // 包名+类型
            String fullClassName = classElement.getQualifiedName().toString();
            mMessager.printMessage(Diagnostic.Kind.WARNING, "method = " + fullClassName);
            pageList.add(fullClassName);
        }

    }

    /**
     * 创建获取注解名的方法
     */
    private MethodSpec createCheckLoginFun() {
        // 创建名为checkLogin的方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("checkLogin")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC);
        ClassName log = ClassName.get("android.util", "Log");

        methodBuilder.addStatement("$T.d(\"TAG\",\"" + System.currentTimeMillis() + "\")", log);
        methodBuilder.addStatement("return");
        return methodBuilder.build();
    }

}
