var history = [];//历史五期数据
var flag = false;//当期是否预测
var newId = "";//最新期号

var unit = "分";
var startInput = 50;
var secondInput = Math.round(50 * 2.718);
var thirdInput = Math.round(50 * 6.671);

$(function () {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/newId",
        async: false,
        success: function (data) {
            newId = data;
            console.log("newId：" + data);
        }
    });
});
setInterval(function () {
    var smalllabel_2_0 = $("#smalllabel_2_0");
    if (smalllabel_2_0.attr("class") == 'method-tab-front') {
        var info = chance();
        var flag = false;
        if (info['0'] != undefined && info['0'] != 0) {
            sel_insert(0, info['0']);
            flag = true;
        }
        if (info['9'] != undefined && info['9'] != 0) {
            sel_insert(9, info['9']);
            flag = true
        }
        if (flag) {
            submit();
            $("#alert_close_button").click();
        }

    }
}, 10000);


function sel_insert(num, money) {
    //选中一个数
    $("#wei__" + num).click();
    //选择单位
    $("ul.choose-money").find("li").each(function () {
        if ($(this).html() == unit) {
            $(this).click();
        }
    });
    //一注金额
    $("#lt_sel_times").val(money);
    //添加注单
    $("#lt_sel_insert").click();
}

/**
 * 获取历史五期数据
 */
function getHistory() {
    $("#gd-box2").find("p").each(function (index) {
        var json = {"id": "", "number": ""};
        $(this).find("span").each(function () {
            if ($(this).attr("class") == 'gd-box-q') {
                json["id"] = $(this).html();
            } else {
                json["number"] += $(this).html();
            }
        });
        history[index] = json;
    });
}

function submit() {
    $("#lt_buy").click();
    $("#confirm_yes").click();
    setTimeout(function () {
        $("#alert_close_button").click();
    },100);
}

function chance() {
    var open_time = $("#lt_opentimebox2").css("display");
    var openTitle=$("#openTitle").css("display");
    //判断当期是否开奖
    var info = {};
    if (open_time == 'none'&&openTitle=='none') {
        var currentId = $("#current_issue").html();//待开奖期数
        if (newId != currentId) {
            //开奖了
            newId = currentId;
            var lastId = $("#lt_gethistorycode").html();//开奖期号
            var lastNumber = [];//开奖号码
            $("#showcodebox").find("li").each(function (index) {
                lastNumber[index] = $(this).html();
            });

            $.ajax({
                type: "get",
                url: "http://localhost:8080/chance?kjNumber=" + lastNumber + "&kjStage=" + lastId + "&nextStage=" + currentId,
                async: false,
                success: function (data) {
                    console.log("历史预测信息：" + data);
                    info = data;
                }
            });
            console.log("info:" + info);
        }
    }
    return info;
}

/**
 * 预测
 */
function forecast() {

    var open_time = $("#lt_opentimebox2").css("display");
    //判断当期是否开奖
    if (open_time == 'none') {
        var currentId = $("#current_issue").html();//当期期数
        if (newId != currentId) {
            //开奖了
            newId = currentId;
            var lastId = $("#lt_gethistorycode").html();//上期期号
            var lastNumber = [];//上期号码
            $("#showcodebox").find("li").each(function (index) {
                lastNumber[index] = $(this).html();
            });
            console.log("lastNumber:" + lastNumber);
            //获取历史数据
            getHistory();
            //分析历史预测
            var chance1 = historyForecast(lastNumber.toString(), currentId);
            //当前预测
            var chance2 = currentForecast(lastNumber, currentId, lastId);

            //提交
            if (chance1 || chance2) {
                submit();
            }
        }
    }
}

/**
 * 当期预测
 * @param lastNumber 最新开奖号码
 * @param currentId 当期待开奖期数
 */
function currentForecast(lastNumber, currentId, lastId) {

    var chance = false;
    var first1 = '';
    var first2 = '';
    var first3 = '';
    if (history[0]["id"] == lastId) {
        first1 = history[1]["number"];
        first2 = history[2]["number"];
        first3 = history[3]["number"];
    } else {
        first1 = history[0]["number"];
        first2 = history[1]["number"];
        first3 = history[2]["number"];
    }
    var forecastNumber = "";
    for (var i = 0; i < lastNumber.length; i++) {
        //去重
        if (forecastNumber.indexOf(lastNumber[i]) != -1) {
            continue;
        }
        //前三期都未出现
        if (first1.indexOf(lastNumber[i]) == -1 && first2.indexOf(lastNumber[i]) == -1
            && first3.indexOf(lastNumber[i]) == -1) {
            forecastNumber += lastNumber[i];
        }
    }
    console.log("第一次预测信息:" + forecastNumber);
    //有机会
    if (forecastNumber != "") {
        chance = true;
        //将预测信息更新到数据库
        $.ajax({
            type: "get",
            url: "http://localhost:8080/ssc/currentForecast?info=" + currentId + "," + forecastNumber,
            async: false,
            success: function () {
                //开始下注
                var numArr = forecastNumber.split('');
                for (var i = 0; i < numArr.length; i++) {
                    sel_insert(numArr[i], startInput);
                }

            }
        });
    }
    return chance;
}

/**
 * 历史预测
 * @param lastNumber 最新已开奖号码
 * @param lastId 最新已开奖期数
 */
function historyForecast(lastNumber, currentId) {
    var chance = true;
    $.ajax({
        type: "get",
        url: "http://localhost:8080/ssc/historyForecast?lastNumber=" + lastNumber + "&currentId=" + currentId,
        async: false,
        success: function (data) {
            console.log("历史预测信息：" + data);
            data = eval(data);
            if (data.length < 1) {
                chance = false;
            }
            //有机会
            //开始下注
            for (var i = 0; i < data.length; i++) {
                var num = data[i]['figure'];
                var time = data[i]['time'];
                if (time == 2) {
                    sel_insert(num, secondInput);
                } else if (time == 3) {
                    sel_insert(num, thirdInput);
                } else {
                    chance = false;
                }
            }

        }
    });

    return chance;
}


