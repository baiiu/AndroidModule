#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import os
# import shutil



PackageName = 'com.baiiu.zhihudaily'
LauncherActivity = 'newsList.view.NewsListActivity'
Root_SDK_Dir = '/Users/baiiu/Library/Android/sdk'

# 1.设置目录
base_file_dir = '/Users/baiiu/Desktop'
create_dir_name='AndroidApp'
create_code_dir_name = 'SourceCode'
create_apk_dir_name = 'Apk'

file_dir = base_file_dir + '/' + create_dir_name    #/Users/baiiu/Desktop/AndroidApp
code_dir = file_dir + '/' + create_code_dir_name      #/Users/baiiu/Desktop/AndroidApp/SourceCode
apk_dir = file_dir + '/' + create_apk_dir_name      #/Users/baiiu/Desktop/AndroidApp/Apk

git_clone_address = "https://github.com/baiiu/ZhihuDaily.git";

##########################################################################################
#2. 在桌面上创建目录
print ('现在所在位置： ' + os.getcwd())
os.chdir(base_file_dir);
print ('进入桌面： ' + os.getcwd())

print('\n')
print('=============================================')
print('create dirs')
print('=============================================')

if not os.path.exists(file_dir):
    os.mkdir(create_dir_name)

os.chdir(file_dir)
print('进入AndroidApp： ' + os.getcwd())

if not os.path.exists(code_dir):
    os.mkdir(create_code_dir_name)

if not os.path.exists(apk_dir):
    os.mkdir(create_apk_dir_name)

##########################################################################################
#3. 执行git clone命令

# 进入SourceCode目录下
os.chdir(code_dir)
print('进入SourceCode下： ' + os.getcwd())

print('\n')
print('=============================================')
print('git clone or git pull')
print('=============================================')

if not os.listdir(code_dir):
    #空文件夹
    os.system('git clone ' + git_clone_address +' ' + code_dir)
else:
    #已经clone过
    os.system('git pull')

##########################################################################################
#4. gradle assemble打包命令，需要生成local.properties文件

def createLocalPropertiesFile(sourceDir,fileName,root_sdk_dir):
    if not os.path.exists(sourceDir):
        return

    fileDir = sourceDir + '/' + fileName

    if os.path.exists(fileDir):
        return

    f = open(fileDir,'w');
    f.write('sdk.dir=' + root_sdk_dir)
    f.close()

# 生成local.properties文件
createLocalPropertiesFile(code_dir,'local.properties',Root_SDK_Dir)

# 打包
print('\n')
print('=============================================')
print('gradle clean')
print('=============================================')
os.system('gradle clean');

print('\n')
print('=============================================')
print('gradle assembleDebug, generate apk')
print('=============================================')
os.system('gradle assembleDebug')

##########################################################################################
#5.移动.apk文件到Apk目录，方便查找
def moveFiles(sourceDir,  targetDir):#复制一级目录下的所有文件到指定目录
    if not os.path.exists(sourceDir):
        return;

    for file in os.listdir(sourceDir):
         sourceFile = os.path.join(sourceDir,  file)
         targetFile = os.path.join(targetDir,  file)
         if os.path.isfile(sourceFile) and ('unaligned' not in os.path.basename(sourceFile)):
             open(targetFile,"wb").write(open(sourceFile,"rb").read())

source_apk_dir = code_dir + '/' + 'app/build/outputs/apk'
if os.path.exists(source_apk_dir):
    moveFiles(source_apk_dir,apk_dir)


##########################################################################################
#6. 安装apk
print('\n')
print('=============================================')
print('install apk')
print('=============================================')

print ('现在所在位置： ' + os.getcwd())


def getFileName(sourceDir):
    if not os.path.exists(sourceDir):
        return;

    for filename in os.listdir(sourceDir):
        if '.apk' in filename:
            return filename



apkName = getFileName(apk_dir)
apkPath = apk_dir + '/' + apkName
print(apkPath)

os.system('adb install -r ' + apkPath)

#########################################################################################
# 7.打开APK

# 先关闭该进程
os.system('adb shell am force-stop ' + PackageName)
# 打开该LauncherActivity
os.system('adb shell am start -n '+ PackageName +'/' + PackageName + '.' + LauncherActivity)
