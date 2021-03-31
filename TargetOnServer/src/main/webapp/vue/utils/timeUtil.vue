timeUtil = {
    data:function () {
        return {
            time:0
        }
    },
    computed:{
        timeLength:function () {
            let sec = this.time;
            let min = 0;
            let hours = 0;
            if (sec > 60){
                min = Math.floor(sec / 60);
                sec -= min * 60;
            }
            if (min > 60){
                hours = Math.floor(min / 60);
                min -= hours * 60;
            }
            return {
                hours:hours,
                min:min,
                sec:sec
            }
        }
    },
    methods:{
        prettyNumber:function (val) {
            if (val < 10){
                return '0' + val;
            } else {
                return val.toLocaleString();
            }
        }
    }
};