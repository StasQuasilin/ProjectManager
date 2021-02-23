treeView = new Vue({
    el:'#treeView',
    data:{
        api:{},
        tree:{},
        isSelected:false,
        title:null,
        props:{}
    },
    methods:{
        handler:function(a){
            console.log(a);
            if (a.update){
                let update = a.update;
                let p = update.parent;

                let parent = this.tree;
                for (let pi in p.path){
                    if (p.path.hasOwnProperty(pi)){
                        let pId = p.path[pi].id;
                        parent = parent.children[pId];
                    }
                }
                parent.children[update.id] = update;
                this.$forceUpdate();
            }
        },
        onClick:function(itemId){
            loadModal(this.api.taskEdit, {id:itemId});
        },
        openTree:function (itemId) {
            this.isSelected = true;
            const self = this;
            PostApi(this.api.getItems, {id:itemId}, function (a) {
                self.tree = a;
                self.title = a.title
            })
        }
    }
});