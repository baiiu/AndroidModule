var jsVariables = "这个是js成员变量";

function onSubmit() {
    var wantedUrl = document.getElementById("wantedUrl").value;

    if ("" === wantedUrl) {
        window.injectedObject.error("没有填URL");
        //alert("没有填URL");//需要WebView的context为activity
        return;
    }

    var methods = document.getElementsByName("method");
    var method = "";
    for(var i = 0; i < methods.length; i++){
      var radio = methods[i];
      if(radio.checked){
        //toastShow(radio.value);
        method = radio.value;
        break;
      }
    }

    //window对象可省略不谢
    window.injectedObject.onSubmit(method,wantedUrl);
}

function toastShow(msg) {
    window.injectedObject.toast(msg);
}

function errorShow(msg) {
    window.injectedObject.error(msg);
}

function successShow(msg) {
    window.injectedObject.success(msg);
}

/*
java调用js,设置返回content
*/
function contentShow(content){
    var divElement = document.getElementById("responseBody");
    divElement.innerHTML = content;
}
