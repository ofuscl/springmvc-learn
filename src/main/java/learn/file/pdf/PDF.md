wkhtmltopdf和itext pdf的区别

wkhtmltopdf：
参照地址：https://www.cnblogs.com/xionggeclub/p/6144241.html
参照地址：https://blog.csdn.net/qq_14873105/article/details/51394026
1、支持直接把浏览器中网页转化为pdf;
2、下载wkhtmltopdf转化工具安装即可；下载地址：http://www.ouyaoxiazai.com/soft/yyrj/69/49234.html
3、可配置环境变量在windows环境下通过cmd执行；
4、可直接通过java的Runtime.getRuntime().exec(cmd.toString());执行；

优点：灵活，不需要画复杂的pdf模板
缺点：表格容易破页，修复破页难度大

重点说明：
1、linux和windows需要安装不同版本；
2、linux上需要设置文件的读写执行权限；通过命令ll查看，r-读，w-写，x-执行



