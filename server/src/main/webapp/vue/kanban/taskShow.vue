var show = new Vue({
    el:'#show',
    data:{
        api:{},
        task:{},
        timeLog:[],
        haveActiveTimer:false
    },
    methods:{
        startTimer:function () {
            const self = this;
            PostApi(this.api.startTimer, {task:this.task.id}, function(ans){
                if (ans.status === 'success'){
                    self.haveActiveTimer = true;
                    self.timeLog.push(ans.result);
                }
            });
            console.log('startTimer')
        },
        stopTimer:function (timer) {
            const self = this;
            PostApi(this.api.stopTimer, {id:timer.id}, function (ans) {
                console.log(ans);
                if (ans.status === 'success'){
                    Vue.set(timer, 'end', ans.result.end);
                    self.haveActiveTimer = false;
                }
            })
        },
        addTimer:function(timer){
            this.timeLog.push(
                timer
            );
            if (!timer.end){
                this.haveActiveTimer = true;
            }
        },
        buildTimeRow:function(d){
            let date = new Date(d);
            return date.toLocaleDateString().substring(0, 5) + ' ' + date.toLocaleTimeString();
        },
        calculateSpend:function(b, e){
            let begin = new Date(b);
            let end = new Date(e);
            let spend = Math.ceil((end - begin) / 1000);
            let min = Math.floor(spend / 60);
            let hours = Math.floor(spend / (60 * 60));

            if (hours > 0){
                spend -= min * 60;
            }
            if (min > 0){
                spend -= min * 60;
            }
            return (hours > 0 ? hours.toLocaleString() + ' hours, ' : '') +
            (min > 0 ? min.toLocaleString() + ' min, ' : '') + spend.toLocaleString() + ' sec';
        },
        sortedTimeLog:function(){
            let lst = Object.assign([], this.timeLog);
            return lst.sort(function (a, b) {
                return new Date(b.begin) - new Date(a.begin);
            })
        }
    }
});