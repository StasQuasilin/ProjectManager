taskEdit = new Vue({
    el:'#taskEdit',
    components:{
        'find-input':inputSearch
    },
    data:{
        api:{},
        useDate:false,
        addToBuyList:false,
        task:{
            id:-1,
            parent:{},
            title:'',
            date:new Date().toISOString().substring(0, 10),
            buyList:{}
        },
        status:[],
        statusNames:{},
        props:{
            put:function(parent){
                taskEdit.task.parent = parent;
            }
        },
        buyListProps:{
            put:function (buyList) {
                taskEdit.task.buyList = buyList;
            }
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
            if (!this.useBuyList){
                delete task.buyList;
            }
            PostApi(this.api.save, task, function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }

});