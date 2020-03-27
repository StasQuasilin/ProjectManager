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
        select:function(item){
            this.selected = item;
            const self = this;
            if (item.parent == null){
                console.log('Project root');
            }
            PostApi(this.api.getSubs, {parent:item.id}, function(a){
                if (a.status === 'success'){
                    self.items = a.result;
                    self.items.sort(function(a, b){
                        return b.isGroup-(a.isGroup);
                    });
                    if (a.tree){
                        self.tree = a.tree;
                        self.sortTree(self.tree);
                    }
                }
            })
        },
        sortTree:function(tree){
            tree.children.sort(function(a, b){
                let sort = (b.children.length > 0) - (a.children.length > 0);
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
        newTask:function(){
            loadModal(this.api.edit, {parent: this.selected.id});
        }

    }
});
