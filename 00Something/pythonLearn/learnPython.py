#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import os
try:
    import xml.etree.cElementTree as ET
except ImportError:
    import xml.etree.ElementTree as ET



def moveFiles(sourceDir,  targetDir):#复制一级目录下的所有文件到指定目录
    if not os.path.exists(sourceDir):
        return;

    for file in os.listdir(sourceDir):
         sourceFile = os.path.join(sourceDir,  file)
         targetFile = os.path.join(targetDir,  file)
         if os.path.isfile(sourceFile) and ('unaligned' not in os.path.basename(sourceFile)):
                open(targetFile,"wb").write(open(sourceFile,"rb").read())


code_dir = '/Users/baiiu/Desktop/AndroidApp/SourceCode'
source_apk_dir = code_dir + '/' + 'app/build/outputs/apk'

moveFiles(source_apk_dir,'/Users/baiiu/Desktop/AndroidApp/Apk')



def createLocalPropertiesFile(sourceDir,fileName):
    if not os.path.exists(sourceDir):
        return

    fileDir = sourceDir + '/' + fileName

    if os.path.exists(fileDir):
        print('已经存在该文件了')
        return

    print('create file:')
    f = open(fileDir,'w');
    f.write('sdk.dir=/Users/baiiu/Library/Android/sdk')
    f.close()

# createLocalPropertiesFile('/Users/baiiu/Desktop/test','local.properties')


#
# # 1. 设置路径
# file_dir = "/Users/baiiu/Desktop"
#
# #2. 测试一个文件
# file_name = "test.txt"
#
# #3. 连接起来，全路径，指向一个文件
# file_abs = os.path.join(file_dir, file_name)
#
#
# # 判断是否绝对路径
# print(1,os.path.isabs(file_dir))
# print (2,os.path.isabs(file_name))
# print (3,os.path.isabs(file_abs))
#
# # 判断是否真实存在
# print (4,os.path.exists(file_abs))
# print (4,os.path.exists(file_dir))
# print (5,os.path.exists(os.path.join(file_dir,"xxx")))
#
# # 判断是否是个目录
# print (6,os.path.isdir(file_dir))
# print (7,os.path.isdir(file_abs))
#
# # 判断是否是个文件
# print (8,os.path.isfile(file_dir))
# print (9,os.path.isfile(file_abs))
#










# file_abs = "/Users/baiiu/";
#
#
# if()
# os.mkdir("test");
#
# os.chdir(file_abs+'/test');
#
# f = open("a.txt","w+");
# print(f.name);



# os.system("cd Desktop/")
# os.system("git clone https://github.com/baiiu/DropDownMenu.git");
#
#
# print(os.name);


# 查看当前目录的绝对路径:
# >>> os.path.abspath('.')
# '/Users/michael'
# 在某个目录下创建一个新目录，首先把新目录的完整路径表示出来:
# >>> os.path.join('/Users/michael', 'testdir')
# '/Users/michael/testdir'
# 然后创建一个目录:
# >>> os.mkdir('/Users/michael/testdir')
# 删掉一个目录:
# >>> os.rmdir('/Users/michael/testdir')
