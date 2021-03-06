SMSManager
==========

Cydu's sms manager

## 起因

http://e.weibo.com/2758197137/zzgO5b20U

@微博平台架构 发起的24小时的 #Hackathon24# 项目, 最近事多,本来不打算参加的, 后经不住他们诱惑, 同时自己也有一个小的需求, 没有得到很好满足. 本着"自已动手, 丰衣足食"的精神,给自己放了半天假,
搞了一个我自己的短信管理器. 

## 业务场景

1. 某人有两个号码, 两部手机一部小米, 一部iPhone
    1. 前者2G的老号(各种银行,老友都记的这个号,更换麻烦)
    1. 后者3G的号,上网方便...
1. iPhone不支持 "双卡双待", 其他支持"双卡双待"的手机又不想用
1. 出门不想带两部, 甚至更多手机...

电话好办, 可以通过呼叫转接来搞定(一个额外的好处是,响一声就挂的烦恼没有了). 

短信麻烦, 尤其是 银行的消费确认短信, 动态校验码 之类的...

## 解决

### 有钱人的解决方案

1. 请一个小秘, 专职管理自己的手机, 碰到重要的短信就手动转到新手机上....

### 码农的解决方案

码农请不起小秘, 但是会写代码, 所以有了这个...

1. 一个Android端的小应用(iOS受平台安全限制, 应该做不到), 监听短信到达事件, 收到短信后, 通过网络上传短信...
1. 一个服务器端的程序(收到上传短信后, 保存入数据库,方便后续查看), 发一条定向微博(即用微博的通道来及时通知新的手机有短信来了...)
    1. 本来可以发私信的, 但是外网不提供这个接口....
    1. 本来可以在Android端调用Android的SDK直接完成,不需要服务器端的, 但是调试过程中没搞定....所以用服务器端中转一下了.
    1. 其实也可以发iMessage, Email, MSN, GTalk消息来通知的.

估计一下工作量, 半天肯定能搞定, 不会影响第二天工作, 所以就开搞了...

插曲: 下载AndroidStudio开发环境折腾了半天, 好几个插件被墙了, 耽误相当多时间.

### 简单说明: 

Android Client:

1. 继承BroadcastReceiver监听广播消息, 如果是SMS就处理, 需要android.permission.RECEIVE_SMS, android.permission.READ_SMS权限. 
1. 短信很私密, 很重要, 传输过程不能走https, 但是最起码的加密还是要有的. 
    1. 随便找了一个开源的加解密函数 MCrypt 来做(主要是php和java的函数都比较简单, 仅用于demo玩, 工业场景谨慎选择)
    1. text 里面可能包含 "&" 等特殊字符, 需要做一下base64编码.
1. 同样找了一个开源的 HttpRequest 来做网络的传输
1. 只上传短信的 接收时间, 发送号码, 短信内容 几个关键信息, 查通讯录啥的其他功能还没搞.
1. 试着用Weibo Android SDK搞, 但因为环境的原因, 没搞起来,放弃了.
1. AndroidStudio 确实比Eclips好用!

PHP Server:

1. Web界面(主要是管理界面了), 当然用bootstrap搞了, 直接拿demo页面改的.
1. 模板本来直接拼的, 想想还是搞了个Smarty, 显得专业一点
1. mysql连接, 想搞个库连的, 没想到趁手的, 反正重在玩,没那么严谨,就直接裸拼sql了
1. 下行就是一个简单的列表搞出来, 删除啥的,上sql了...
1. 做了个简单的登录控制, 不然是个人就能看我的短信了.
    1. 用户创建啥的没做, 直接code进代码里面, 要加用户就加两行php.
        1. BAE的同学辛苦回去Review一下代码, "grep cydu * -r"就行, 看到类似的代码记得删除.
1. 用了一个Weibo 的PHP SDK, 拿到授权token后, 由于用户不会主动触发发微博, 而是消息触发的. 所以直接把授权码记在服务器端了(当前就我自己, 所以还好).
    1. 收到上行短信后, 拿上次记下来的token, 发一条 分组可见 的微博, 然后我,我老婆,还有我儿子的微博就都收到相关的短信通知了... 
        1. [重要!!!] 对我个人有意思,有想法的MM们, 你们千万不要用短信发太露骨的信息! 切记!
1. 号码 / 内容过滤, 直接置到代码里面, 做的字符串比较
1. 短信的存储, 简单暴力起见, 没有加密, 定期删除(只保留最后10条, 就ok了).
    1. 我的短信里面, 除了 报警, 保险, 银行, 猎等头, 理财, 中奖, 促销...之外, 就是群发的拜年短信了!
    
## 总结

全搞下来, 真正的代码量其实不超过300行, 半天搞定(晚上8点多开始搞, 搞到2点多弄完...). 

从工程的角度看, 这些代码是毫无价值的, 各种论坛搜集过来,然后挤到一起, 未经测试, 毫无设计, 毫无复用价值, 解决的问题也非常小众, 场景比较狭小....

但是...从目前来看, 我的需求得到满足了, 出于纪念, 还是放上来了... ^_^

PS: 自从有了微博, 我其实很少用短信了, 各种沟通私信/邮件就ok了.

