<h1 align="center">基于SSM架构的骑士博客项目</h1>

## 项目描述
本项目采用Spring5+SpringMVC+Mybatis3经典架构，其中Spring部分实现无xml纯java配置，支持restful风格。采用MySQL作为数据库，使用阿里巴巴的Druid作为数据库连接池，采用本地文件系统作为图片上传后的存储目录。采用Maven做项目管理，使用Shiro安全框架作身份认证和权限认证，采用Lucene作为全文搜索引擎且搜索部分实现代码高亮。前端部分：前台网页使用Bootstrap3 UI框架设计，后台管理页面使用Jquery EasyUI框架内嵌在Bootstrap3框架内的结构实现美观，使用百度的UEditor作为博文发布的在线编辑器并支持图片上传功能，采用Cropper实现头像上传预览和裁切功能。

## 实现功能

1. 前台模块：用户注册、登录、注销；修改密码，修改头像；全站搜索，标签搜索，日期搜索；查看博主信息，查看热门博文；可对博文评论，可对评论评论。
2. 后台模块：可对博客增删改查，可修改博主信息，可对用户增删改查，可查看和删除评论或子评论，可对外部链接增删改查，可手动刷新系统缓存。

## 亮点

1. 本项目最重要的设计：仿B站的评论模式，从数据库的设计到前端交互，设计了评论模块和内部评论模块，并实现了相应的分页局部异步加载功能。
2. 本项目第二重要设计：设计了博文和标签的多对多关系，一篇博文可以添加多个标签，一个标签可对应多篇博文，同时后台在添加博文和修改博文页面，对博文与标签多对多的添加关系操作，做了人性化风格的前端操作优化。
3. 自己写了一个BootStrapPaginationUtil工具类，实现了bootstrap的前端分页样式的简化。
4. 前台注册部分做了正则验证，若登录成功再次访问登录页面直接跳转主界面。
5. 后台可根据不同的评论跳转其子评论的管理页面。
6. shiro框架使用说明，对前台做了身份验证，对后台做了身份验证+角色授权验证。
7. 控制层对通过url访问控制层需要返回数据到原页面的情况，做了重定向并携带数据的操作，避免了刷新时数据重复提交的情况。
8. 每层的方法都做的一定的入参验证，做到方法对自身负责。

## 不足

1. 每次访问都需要访问控制层，没有实现动静分离。
2. 热门博文设置成了获取所有点击次数最大的10篇博文，而没有设置成自动更新每天热门或每小时热门。
3. 右侧10条最热门博文放在了application中，最好放在缓存中，并设置定时刷新数据。
4. 博主blogger只有一个且ID为1，所以为了防止有人注册与博主相同的userName以登录后台作如下处理：在user表中也要建立一个ID为1，且userName和博主表userName相同，且userName字段唯一并作为博主登录前台的一个账号，同时更新user表中的博主信息时也更新blogger表中的博主信息，反之亦然。
5. 头像文件不应该直接传blob给后端，应该编码为base64字符串，模糊变小后传到后端，在后端转化为blob来存储。
6. 因为希望评论后立即显示，所以没有做评论发布审核的功能；因为修改用户的评论会触犯用户的权利，所以没有做评论修改功能。
7. 后台管理没有做评论的细致化搜索功能，有待完善。

## 备注

1. 项目会创建 D:/Project-Data/blog-data 目录作为文件存储目录。 
2. MySQL数据库建表请参考 mysql表结构 目录。
3. 项目仅供学习参考。