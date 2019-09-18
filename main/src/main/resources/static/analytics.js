(function () {

    var bigData = {
        /** 相关配置参数  */
        config: {
            /** 域名地址  */
            host: "",
            // host: "/analytics",
            /** 后台保存数据地址  */
            url: "/api/analytics",
            /** key名称  */
            key: 'shoestp',
            /** 工程名称  */
            project: 'bigdata',
            /** 页面超时时间  */
            timeout: 15,
            issend: true
        },
        /** 初始化数据  */
        init: function () {
            var _that = this
            /** 获取用户第一次referrer进来的地址  */
            if (sessionStorage && !sessionStorage[_that.config.key
            + _that.config.project]) {
                if (document.referrer) {
                    document.referrer = ''
                }
                sessionStorage[_that.config.key
                + _that.config.project] = document.referrer;
                _that.cookie.set("_" + _that.config.key + "_first_referrer", document.referrer, "s900")
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
                if (_that.data.mouseClick.length > 20) {
                    _that.data.mouseClick.splice(0, 1)
                }
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
            if (window.sysConfig && window.sysConfig.user) {
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
                _that.data.session = _that.cookie.get("__" + _that.config.key + "_session")
                _that.data.session_create_time = _that.cookie.get("__" + _that.config.key + "_session_time")
            }
            /** 如果没有唯一标识获取用户唯一标识 */
            if (!_that.data.userInfo || !_that.data.userInfo.userName) {
                console.log("请求签名")
                this.ajax.get(this.config.host + "/api/analytics/device_sign",
                    function (data) {
                        data = JSON.parse(data)
                        /** 设置会话ID及会话创建时间,但是不设置过期时间,默认关闭浏览器就过期  */
                        _that.cookie.set("__" + _that.config.key + "_session", data.result.session)
                        var date = Number(new Date());
                        _that.cookie.set("__" + _that.config.key + "_session_time", date)
                        _that.cookie.set("__" + _that.config.key + "_UUID", data.result.sign,
                            "d360")
                        _that.data.userInfo = {
                            userName: data.result.sign
                        }
                        _that.data.session = data.result.session
                        _that.data.session_create_time = date
                    })
            }
            /** 添加事件  */
            window.addEventListener('unload', function (event) {
                _that.data.firstReferrer = sessionStorage[_that.config.key
                + _that.config.project]
                /** 关闭浏览器时发送数据
                 *  浏览器将 Beacon 请求排队让它在空闲的时候执行并立即返回控制
                 *  它在unload状态下也可以异步发送，不阻塞页面刷新/跳转等操作。
                 * */
                if (_that.config.issend) {
                    _that.data.time_on_page = new Date() - window.performance.timing.domComplete
                    navigator.sendBeacon(_that.config.host + _that.config.url,
                        JSON.stringify(_that.data));
                }
            });
            /** TODO 2019-08-05 15:43
             *  添加超时发送数据
             */
            var t = setTimeout(function () {
                _that.data.firstReferrer = sessionStorage[_that.config.key
                + _that.config.project]
                _that.data.time_on_page = new Date() - window.performance.timing.domComplete
                _that.ajax.post(_that.config.host + _that.config.url,
                    JSON.stringify(_that.data))
                _that.cookie.set("__" + _that.config.key + "_session", null, -1)
                _that.cookie.set("__" + _that.config.key + "_session_time", null, -1)
                clearTimeout(t)
                _that.config.issend = false
            }, _that.config.timeout * 60 * 1000)

        },
        /** 数据  */
        data: {
            /** 标题  */
            "title": document.title,
            /** url  */
            "url": window.location.href,
            /** uri  保留参数*/
            "uri": window.location.href.replace(window.location.protocol + "//" + window.location.host, ""),
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
            /** {
                userId: null,
                userName: null,
                email: null
            }  */
            "userInfo": null,
            /** 会话Id  */
            "session": null,
            /** 会话创建时间  */
            "session_create_time": null,
            /** 页面停留时间  */
            time_on_page: 0
        },
        saveData: function () {

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
            set: function (name, value, time, path) {
                function getsec(str) {
                    if (!str) return null
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
                var _t = ""
                if (strsec) {
                    var exp = new Date();
                    exp.setTime(exp.getTime() + strsec * 1);
                    _t = ";expires=" + exp.toUTCString();
                }
                if (!path) {
                    path = ";path=/"
                } else {
                    path = ";path=" + path
                }
                console.log(name + "=" + escape(value) + path + _t)
                document.cookie = name + "=" + escape(value) + path + _t
            }
        }
    }
    bigData.init()
})(window)


