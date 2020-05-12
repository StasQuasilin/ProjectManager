var kanban = new Vue({
    components:{
        board:board
    },
    el:'#kanban',
    data:{
        api:{},
        task:{},
        todo:[],
        inProgress:[],
        done:[],
        todoProps:{},
        processingProps:{},
        doneProps:{}
    },
    methods:{
        sort:function(){

        },
        handle:function(items){
            console.log(items);
            for (let u in items.update){
                if (items.update.hasOwnProperty(u)){
                    this.update(items.update[u]);
                }
            }
            this.sort();
        },
        open:function(task){
            loadModal(this.api.show, {id:task.id})
        },
        update:function(item){

            let list = null;
            if (item.status === 'active'){
                list = this.todo;
            } else if (item.status === 'progressing'){
                list = this.inProgress;
            } else if (item.status === 'done'){
                list = this.done;
            } else {
                console.warn('Some other list type');
            }
            if (list !== null){
                let found = false;
                for (let i in list){
                    if (list.hasOwnProperty(i)){
                        let u = list[i];
                        if (u.id === item.id){
                            found = true;
                            list.splice(i, 1, item);
                        }
                    }
                }
                if (!found){
                    list.push(item);
                }
            }


        },
        editTask:function(task){
            console.log(task);
            if (!task){
                task = {
                    id:-1,
                    title:'New task',
                    status:'active'
                };
                this.todo.push(task);
            } else {
                Vue.set(task, 'edit', true);
            }
        },
        removeTask:function(id){
            loadModal(this.api.removeTask, {id:id})
        },
        saveTask:function(task){
            let t = Object.assign({}, task);
            t.parent = this.task.id;
            delete t.edit;
            if (!t.id){
                t.id = -1;
            }
            PostApi(this.api.saveTask, t, function(a){
                task.id = a.result;
                Vue.set(task, 'edit', false);
            })
        },
        drop:function(item, status){
            PostApi(this.api.changeStatus, {id:item.id, status:status}, function(a){
                //todo fix it;
                item.status = status;
            });
        },
        check:function(task){
            console.log(task)
        },
        log: function(evt) {
            console.log(evt);
        }
    }
});