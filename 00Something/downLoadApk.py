#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import urllib.request
import http.cookiejar
import json
import configparser
import os
import sys


Auto_Config_Path = os.path.dirname(__file__) + '/downLoadApk.config'


cf = configparser.ConfigParser()
cf.read(Auto_Config_Path)
remote_address_path = cf.get('remote','remote_address_path')
password = cf.get('remote','password')


try:
    url = 'http://download.fir.im/' + remote_address_path
    request = urllib.request.Request(url)
    request.add_header('Passwd', password)


    cookie = http.cookiejar.CookieJar()
    opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(cookie))

    # send a request
    response = opener.open(request)

    for item in cookie:
        print('Name = '+item.name)
        print('Value = '+item.value)

    # cookie.save(ignore_discard=True, ignore_expires=True)

    s = response.read().decode('utf-8')

    data = json.loads(s)

    print('================================================')
    print('请求到的数据：')
    print(data['app']['id'])
    print(data['app']['token'])
    print(data['app']['releases']['master']['id'])
    print('================================================')

    appId = data['app']['id']
    downLoadToken = data['app']['token']
    downLoadId = data['app']['releases']['master']['id']

    getApkurl = 'http://download.fir.im/apps/'+appId+'/install?download_token' + downLoadToken + '&release_id=' + downLoadId
    print(getApkurl)


    postdata =urllib.parse.urlencode({
    "download_token":downLoadToken,
    "id":appId,
    "release_id":downLoadId,
    }).encode('utf-8')
    get_response = opener.open(getApkurl, postdata)

    # after 302,use response.geturl() acheving the real apk address,but useless
    apk_download_address = get_response.geturl()
    print('after 302: ' + apk_download_address)

    apkStr = get_response.read().decode('utf-8')
    apkJson = json.loads(apkStr)
    download_url = apkJson['url']
    print('download_url: '+download_url)

    part = download_url.partition('filename')[2]
    app_name = part[1:len(part)]

    def reporthook(a, b, c):    # a:已经下载的数据大小; b:数据大小; c:远程文件大小;
        # if c > 1000000:
        per = (100.0 * a * b) / c
        if per>100: per=100
        sys.stdout.write("%d"%per)
        sys.stdout.write("%\r")
        sys.stdout.flush()

    apkPath = os.path.dirname(__file__) + '/' + app_name
    urllib.request.urlretrieve(download_url, apkPath,reporthook)

    print('================================================')
    print('安装APK')
    print('app_name = ' + app_name)
    print('apkPath: '+ apkPath)
    print('================================================')
    os.system('adb install -r ' + apkPath)

    PackageName = cf.get('app','package_name')
    LauncherActivity = cf.get('app','launcher_activity')

    # 先关闭该进程
    os.system('adb shell am force-stop ' + PackageName)
    # 打开该Launch erActivity
    if PackageName in LauncherActivity:
        os.system('adb shell am start -n '+ PackageName +'/' + LauncherActivity)
    else:
        os.system('adb shell am start -n '+ PackageName +'/' + PackageName + LauncherActivity)



except urllib.request.HTTPError as e:
    print(e.code,e.reason)
except urllib.request.URLError as e:
    print(e.reason)
