(function () {

    var bigData = {
        /** 相关配置参数  */
        config: {
            /** 域名地址  */
            host: "",
            /** 后台保存数据地址  */
            url: "/analytics/api/analytics",
            /** key名称  */
            key: 'shoestp',
            /** 工程名称  */
            project: 'bigdata',
            /** 页面超时时间  */
            timeout: 15
        },
        /** 初始化数据  */
        init: function () {
            var _that = this
            /** 获取用户第一次referrer进来的地址  */
            if (sessionStorage && !sessionStorage[_that.config.key
            + _that.config.project]) {
                sessionStorage[_that.config.key
                + _that.config.project] = document.referrer;
            }
            /** 再入用户标识  */
            if (localStorage && localStorage[_that.config.key + "userKey"]) {
                this.data.userInfo = localStorage[_that.config.key + "userKey"]
            }
            var temp = null
            /** 查看onmousedown方法是否被占用  */
            if (document.onmousedown) {
                temp = document.onmousedown
            }
            /** 注册发送事件 */
            document.onmousedown = function (ev) {
                var oEvent = ev || event;
                _that.data.mouseClick.push({
                    x: oEvent.clientX,
                    y: oEvent.clientY,
                    scroll: _that.ScollPostion(),
                    date: new Date()
                })
                /** 执行原有的事件 */
                if (temp) {
                    temp(ev)
                }
            }
            /** 判断是否登录用户 */
            /** NOTE 2019-08-05 15:41
             *  单页应用可能要在Window对象下面手动挂载
             */
            if (sysConfig && sysConfig.user) {
                _that.data.userInfo = {
                    id: sysConfig.user.id,
                    userName: sysConfig.user.name,
                    email: sysConfig.user.name,
                }
            }
            /** 查看用户唯一标识 */
            if (_that.cookie.get("__" + _that.config.key + "_UUID")) {
                _that.data.userInfo = {
                    userName: _that.cookie.get("__" + _that.config.key + "_UUID")
                }
            }
            /** 如果没有唯一标识获取用户唯一标识 */
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
            /** 添加事件  */
            window.addEventListener('unload', function (event) {
                _that.data.firstReferrer = sessionStorage[_that.config.key
                + _that.config.project]
                /** 关闭浏览器时发送数据  */
                navigator.sendBeacon(_that.config.host + _that.config.url,
                    JSON.stringify(_that.data));
            });
            /** TODO 2019-08-05 15:43
             *  添加超时发送数据
             */

        },
        /** 数据  */
        data: {
            /** 标题  */
            "title": document.title,
            /** url  */
            "url": window.location.href,
            /** 首次跳入的ref  */
            "firstReferrer": sessionStorage["bigdata"],
            /** 页面跳入的ref  */
            "pageReferrer": document.referrer,
            /** 页面请求加载的一些统一时间  */
            "pageLoadInfo": window.performance.timing,
            /**   */
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
                    } else if (str2 == "h") {
                        return str1 * 60 * 60 * 1000;
                    } else if (str2 == "d") {
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


