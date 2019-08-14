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
            console.log("第一次转入网页链接")
            console.log(_that.cookie.get("_" + _that.config.key + "_first_referrer"))
            console.log(sessionStorage[_that.config.key + _that.config.project])
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
                /** 会话ID  */
                _that.data.session = _that.cookie.get("__" + _that.config.key + "_session")
                /** 会话产生时间  */
                _that.data.session_create_time = _that.cookie.get("__" + _that.config.key + "_session_time")
            }
            console.log(_that.data)
        },
        /** 数据  */
        data: {
            /** 标题  */
            "title": document.title,
            /** url  */
            "url": window.location.href,
            /** uri  */
            "uri": window.location.pathname,
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
            "session_create_time": null
        },
        cookie: {
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


