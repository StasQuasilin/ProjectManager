let timer = {
    data:{

        timer:{
            id:-1,
            task:{
                id:-1,
                title:''
            },
            begin:null,
            length:0
        }
    },
    computed:{
        beginDate:function(){
            return new Date(this.timer.begin);
        },
        timerLength:function () {
            let sec = Math.floor(this.timer.length / 1000);
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
        updateLength:function(){
            let date = new Date();
            this.timer.length = (date - this.beginDate);
            const self = this;
            setTimeout(function () {
                self.updateLength();
            }, 1000 - date.getMilliseconds())
        },
        timerStop:function(){
            const self = this;
            PostApi(this.api.timerStop, {id:this.timer.id}, function (a) {
                if (a.status === 'success') {
                    self.timer.begin = null;
                    self.timer.length = 0;
                }
            })
        },
        prettyNumber:function (val) {
            if (val < 10){
                return '0' + val;
            } else {
                return val.toLocaleString();
            }
        }
    }
};