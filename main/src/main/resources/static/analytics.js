(function () {
  var bigData = {
    config: {
      host: "",
      url: "/analytics/api/analytics",
      key: 'shoestp',
      project: 'bigdata'
    },
    init: function () {
      var _that = this
      if (sessionStorage && !sessionStorage[_that.config.key
      + _that.config.project]) {
        sessionStorage[_that.config.key
        + _that.config.project] = document.referrer;
      }
      if (localStorage && localStorage[_that.config.key + "userKey"]) {
        this.data.userInfo = localStorage[_that.config.key + "userKey"]
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
          scroll: _that.ScollPostion(),
          date: new Date()
        })
        if (temp) {
          temp(ev)
        }
      }
      if (sysConfig && sysConfig.user) {
        _that.data.userInfo = {
          id: sysConfig.user.id,
          userName: sysConfig.user.name,
          email: sysConfig.user.name,
        }
      }
      if (!_that.data.userInfo) {
        this.ajax.get(this.config.host + "/api/analytics/device_sign",
            function (data) {
              data = JSON.parse(data)
              _that.cookie.set("__" + _that.config.key + "_UUID", data.result,
                  "d360")
              _that.data.userInfo = {
                userName: data.result
              }
            })
      }
      // window.onbeforeunload = this.send
      window.addEventListener('unload', function (event) {
        _that.data.firstReferrer = sessionStorage[_that.config.key
        + _that.config.project]
        navigator.sendBeacon(_that.config.host + _that.config.url,
            JSON.stringify(_that.data));
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
    }, cookie: {
      get: function (name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)) {
          return unescape(arr[2]);
        } else {
          return null;
        }
      },
      set: function (name, value, time) {
        function getsec(str) {
          var str1 = str.substring(1, str.length) * 1;
          var str2 = str.substring(0, 1);
          if (str2 == "s") {
            return str1 * 1000;
          }
          else if (str2 == "h") {
            return str1 * 60 * 60 * 1000;
          }
          else if (str2 == "d") {
            return str1 * 24 * 60 * 60 * 1000;
          }
        }
        //s20是代表20秒
        //h是指小时，如12小时则是：h12
        //d是天数，30天则：d30
        var strsec = getsec(time);
        var exp = new Date();
        exp.setTime(exp.getTime() + strsec * 1);
        document.cookie = name + "=" + escape(value) + ";expires="
            + exp.toUTCString();
      }
    }
  }
  bigData.init()
})(window)


