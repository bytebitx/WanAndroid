# WanAndroid
🔥 🔥 🔥 一个充满设计感的APP，采用Kotlin 语言，组件化开发，MVVM+JetPack架构设计，Arouter、LiveData、ViewModel、Room、Retrofit2、
协程Coroutines、Flow等流行技术。

# 项目截图


<img src="https://github.com/bbggo/WanAndroid/blob/main/screenshot/page_1.png?raw=true" width="280" alt="首页"/><img src="https://github.com/bbggo/WanAndroid/blob/main/screenshot/page_2.png?raw=true" width="280" alt="广场"/><img src="https://github.com/bbggo/WanAndroid/blob/main/screenshot/page_3.png?raw=true" width="280" alt="公众号"/>


# Arouter使用

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

十一、由于所有的数据库操作在app主目录中，那么定义的相关的bean应该放在那里？如果放在base中，那么则可解决app主目录和module都使用的情况；
否则app主目录放一份，module也要放一份。

## LICENSE

```
Copyright (C) bbgo, Open source codes for study only.
Do not use for commercial purpose.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```