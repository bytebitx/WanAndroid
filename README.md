
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

七、不同module的布局文件存在同名的情况下，需要按照module的名称命名。
比如登录模块的toolbar模块，命名为：reg_login_toolbar，content模块的toolbar命名为：content_toolbar

八、对提供的服务使用@Autowired注解获取实例的时候，不能是private，否则编译不通过

九、接上一条，在使用服务的实例的之前，需要调用ARouter.getInstance().inject(this)

十、如果新增一个module，或者新增一个功能，需要用到某个常量，然后主app也要用到某个该常量，那么该常量应该定义在哪里？base里面？
如果定义在base里面，那么就会经常动base；如果不定义在base里面，那么该定义在哪里？


