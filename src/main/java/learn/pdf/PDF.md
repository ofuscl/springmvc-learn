wkhtmltopdf和itext pdf的区别

wkhtmltopdf：
1、支持直接把浏览器中网页转化为pdf;
2、下载wkhtmltopdf转化工具安装即可；下载地址：http://www.ouyaoxiazai.com/soft/yyrj/69/49234.html
3、可配置环境变量在windows环境下通过cmd执行；
4、可直接通过java的Runtime.getRuntime().exec(cmd.toString());执行；

优点：灵活，不需要画复杂的pdf模板
缺点：表格容易破页，修复破页难度大



