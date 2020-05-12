/**
 * Created by szpt_user045 on 17.09.2018.
 */
function dateFormatter(date){
    var d = new Date(date);
    var day=d.getDate();
    if (day<10){
        day='0'+day;
    }

    var month=d.getMonth()+1;
    if (month<10){
        month='0'+month;
    }

    return day+'.'+month+'.'+d.getFullYear();
}
function timeFormatter(time, showDays){
    var t = new Date(time);
    if (isNaN(t.getHours())){
        return '--';
    }
    var now = new Date();
    var hour = t.getHours();
    var minute = t.getMinutes();
    if (hour < 10){
        hour='0'+hour;
    }
    if (minute<10){
        minute='0'+minute;
    }
    var d = '';
    if (t.getDate() != now.getDate() || t.getMonth() != now.getMonth()){
        d=(t.getDate() < 10 ? '0' + t.getDate() : t.getDate()) +'.' + ((t.getMonth() + 1) < 10 ? '0' + (t.getMonth()+1) : (t.getMonth()+1)) + ', ';
    }
    return showDays ? d : '' + hour+":"+minute;
}
function updateTime(t, span) {
    var now = new Date();
    var day = now.getDate()-t.getDate();
    var hour = now.getHours()-t.getHours();
    var minute = now.getMinutes()-t.getMinutes();
    var sec = now.getSeconds()-t.getSeconds();
    if (sec < 0){
        sec = 60+sec;
        minute--;
    }
    if(minute < 0) {
        minute= 60+minute;
        hour--;
    }
    if (hour < 0){
        hour=24+hour;
        day--;
    }
    if (hour < 10){
        hour='0'+hour;
    }
    if (minute<10){
        minute='0'+minute;
    }
    if (sec<10){
        sec='0'+sec;
    }
    span.innerText = (day != 0 ? Math.abs(day) + 'д. ' : '') + hour+':'+minute+':'+sec;
    setTimeout(function(){
        updateTime(t, span);
    }, 1000-now.getMilliseconds());
}
function timeDifferent(time, span){
    var t = new Date(time);
    if (isNaN(t.getHours())){
        return;
    }
    updateTime(t, span);
}