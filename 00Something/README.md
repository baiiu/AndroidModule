# 00Something

the file `auto.py` and `auto.config` is a Python script to get the project code with Git 、 assemble android APK 、 install on the divices and then start the app.
Once run it, it will clone the project and run into your devices.

`auto.py`和`auto.config`是使用Python写的脚本，主要用来从Git拉项目代码后打包，安装到机器并自动启动。
运行后将会自动拉取代码，并打包后安装到机器自动打开。

## Usage
config the `auto.config` file:
`Root_SDK_Dir` is your SDK absolute path
`git_clone_address` is your Git address to clone.
`base_file_dir` is a absolute directory, maybe the Desktop.
`create_dir_name` is the directory which your name of the project in,it will be created withIn the base_file_dir.

after config these properties,you can run this script use python command line. Wait for a while to assemble apk, then install on your devices.

配置`auto.config`文件：
`Root_SDK_Dir` 是你的SDK的绝对路径
`git_clone_address` 是你的Gid地址
`base_file_dir` 是一个绝对路径，可随便填
`create_dir_name` 将在`base_file_dir`生成一个目录，用于存放项目代码和打出来的APK文件

配置好后你就可以使用python命令行运行该脚本，等待打包完成后运行到机器。


## 文章总结
[使用Python实现Android打包并安装后启动](http://blog.csdn.net/u014099894/article/details/52145994)
