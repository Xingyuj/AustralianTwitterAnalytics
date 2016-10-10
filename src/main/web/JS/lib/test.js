$(document).ready(function(){
    setInterval("ajaxRequest()", 5000);
});

function ajaxRequest() {
    $.ajax({
        url: "http://localhost:8080/getNewTweet",
        type: "Get",
        dataType: "json",
        async: false,
        data: {
            currentTime: new Date().getTime()
        },
        success: function(data) {
            //arr = eval(data);
            //for(var i =0; i < arr.length; i++) {
            //    alert(arr[i].text + " " + arr[i].location);
            //}
            //console.log("go");
            console.log(data.list);

        },
        error: function() {
            alert("error");
        }
    });
}