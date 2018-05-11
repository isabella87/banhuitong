# banhuitong

# 一，准备阶段：

1，git bash 中运行命令：

ssh-keygen -t rsa -C "429509577@qq.com"

2，并将生成的钥匙文件内容上传到git官网创建的用户下，用于设置远程登录权限。

3，测试本地能否正常连接远程git命令：

ssh -T git@github.com

4，运行以下命令查询基本配置： vi .git/config

调整配置文件url内容为以下：

​	url = https://用户名：密码@github.com/用户名/仓库名.git

​	例如：url=	https://isabella87:760810ssg@github.com/isabella87/xrsrv.git

5，设置配置文件中用户基本信息

git config --global user.name "isabella87"

git config --global user.email  429509577@qq.com

6，注意文件“.gitignore”，系统会根据该文件的设置忽略一些文件或者文件夹。（会出现无法识别新加文件的情况）



# 二，同步文件阶段：

1,右击要同步的文件夹选择-Git Bash Here

2，运行以下命令，完成初始化工作，此时在目录里会对个.git文件

git init

3，运行以下命令与远程仓库建立连接，其中isabella87是我再git官网的用户名，xrsrv是某个仓库的名称。

git remote add origin git@github.com:isabella87/xrsrv.git  （此处的origin为命名的id名，可以在同一机器上或者不同机器上创建多个实现协同开发）

4，运行以下命令，同步一次本地与远程仓库中的内容。运行完本地会多一个README.md文件。

1）将远程仓库中的内容拉取到本地：  git pull git@github.com:isabella87/xrsrv.git

2） 本地与远程仓库中代码合并（应用于同步出现问题时）： git pull --rebase origin master

5，运行以下命令，标记哪些文件是要被同步到官网的

1）标记某个文件：git commit -m "文件名"

2）标记所有文件： git commit  -a   （修改文件）

6，运行以下命令推送本地文件到git官网，实现文件同步。

git push -u origin master



------

7，git status 列出项目文件中的文件状态

8，git add 目录或者文件（将文件设置为可提交状态）



注意：7,8步后重复5,6步。



# 三，获取github上项目仓库内容（在本地建立一个项目仓库）

1，现在本地新建一个文件夹，把该文件夹作为一个本地仓库，然后使用终端命令进入该文件夹。

2，初始化版本仓库：git init

3，复制github中项目的的url，使用以下命令进行以下仓库代码的同步

​	git clone url  

例如 git clone https://github.com/isabella87/banhuitong.git

小心由于加了servlet安全过滤，request被读取一遍。 再用request时没有的之前的body内容。  注意避让！！！！！！


# 四，本项目代码说明

1，com.bht.banhuitong.client 客户端UI界面代码，目前有ListGrid 等组成的列表界面模板；

2，com.bht.banhuitong.server 为客户端与后端服务rpc式交互的模块；本部分编写了反射代理模板，可以对各个服务选择性应用该反射代理功能（在反射代理中完成了业务日志，敏感数据的记录）。目前后端服务的数据末端设计了两种实现方式的模板：
	1）其一为：直接在本框架内通过调用com.bht.banhuitong.db里与数据库交互的服务来获取服务端数据；
	2）其二为：通过httpclient方式去获取外部已经写好的服务端服务（调用com.bht.banhuitong.http内服务），获取数据并解析；
	
3，com.bht.banhuitong.filter 为客户端UI界面调用后台服务的中间环节，相当于一个拦截器。
	用于判定当前用户有无访问该服务的权限、当前用户是否长时间未登录、当前用户是否已在别处登录等。依据实际情况判断是否继续拦截。可以设置哪些模块应该该拦截，比如登录服务不宜拦截，查询服务需要拦截等。（通过在配置config.properties文件中增加“server module name and code”记录，来记录模块序号）
	
4，com.bht.banhuitong.share 该部分为客户端和服务端需要用到的公共类，其中包含自定义类注解、写文件等功能。其中类注解实现了以下功能：
	1）每一模块每一服务方法的访问权限制设定；
	2）每一模块每一服务方法是否需要记录业务日志；
	3）每一模块每一服务方法的服务号设置（此项主要是为了定位导出界面列表数据到文件而用，因为服务端导出功能所有列表共用）；
	4）此处还可以继续丰富，比如定义统一的敏感数据......

5，com.bht.banhuitong.exception 对异常的包装。目前主要有以下几点应用：
	1）对通过httpclient中获取的数据，通过解析返回信息中是否含有error来设置异常，并做抛出处理。
	2）本系统中的异常描述，通过在配置config.properties文件中增加“exception code and desc”记录；

	