# appium对淘宝App简单测试的demo
## 环境配置
> Idea
> Android stuio  
> JDK+Maven+Testng  
> node.js+Npm+appium  
> 夜神模拟器   

```
git clone https://github.com/Mickey001/appium_test_taobao.git
```
安装Node.js 安装包及源码下载地址为：https://nodejs.org/en/download/。  

appium安装
```
npm install -g appium
```
appium环境检查
```
npm install -g appium-doctor
appium-doctor
```
接下来
> install maven  
> install Android stuio  
> install Android SDK  
> create Android AVD  

配置夜神模拟器  
教程https://blog.csdn.net/fbb1995/article/details/79869772  
如遇链接问题https://blog.csdn.net/sunruirui1028/article/details/79805629  
配置Android模拟器  
https://blog.csdn.net/w690333243/article/details/93046451  

运行之前同时开启两种模拟器  
因版本签署问题，需要提前手动安装好apk，脚本或adb安装程序内部会做限制，导致重要功能无法使用，如登录。  
如需测试完整功能需debug包，并且apk包含机器签名，故而分开测试，敬请谅解  
请使用Android原生模拟器跑启动淘宝app的用例  
请使用夜神模拟器提前安装好淘宝app，并使用私人账号登录，然后再执行用例  
## run
```
appium
mvn test
```
此次dom操作单一从黑盒角度设计，测试用例较为脆弱，期待后续develop与test联动，debug过程中定义好定位器及方法
本次测试没有过多的封装以及异常处理，时间仓促可能会有问题，欢迎随时交流  

## 演示效果  

启动淘宝app  

<video src="https://vd3.bdstatic.com/mda-ke7u19bsrmyx7hjv/v1-cae/mda-ke7u19bsrmyx7hjv.mp4?playlist=%5B%22hd%22%2C%22sc%22%5D" controls="controls" width="500" height="300">您的浏览器不支持播放该视频！</video>
