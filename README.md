
一、使用room之后，组件化操作的时候，如果子module有数据存储需求，由于AppDatabase在主module中，则处理方式有两种：
1.在service模块，提供方法的时候，将对应的bean转为string，然后在子module中调用service提供的方法的时候，将获取到的数据转为string即可
2.在service模块，提供方法的时候，定义相应的bean即可

二、每个模块需要有
kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
    generateStubs = true
}

三、每个模块的路由路径的一级目录不能相同

四、传递参数的时候，参数名称不能是关键字。如：title

五、接收参数的时候，使用@Autowired注解的时候，变量不能被赋值

六、接收参数的时候，可以不使用@Autowired注解，使用intent.extras 详见ContentActivity
