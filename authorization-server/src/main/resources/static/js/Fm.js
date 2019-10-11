window.String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
window.String.prototype.lTrim = function () {
    return this.replace(/(^\s*)/g, "");
}
window.String.prototype.rTrim = function () {
    return this.replace(/(\s*$)/g, "");
}
if (typeof String.prototype.startsWith != 'function') {
    String.prototype.startsWith = function (str) {
        var reg = new RegExp("^" + str);
        return reg.test(this);
    }
    String.prototype.endsWith = function (str) {
        var reg = new RegExp(str + "$");
        return reg.test(this);
    }
}
if (typeof String.prototype.startWith != 'function') {
    String.prototype.startWith = function (str) {
        var reg = new RegExp("^" + str);
        return reg.test(this);
    }
    String.prototype.endWith = function (str) {
        var reg = new RegExp(str + "$");
        return reg.test(this);
    }
}
if (!window.location.origin) {
    window.location.origin = window.location.protocol + '//' + window.location.host;
}
if (typeof Array.prototype.indexOf != 'function') {
    Array.prototype.indexOf = function (elt /*, from*/) {
        var len = this.length >>> 0;
        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;
        for (; from < len; from++) {
            if (from in this &&
                this[from] === elt)
                return from;
        }
        return -1;
    };
}

var Cookie = {
    set: function (name, value, expiredays, path, domain, secure) {
        var str = name + "=" + escape(value);
        if (!expiredays) {
            expiredays = 7;
        }
        var expires = new Date();
        expires.setDate(expires.getDate() + expiredays);
        str += "; expires=" + expires.toGMTString();
        if (path) {
            str += "; path=" + path;
        } else {
            str += "; path=/";
        }
        if (domain) {
            str += "; domain=" + domain;
        }
        if (secure) {
            str += "; secure";
        }
        document.cookie = str;
    },
    //获取
    get: function (cookieName) {
        var c = document.cookie + ";";
        var re = /\s?(.*?)=(.*?);/g;
        while ((matches = re.exec(c)) != null) {
            if (matches[1] == cookieName) {
                return unescape(matches[2]);
            }
        }
        return "";
    },
    //删除
    del: function (cookieName) {
        var expires = new Date();
        expires.setDate(expires.getDate() - 1); //将expires设为一个过去的日期，浏览器会自动删除它
        document.cookie = cookieName + "=; expires=" + expires.toGMTString();
    }
};

(function (name, context, definition) {
    if (typeof module !== 'undefined' && module.exports) module.exports = definition();
    else if (typeof define === 'function' && define.amd) define(definition);
    else context[name] = definition();
})('urlJoin', this, function () {

    function normalize(strArray) {
        var resultArray = [];

        // If the first part is a plain protocol, we combine it with the next part.
        if (strArray[0].match(/^[^/:]+:\/*$/) && strArray.length > 1) {
            var first = strArray.shift();
            strArray[0] = first + strArray[0];
        }

        // There must be two or three slashes in the file protocol, two slashes in anything else.
        if (strArray[0].match(/^file:\/\/\//)) {
            strArray[0] = strArray[0].replace(/^([^/:]+):\/*/, '$1:///');
        } else {
            strArray[0] = strArray[0].replace(/^([^/:]+):\/*/, '$1://');
        }

        for (var i = 0; i < strArray.length; i++) {
            var component = strArray[i];

            if (typeof component !== 'string') {
                throw new TypeError('Url must be a string. Received ' + component);
            }

            if (component === '') {
                continue;
            }

            if (i > 0) {
                // Removing the starting slashes for each component but the first.
                component = component.replace(/^[\/]+/, '');
            }
            if (i < strArray.length - 1) {
                // Removing the ending slashes for each component but the last.
                component = component.replace(/[\/]+$/, '');
            } else {
                // For the last component we will combine multiple slashes to a single one.
                component = component.replace(/[\/]+$/, '/');
            }

            resultArray.push(component);

        }

        var str = resultArray.join('/');
        // Each input component is now separated by a single slash except the possible first plain protocol part.

        // remove trailing slash before parameters or hash
        str = str.replace(/\/(\?|&|#[^!])/g, '$1');

        // replace ? in parameters with &
        var parts = str.split('?');
        str = parts.shift() + (parts.length > 0 ? '?' : '') + parts.join('&');

        return str;
    }

    return function () {
        var input;

        if (typeof arguments[0] === 'object') {
            input = arguments[0];
        } else {
            input = [].slice.call(arguments);
        }

        return normalize(input);
    };

});

function CommonUtil() {
    this.urlJoin = window.urlJoin;
    /**
     * 前段url占位符处理
     * @param url
     * @returns {*}
     */
    this.getTrueUrl = function (url) {
        if (!url){
            return url;
        }
        return url.replace("{host}",window.location.host).replace("{port}",window.location.port);
    }
    /**
     * 校验强密码规则
     * @param pwd
     * @returns {boolean}
     */
    this.validatePassword = function (pwd) {
        //|| !/[@Html.Raw("`~!@#$%^&*()_+<>?:\"{},.\\/;'[\\]")]/.test(newpwd)
        if ((pwd.length < 8 || pwd.length > 30)
            || !/[0-9]/.test(pwd)
            || !/[a-zA-Z]/.test(pwd)
        ) {
            return false;
        }
        return true;
    }
    /**
     * 校验简易密码规则
     * @param pwd
     * @returns {boolean}
     */
    this.validateSimplePassword = function (pwd) {
        return /^[a-zA-Z0-9_!@#$%^&*,.;':]{1,30}$/.test(pwd);
    }
    /**
     * 校验邮箱格式
     * @param val
     * @returns {*|boolean}
     */
    this.validateEmail = function (val) {
        return /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(val);
    }
    /**
     * 校验手机号
     * @param val
     * @returns {*|boolean}
     */
    this.validateMobileNum = function (val) {
        return /^1[34578]\d{9}$/.test(val);
    }
    /**
     * 校验身份证号
     * @param val
     * @returns {*|boolean}
     */
    this.validateIdCardNo = function (val) {
        return /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(val);
    }
}
window.commonUtil = new CommonUtil();

(function () {
    var ua = navigator.userAgent.toLowerCase(),
        isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
    var Fm = window.Fm = {
        //框架配置
        Config: {
            //框架版本号
            version: 'v3.0',
            clientDate: new Date()
        },
        //浏览器
        Browser: {
            isIE: isIE,
            isIE8: isIE && ua.indexOf("msie") > -1 && parseInt(ua.match(/msie ([\d.]+)/)[1]) === 8.0,
            isIE9: isIE && ua.indexOf("msie") > -1 && parseInt(ua.match(/msie ([\d.]+)/)[1]) === 9.0,
            isIE10: isIE && ua.indexOf("msie") > -1 && parseInt(ua.match(/msie ([\d.]+)/)[1]) === 10.0
        }
    };
})();