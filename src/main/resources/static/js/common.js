// 公共的 Ajax JSON POST 请求封装
function postJson(url, data, successCallback, errorCallback) {
    $.ajax({
        url: url,
        type: "post",
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: "json",
        success: successCallback,
        error: errorCallback
    });
} 