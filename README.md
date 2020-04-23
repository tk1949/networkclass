## 在A服务器有个class序列化了之后发送到B服务器，但是B服务器没有这个class的，怎么样才能让B服务器能反序列化成功呢？

本例子实现了上面的功能

### STEP 1 : 

自己实现了个类加载器`NetworkClassLoader`，使用了热加载原理

### STEP 2 :

把自己实现的类加载器替换掉`kryo`的类加载器

> 这是在本地测试的，也可以通过网络的传输，我用的序列化库是kryo

提示：在使用本例子时需要把编译后的`TestClass`移动到项目的根目录下再次运行程序，否则自定义的`NetworkClassLoader`里面实现的`findClass`函数无法实现模拟在网络中加载类

目录结构如下

![图片加载失败](https://github.com/tk1949/networkclass/blob/master/root.jpg)