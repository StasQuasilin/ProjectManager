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
                console.log(ans);
                if (ans.status === 'success'){
                    self.timeLog.push(ans.result);
                }
            });
            console.log('startTimer')
        },
        stopTimer:function (timer) {
            const self = this;
            PostApi(this.api.stopTimer, {id:timer.id}, function (ans) {
                if (ans.status === 'success'){
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
            return date.toLocaleDateString().substring(0, 5) + ' ' + date.toLocaleTimeString().substring(0, 5);
        },
        calculateSpend:function(b, e){
            let begin = new Date(b);
            let end = new Date(e);
            return Math.ceil((end - begin) /  60 / 1000);
        }
    }
});