import urllib.request
import http.cookiejar
import json

try:
    url = 'http://download.fir.im/159600'
    request = urllib.request.Request(url)
    request.add_header('Passwd','baiiu')


    cookie = http.cookiejar.MozillaCookieJar()
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

    # after 302,use response.geturl() acheving the real apk address
    apk_download_address = get_response.geturl()
    print('after 302: ' + apk_download_address)

    apkStr = get_response.read().decode('utf-8')
    apkJson = json.loads(apkStr)
    download_url = apkJson['url']
    print('download_url: '+download_url)


    urllib.request.urlretrieve(download_url,'/Users/baiiu/Desktop/1.apk')








except urllib.request.HTTPError as e:
    print(e.code,e.reason)
except urllib.request.URLError as e:
    print(e.reason)
