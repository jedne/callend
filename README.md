
# callend
手机黑名单，拦截电话和短信，并记录日志

拦截电话是通过反射出系统TelephonyManager，从而获得ITelephony，然后调用endCall来挂掉电话。
短信是通过监听短信广播，通过设置权限到int最大值来提升权限，在收到广播后调用abortBroadcast来结束广播传递。

短信的广播为了保证优先级是用动态注册的形式完成的，并且监听了开机广播。

添加黑名单的功能目前可以通过自定义输入账号添加也可以选择通讯录中的号码来添加。

CSDN：http://blog.csdn.net/jeden/article/details/46621531
