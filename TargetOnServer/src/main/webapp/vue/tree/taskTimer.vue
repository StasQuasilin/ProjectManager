timer = new Vue({
    el:'#timer',
    mixins:[timer],
    components:{
        'find-input':inputSearch
    },
    data:function(){
        return {
            api:{},
            taskProps: {
                put: function (task) {
                    timer.timer.task = task;
                }
            }
        }
    },
    methods:{
        timerStart:function(){
            const self = this;
            PostApi(this.api.timerStart, {task:this.timer.task.id}, function (a) {
                if (a.status === 'success'){
                    self.timer.id = a.id;
                    self.timer.begin = new Date(a.begin);
                    self.updateLength();
                }
            });
        }
    }
}) ;