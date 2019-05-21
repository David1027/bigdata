(function () {
  var bigData = {
    config: {
      url: "http://127.0.0.1/api/analytics"
    },
    init: function () {
      var _that = this
      if (sessionStorage && !sessionStorage["bigdata"]) {
        sessionStorage["bigdata"] = document.referrer;

        console.log(sessionStorage && !sessionStorage["bigdata"])
      }
      if (localStorage && localStorage['shoestp_userInfo']) {
        this.data.userInfo = localStorage['shoestp_userInfo']
      }
      var temp = null
      if (document.onmousedown) {
        temp = document.onmousedown
      }
      document.onmousedown = function (ev) {
        var oEvent = ev || event;
        _that.data.mouseClick.push({
          x: oEvent.clientX,
          y: oEvent.clientY,
          scroll: _that.ScollPostion()
        })
        if (temp) {
          temp(ev)
        }
      }
      // window.onbeforeunload = this.send
      window.addEventListener('unload', function (event) {
        _that.data.firstReferrer = sessionStorage["bigdata"]
        navigator.sendBeacon(_that.config.url, JSON.stringify(_that.data));
      });

    },
    data: {
      "title": document.title,
      "url": window.location.href,
      "firstReferrer": sessionStorage["bigdata"],
      "pageReferrer": document.referrer,
      "pageLoadInfo": window.performance.timing,
      "mouseClick": [],
      "windows": {
        height: window.screen.height,
        width: window.screen.width,
      },
      "userInfo": null
    },
    ScollPostion: function () {
      var t, l, w, h;
      if (document.documentElement && document.documentElement.scrollTop) {
        t = document.documentElement.scrollTop;
        l = document.documentElement.scrollLeft;
        w = document.documentElement.scrollWidth;
        h = document.documentElement.scrollHeight;
      } else if (document.body) {
        t = document.body.scrollTop;
        l = document.body.scrollLeft;
        w = document.body.scrollWidth;
        h = document.body.scrollHeight;
      }
      return {
        top: t,
        left: l,
        width: w,
        height: h
      };
    },
    ajax: {
      get: function (url, fn) {
        // XMLHttpRequest对象用于在后台与服务器交换数据
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.onreadystatechange = function () {
          // readyState == 4说明请求已完成
          if (xhr.readyState == 4 && xhr.status == 200 || xhr.status == 304) {
            // 从服务器获得数据
            fn.call(this, xhr.responseText);
          }
        };
        xhr.send();
      },
      // datat应为'a=a1&b=b1'这种字符串格式，在jq里如果data为对象会自动将对象转成这种字符串格式
      post: function (url, data, fn, ContentType) {
        if (!ContentType) {
          ContentType = "application/x-www-form-urlencoded"
        }
        var xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        // 添加http头，发送信息至服务器时内容编码类型
        xhr.setRequestHeader("Content-Type", ContentType);
        xhr.onreadystatechange = function () {
          if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 304)) {
            fn.call(this, xhr.responseText);
          }
        };
        xhr.send(data);
      }
    }
  }
  bigData.init()
})()
