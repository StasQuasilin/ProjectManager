var edit = new Vue({
    components:{
        'find':findInput
    },
    el:'#editor',
    data:{
        api:{},
        task:{

        },
        parents:[]
    },
    methods:{
        getPathByParent:function(){
            let param = {
                id:this.task.id
            };
            if (this.task.path.length > 0){
                let parent = this.task.path[this.task.path.length - 1];
                param.parent = parent.id;
            }
            const self = this;
            PostApi(this.api.getSubTask, param, function(ans){
                self.parents = ans.result;
            })
        },
        setParent:function(parent){
            this.task.parent = parent.id;
            this.task.path.push(parent);
            this.clearParents();
        },
        clearParents:function(){
            this.parents = [];
        },
        save:function(){
            PostApi(this.api.save, this.task, function (ans) {
                if(ans.status === 'success'){
                    closeModal();
                }
            });
        },
        removePath:function(id){
            this.task.path.splice(id);
            this.task.parent = this.task.path[this.task.path.length - 1];
        }
    }
});