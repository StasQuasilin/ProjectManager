var tree = new Vue({
    el: '#tree',
    data:{
        api:{},
        tree:{},
        items:[],
        selected:null,
        props:{}
    },
    methods:{
        handle:function(items){
            console.log(items);
            for (let u in items.update){
                if (items.update.hasOwnProperty(u)){
                    this.update(items.update[u]);
                }
            }
        },
        update:function(item){
            let found = false;
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    if (this.items[i].id === item.id){
                        this.items.splice(i, 1, item);
                    }
                }
            }
            if (!found){
                this.items.push(item);
            }
        },
        remove:function(item){
            loadModal(this.api.remove, {id:item.id});
        },
        select:function(item){
            this.selected = item;
            const self = this;
            PostApi(this.api.getSubs, {parent: item.id}, function (a) {
                if (a.status === 'success') {
                    self.items = a.result;
                    self.items.sort(function (a, b) {
                        return b.isGroup - (a.isGroup);
                    });
                    if (a.tree) {
                        self.tree = a.tree;
                        self.sortTree(self.tree);
                    }
                }
            })
        },
        getStatusNumber:function(status){
            switch (status) {
                case 'active':
                    return 0;
                case 'progressing':
                    return 1;
                case 'done':
                    return 2;
                default:
                    return 3;
            }
        },
        sortTree:function(tree){
            const self = this;
            tree.children.sort(function(a, b){
                let sort = (b.children.length > 0) - (a.children.length > 0);
                if (sort === 0){
                    sort = self.getStatusNumber(a.status) - self.getStatusNumber(b.status);
                }
                if (sort === 0){
                    sort = a.title.localeCompare(b.title);
                }
                return sort;
            });
            for (let i in tree.children){
                if (tree.children.hasOwnProperty(i)){
                    let treeItem = tree.children[i];
                    this.sortTree(treeItem);
                }
            }
        },
        settings:function(task){
            loadModal(this.api.taskSettings, {id:task.id});
        },
        newTask:function(){
            loadModal(this.api.edit, {parent: this.selected.id});
        }
    }
});
