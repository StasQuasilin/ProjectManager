taskEdit = new Vue({
    el:'#taskEdit',
    data:{
        api:{},
        useDate:false,
        task:{
            id:-1,
            parent:{},
            title:'',
            date:new Date().toISOString().substring(0, 10)
        }
    },
    methods:{
        save:function () {
            let task = Object.assign({}, this.task);
            if (!this.useDate){
                delete task.date;
            }
            if (task.parent){
                task.parent = task.parent.id;
            }
            PostApi(this.api.save, task, function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }

});