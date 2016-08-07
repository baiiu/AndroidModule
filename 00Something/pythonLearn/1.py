
#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import os
try:
    import xml.etree.cElementTree as ET
except ImportError:
    import xml.etree.ElementTree as ET


s = '.MainActivity'


s = s[1:len(s)]
print s

# print (cmp('eee','eee'))

# get packageName
# print root.tag, root.attrib, root.get('package')


# getLauncherActivity
# for elem in tree.iter(tag = 'activity'):
#     for childElem in elem:
#         print(childElem.tag, childElem.attrib)
#     # print(elem.tag, elem.attrib, elem.get('{http://schemas.android.com/apk/res/android}name'))





# def findLauncherActivityName(ele,targetString):
#     for childElem in ele:
#         if len(list(childElem)) == 0:
#             print('no child, ' + ele.tag, ele.attrib, childElem.tag, childElem.attrib, childElem.get('{http://schemas.android.com/apk/res/android}name'))
#             if cmp(childElem.get('{http://schemas.android.com/apk/res/android}name'),"'" + targetString +"'"):
#                 print('ture')
#                 return True
#             else:
#                 return False
#         else:
#             # print('has child, ' + childElem.tag, childElem.attrib)
#             return findLauncherActivityName(childElem,targetString)
#
#
#
#
# def getLauncherActivity():
#     tree = ET.ElementTree(file='test.xml')
#
#     root = tree.getroot()
#     packageName = root.get('package')
#
#     for elem in tree.iter(tag = 'activity'):
#         if findLauncherActivityName(elem,'android.intent.action.MAIN'): #if has
#             if(findLauncherActivityName(elem,'android.intent.category.LAUNCHER')):
#                 print(elem.tag, elem.attrib, elem.get('{http://schemas.android.com/apk/res/android}name'))
#                 return packageName,elem.get('{http://schemas.android.com/apk/res/android}name')
#
#     return 'NULL'
#
#
# packageName,launcherActivity = getLauncherActivity()
#
# print(packageName +', ' + launcherActivity)





# xml parser learn
# tree = ET.ElementTree(file='test.xml')
# root = tree.getroot()
#
# print root.tag, root.attrib


# for childRoot in root:
    # print childRoot.tag, childRoot.attrib

# for elem in tree.iter():
#     print(elem.tag, elem.attrib)

# for elem in tree.iter(tag = 'branch'):
#     print(elem.tag, elem.attrib)


# import ConfigParser
#
# cf = ConfigParser.ConfigParser()
# cf.read('/Users/baiiu/Desktop/1.config')
#
#
# a = cf.get('app','Root_SDK_Dir');
# print(a)


# <!-- <?xml version="1.0"?>
# <doc>
#     <branch name="testing" hash="1cdf045c">
#         text,source
#     </branch>
#     <branch name="release01" hash="f200013e">
#         <sub-branch name="subrelease01">
#             xml,sgml
#         </sub-branch>
#     </branch>
#     <branch name="invalid"></branch>
# </doc> -->

# read the config file
# [db]
# db_port = 3306
# db_version = 1.0
#
# db_port = cf.get('db','db_port')
# print db_port
#
# secs = cf.sections()
# print 'sections:', secs, type(secs)
#
#
# opts = cf.options('db')
# print 'options:', opts, type(opts)
#
#
# kvs = cf.items("db")
# print 'db:', kvs
