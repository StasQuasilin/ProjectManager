taskEdit = new Vue({
    el:'#taskEdit',
    components:{
        'find-input':inputSearch,
        'date-picker':datePicker
    },
    data:{
        api:{},
        useDate:false,
        installDeadline:false,
        addToBuyList:false,
        task:{
            id:-1,
            parent:{},
            title:'',
            date:new Date().toISOString().substring(0, 10),
            deadline:new Date().toISOString().substring(0, 10),
            buyList:{}
        },
        status:[],
        types:[],
        statusNames:{},
        typeNames:{},
        props:{
            put:function(parent){
                taskEdit.task.parent = parent;
            }
        },
        buyListProps:{
            put:function (buyList) {
                taskEdit.task.buyList = buyList;
            }
        },
        dateProps:{
            put:function (date) {
                console.log(date);
                taskEdit.task.date = date;
            }
        },
        deadlineProps:{
            put:function (date) {
                taskEdit.task.deadline = date;
            }
        },
        dependencyProps:{
            put:function (item) {
                taskEdit.task.dependency.push({
                    id:item.id,
                    title:item.title,
                    status:taskEdit.status[0]
                });
                taskEdit.addDependency = false;
            }
        },
        dependent:[],
        principal:[],
        dependency:{},
        addDependency:false
    },
    computed:{
        additionally:function () {
            return {task : this.task.id}
        }
    },
    methods:{
        enableBuyList:function(){
            this.addToBuyList=true;
            if (typeof this.task.buyList === "undefined"){
                if(typeof this.buyList === "undefined"){
                    this.task.buyList = {
                        id:-1,
                        title:''
                    }
                } else {
                    this.task.buyList = Object.assign({}, this.buyList);
                }

            }
        },
        removeDependency:function(idx){
            this.task.dependency.splice(idx, 1);
            this.$forceUpdate();
        },
        save:function () {
            let task = Object.assign({}, this.task);
            if (!this.useDate){
                delete task.date;
            }
            if (!this.installDeadline){
                delete  task.deadline;
            }
            if (task.parent){
                task.parent = task.parent.id;
            }
            if (!this.addToBuyList){
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