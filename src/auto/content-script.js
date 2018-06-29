var newId = "";//最新期号
var unit = "元";

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
    //判断当前页是否定位到五星不定位一码位置
    if (smalllabel_2_0.attr("class") == 'method-tab-front') {
        //正在开奖
        var open_time = $("#lt_opentimebox2").css("display");
        //未开盘
        var openTitle = $("#openTitle").css("display");
        //判断当期是否开奖，既不是正在开奖状态也不是未开盘的状态
        if (open_time == 'none' && openTitle == 'none') {
            //请求后台，返回下注号码与对应金额
            var result = chance();

            var flag = false;
            if (result['0'] != undefined && result['0'] != 0) {
                sel_insert(0, result['0']);
                flag = true;
            }
            if (result['9'] != undefined && result['9'] != 0) {
                sel_insert(9, result['9']);
                flag = true
            }
            if (flag) {
                submit();
                $("#alert_close_button").click();
            }
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


function submit() {
    $("#lt_buy").click();
    $("#confirm_yes").click();
    setTimeout(function () {
        $("#alert_close_button").click();
    }, 100);
}

function chance() {
    var result = {};
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
            url: "http://localhost:8080/ssc/chance?kjNumber=" + lastNumber + "&kjStage=" + lastId + "&nextStage=" + currentId,
            async: false,
            success: function (data) {
                result = eval("(" + data + ")");
            }
        });
        console.log("开奖期号:"+lastId+",后台返回信息:" + JSON.stringify(result));
    }
    return result;
}



