#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
# import shutil
try:
    import configparser
except ImportError:
    import ConfigParser

try:
    import xml.etree.cElementTree as ET
except ImportError:
    import xml.etree.ElementTree as ET


# 保持auto.py 和 auto.config在同一级目录下
Auto_Config_Path = os.path.dirname(__file__) + '/auto.config'

try:
    cf = configparser.ConfigParser();
    print('python 3')
except Exception as e:
    cf = ConfigParser.ConfigParser();
    print('python 2')


#替换为绝对路径
cf.read(Auto_Config_Path)


Root_SDK_Dir = cf.get('app','Root_SDK_Dir')
git_clone_address = cf.get('app','git_clone_address')
git_branch_name = cf.get('app','git_branch_name')
assembleRelease = cf.getboolean('app','assembleRelease')

# 1.设置目录
base_file_dir = cf.get('dir','base_file_dir')
create_dir_name = cf.get('dir','create_dir_name')
create_code_dir_name = cf.get('optional','create_code_dir_name')
create_apk_dir_name = cf.get('optional','create_apk_dir_name')

file_dir = base_file_dir + '/' + create_dir_name    #/Users/baiiu/Desktop/AndroidApp
code_dir = file_dir + '/' + create_code_dir_name      #/Users/baiiu/Desktop/AndroidApp/SourceCode
apk_dir = file_dir + '/' + create_apk_dir_name      #/Users/baiiu/Desktop/AndroidApp/Apk


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

def removeFileInFirstDir(targetDir):#删除一级目录下的所有文件
    for file in os.listdir(targetDir):
        targetFile = os.path.join(targetDir,  file)
        if os.path.isfile(targetFile):
            os.remove(targetFile)
    # os.remove(targetDir)

if(os.path.exists(apk_dir)):
    removeFileInFirstDir(apk_dir)

if not os.path.exists(apk_dir):
    os.mkdir(create_apk_dir_name)

##########################################################################################
#3. 执行git clone命令

# 进入SourceCode目录下
os.chdir(code_dir)
print('进入SourceCode下： ' + os.getcwd())

print('\n')
print('=============================================')
gitCommandLine = 'git clone ' + git_clone_address + ' ' + code_dir + ' -b ' + git_branch_name
print('git clone or git pull: ')
print(gitCommandLine)
print('=============================================')

if not os.listdir(code_dir):
    #空文件夹
    os.system('git clone ' + git_clone_address + ' ' + code_dir + ' -b ' + git_branch_name)
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
print('gradle assemble, generate apk')
print('=============================================')

if(assembleRelease):
    print('assembleRelease apk')
    os.system('gradle assembleRelease')
else:
    print('assembleDebug apk')
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
# if os.path.exists(source_apk_dir):
#     removeFileInFirstDir(source_apk_dir)

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
print('apkPath: '+ apkPath)

os.system('adb install -r ' + apkPath)

#########################################################################################
# 7.打开APK
def cmp(a, b):
    return (a > b) - (a < b)

#获取PackageName,launcherActivity
def findLauncherActivityName(ele,targetString):
    for childElem in ele:
        if len(list(childElem)) == 0:
            if cmp(childElem.get('{http://schemas.android.com/apk/res/android}name'),"'" + targetString +"'"):
                return True
            else:
                return False
        else:
            # print('has child, ' + childElem.tag, childElem.attrib)
            return findLauncherActivityName(childElem,targetString)

def getLauncherActivity(xmlFileDir):
    tree = ET.ElementTree(file=xmlFileDir)

    root = tree.getroot()
    packageName = root.get('package')

    for elem in tree.iter(tag = 'activity'):
        if findLauncherActivityName(elem,'android.intent.action.MAIN'): #if has
            if(findLauncherActivityName(elem,'android.intent.category.LAUNCHER')):
                return packageName,elem.get('{http://schemas.android.com/apk/res/android}name')

    return 'NULL'

PackageName,LauncherActivity = getLauncherActivity(code_dir + '/app/src/main/AndroidManifest.xml')

print (PackageName + '  ' + LauncherActivity)

# PackageName = cf.get('app','PackageName')
# LauncherActivity = cf.get('app','LauncherActivity')

# 先关闭该进程
os.system('adb shell am force-stop ' + PackageName)
# 打开该LauncherActivity
if PackageName in LauncherActivity:
    os.system('adb shell am start -n '+ PackageName +'/' + LauncherActivity)
else:
    os.system('adb shell am start -n '+ PackageName +'/' + PackageName + LauncherActivity)
