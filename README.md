[toc]
# WanAndroid
🔥 🔥 🔥 一个充满设计感的WanAndroid APP，采用Kotlin 语言，组件化开发，MVVM+JetPack架构设计，**Arouter**、**LiveData**、**ViewModel**、**Room**、**Retrofit2**、**协程Coroutines**、**Flow**等流行技术。

## API
[**玩 Android 开放 API**](http://www.wanandroid.com/blog/show/2)

## 项目截图

| ![](screenshot/page_1.jpg) | ![](screenshot/page_2.jpg) | ![](screenshot/page_3.jpg) |
| --- | --- | --- |
| ![](screenshot/page_4.jpg) | ![](screenshot/page_5.jpg) | ![](screenshot/page_6.jpg) |


## 项目说明

- #### 由于项目中使用了Hilt和Arouter，有大量的注解，因此当build项目失败之后，请clean之后再build。

> Login-Plugin、apt_annotation、ap_compiler文件夹涉及到使用***APT+ASM+自定义Gradle插***件技术，和项目其他功能相关性不大，如果对这几个技术不感兴趣，可暂时不管这几个文件夹的code。

- 通用依赖库在common文件夹下，子组件都在modules文件夹下面。
- 控制子组件单独运行的开关在根目录下的**config.gradle**文件里面。
- 整个项目结构清晰简单，将每个tab做成一个module，让你快速上手组件化知识。
- 那如何将一个tab当成一个module的呢？具体是怎么实现的呢？具体代码可以查看MainActivity里面的写法。
- 该项目主要是学习如何将项目拆分module，是为了拆分module而拆分，实际项目中需要根据业务去拆分module。
- **login模块和content模块由于改动较小，所以将这两个模块已上传到maven上面；APP壳工程既可以源码依赖，也可以aar依赖。**

### 子组件独立运行
*由于项目中使用到有**Hilt**注解，因此需要在子组件的Application添加@HiltAndroidApp注解；但是当子组件合并到APP主工程的时候，由于RootApplication也有@HiltAndroidApp注解，就会导致编译不通过；因此在将子组件合并到APP主工程的时候，需要移除子组件的@HiltAndroidApp注解*

### 网络请求框架使用:
1. Retrofit2 + 协程Coroutines + Flow技术搭建，因此每个模块都有涉及。
2. 该网络请求框架同时支持多个BaseUrl以及动态改变BaseUrl；如果使用的网络接口的baseUrl不是http://www.wanandroid.com 则直接在Retrofit注解上写上完整的请求接口地址即可。具体的实现方式是自定义拦截器，将旧的baseUrl替换成新的即可，
详情可见：**MultiBaseUrlInterceptor**

### Room:
1. 使用到Room的模块主要是module-project模块，涉及到Room的增删改查，定义关联表等知识。
2. 在组件化的情况下，如果某个子组件需要用到数据库，就不要和其他的组件或app工程使用同一个DB；如果这样就会出现耦合，其实如果某个子组件需要用到DB，那么就要为该组件定义一个DB；因为从业务上来讲，该业务被划成子组件，就和其他组件关系不大，因此需要一个独立的DB。

### composeUI:
module-compose模块使用的是compose开发的界面，主要用来学习compose

### Hilt:
1. 组件化使用Hilt，需要在主工程和子module中加入hilt相关依赖
2. ViewModel中使用@HiltViewModel注解，则在Fragment或者Activity中无法只用Inject来实例化ViewModel，具体实例化方法参考@HiltViewModel注解注释的内容
3. ViewModel中使用@HiltViewModel注解，是使用HiltViewModelFactory来创建ViewModel实例，提供了灵活性
4. ViewModel中使用@ActivityRetainedScoped注解，则在Fragment或者Activity中直接用Inject来实例化ViewModel

### Flow StateFlow
1. 使用Flow替代LiveData可以参考module-wechat模块
2. 对于项目中是否有必要将LiveData替换为Flow，可以参考这篇[文章](https://juejin.cn/post/7007602776502960165) 和这个[视频](https://www.youtube.com/watch?v=7VDf-W82ZYE)

## 一些知识点
1. 使用Flow，不管是请求网络返回数据还是从DB中返回数据的时候，已经处于main线程了；网络请求和从DB中查询数据操作是在子线程。
2. 开启混淆的时候，所有的实体bean都必须加上**@Keep**注解，让其不混淆
3. 组件化混淆的时候，可以将通用的和第三方库的混淆配置规则放在base里面，然后每个组件如果有单独的库或不需要混淆的地方，单独配置规则
4. proguard-rules.pro文件是给Library模块自己使用的混淆规则；  
   consumer-rules.pro文件则是会合并到app的混淆规则中，是给包括app在内的其他模块调用时使用的混淆规则；
   详细说明可见该[文档](https://www.freesion.com/article/38811202153/)
5. [LiveData粘性事件](https://tech.meituan.com/2018/07/26/android-livedatabus.html)的解决方式有两种，一种是hook LiveData，将Observer的mLastVersion变量设置成和LiveData的mVersion变量一致；另一种是使用SingleLiveData；具体实现方式参见代码里面的LiveDataBus和SingleLiveData；注意如果使用SingleLiveData的话，如果多个页面使用同一个SingleLiveData对象注册observer，那么只有第一个页面能收到订阅数据；原因是在定义的原子变量身上。
6. [MVC、MVP、MVVM真正区别](https://www.bilibili.com/read/cv6670642)
		MVC：view直接model进行交互
				MVC缺点：
				1.Activity管理了过多的数据处理逻辑
				2.业务逻辑无法复用
				3.异步任务会出现很多嵌套回调
				4.业务逻辑很多的情况下，Manager也会很臃肿
				
		MVP：出现它就是为了取代Activity的地位
		缺点：
		1.接口文件过多
		2.如果页面逻辑复杂，Activity需要实现非常多的接口，并且这些接口和具体页面绑定，无法复用
		3.p层直接持有view层的引用，或者会用到Context，会很容易导致内存泄漏
		4.有时候p层需要明确使用Activity的时候，需要做强制转换
		5.多个页面无法复用同一个接口
		6.总的来说，p层和view层是高度绑定的，p层和定义的那些接口无法高效的复用
		
		MVVM：view通过ViewModel订阅所需数据，ViewModel向View提供数据改变的接口，当view改变引起数据改变或者数据源发生改变时，ViewModel通过订阅告诉view，view进行视图更新。
		优点：
		1. ViewModel只提供数据订阅和数据接口，真正做到了和UI分离；
		2. ViewModel伴随View生命周期，不会出现内存泄漏问题
		3. 多个页面有相同数据来源，ViewModel可以复用相同接口并直接获取数据不会重复请求
		4. 可以实现多个页面数据共享问题，并在Activity销毁重建的时候，数据不会丢失
	


## Arouter使用

#### 1. 使用room之后，组件化操作的时候，如果子module有数据存储需求，由于AppDatabase在主module中，则处理方式有两种：
1.1 在service模块，提供方法的时候，将对应的bean转为string，然后在子module中调用service提供的方法的时候，将获取到的数据转为string即可
1.2 在service模块，提供方法的时候，定义相应的bean即可
1.3 以上做法其实比较耦合，如果子module有数据存储需求，其实应该子module应该有一个单独的db。

#### 2. 每个模块需要有
```class
kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
    generateStubs = true
}
```

#### 3. 每个模块的路由路径的一级目录不能相同

#### 4. 传递参数的时候，参数名称不能是关键字。如：title

#### 5. 接收参数的时候，使用@Autowired注解的时候，变量不能被赋值

#### 6. 接收参数的时候，可以不使用@Autowired 注解，使用intent.extras 详见ContentActivity

#### 7. 不同module的布局文件存在同名的情况下，需要按照module的名称命名。
1. 比如登录模块的toolbar模块，命名为：reg_login_toolbar，content模块的toolbar命名为：content_toolbar

#### 8. 对提供的服务使用@Autowired注解获取实例的时候，不能是private，否则编译不通过

#### 9. 接上一条，在使用服务的实例的之前，需要调用
```class
ARouter.getInstance().inject(this)
```
#### 10. 拦截器的使用
在跳转到某个页面的时候，如果目标页面是需要已经登录了才能跳转的页面，那么可以使用Arouter的拦截器实现；如果登录了就直接跳转到目标页面，如果没有登录就跳转到登录页面。拦截器实现如下：
```class
/**
 * @Description:
 * 该拦截器的作用是：统一页面跳转时，判断用户是否已经登录，
 * 将业务层判断用户是否登录的逻辑统一到这里，业务层就不需要做if else判断了
 * @Author: wangyuebin
 * @Date: 2021/9/17 2:25 下午
 */
@Interceptor(name = "login", priority = 1)
class LoginInterceptor : IInterceptor {

    /**
     * 该集合保存的是需要登录成功之后才跳转的页面，也就是有@RequireLogin注解的页面
     */
    private lateinit var pageList: List<String>

    private var isLogin: Boolean = false

    override fun init(context: Context?) {
        pageList = RefletionUtils.getRequireLoginPages()
        isLogin = RefletionUtils.getLoginStatus()
    }

    /**
     * 在该项目中，也可以使用AppUtils.isLogin来直接判断是否登录，但是这样就耦合了AppUtils
     * 如果不想耦合，就可以使用RefletionUtils.getLoginStatus()来判断是否登录，不过
     * 前提是需要为AppUtils的isLogin变量增加@InjectLogin。这样做的好处就是：
     * 可以将LoginInterceptor和RefletionUtils单独拿出来作为一个lib。
     */
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        // 判断哪些页面需要登录 (在整个应用中，有些页面需要登录，有些是不需要登录的)
        if (pageList.contains(postcard.destination.canonicalName)) {
            if (RefletionUtils.getLoginStatus()) { // 如果已经登录了，则默认不做任何处理
                callback.onContinue(postcard)
            } else { // 未登录，拦截
                callback.onInterrupt(null)
            }
        } else {
            callback.onContinue(postcard)
        }
    }
}
```
从上面代码中可以看出，只需要统计已经登录了才能跳转的页面，将其存入一个List集合中，然后在拦截器里面做判断就好了。

但是使用Arouter只能解决跳转页面的时候判断是否已经登录，在项目中，有些功能是不需要跳转页面就要判断是否登录，登录才执行某个操作，没登录就跳转到登录页面，这个时候Arouter就不适用了；例如本项目中的收藏功能。

## APT+ASM+自定义Gradle插件
- 在进入**我的收藏**页面的时候，使用了注解`@RequireLogin`和`@InjectLogin`两个注解判断该页面是否是需要登录才能进的页面以及是否已经登录了；因此涉及到***APT***技术
- ASM+自定义Gradle插件部分目前只是做了基本的尝试，目的是学习如何自定义Gradle插件以及字节码插装。

## 疑问？
1. 如果新增一个module，或者新增一个功能，需要用到某个常量，然后主app也要用到某个该常量，那么该常量应该定义在哪里？base里面？如果定义在base里面，那么就会经常动base；如果不定义在base里面，那么该定义在哪里？

2. Arouter的路由应该放在哪里？如果放在common-base中，那么就要经常动base，如果放在某个module中，那么其他的module就无法使用，除非在该再定义一个相同的路由。

## 鼓励
通过这个项目希望能够帮助大家更好地学习 Jetpack 与 MVVM 架构。欢迎您提出项目架构设计中设计不合理的地方或者提出更优的解决方案，大家共同进步。如果你喜欢 WanAndroid的设计，感觉本项目的源代码对你的学习有所帮助，可以点右上角 "Star" 支持一下，谢谢！^_^

## 主要开源框架


 - [Retrofit](https://github.com/square/retrofit)
 - [okhttp](https://github.com/square/okhttp)
 - [Glide](https://github.com/bumptech/glide)
 - [BRVH](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
 - [Logger](https://github.com/orhanobut/logger)
 - [AgentWeb](https://github.com/Justson/AgentWeb)
 - [BGABanner-Android](https://github.com/bingoogolapple/BGABanner-Android)
 - [XXPermissions](https://github.com/getActivity/XXPermissions)
 - [Arouter](https://github.com/alibaba/ARouter)




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