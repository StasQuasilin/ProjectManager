tree = new Vue({
    el:'#tree',
    mixins:[list, pathBuilder],
    data:function(){
        return {
            currentItem:-1,
            root:-1,
            item:null,
            children:[],
            goals:[],
            tree:{},
            props:{
                onOpen:function (item) {
                    tree.getChildren(item.category);
                }
            },
            status:[],
            statusNames:{}
        }
    },
    computed:{
        path:function () {
            return this.getPath(this.item);
        }
    },
    methods:{
        handler:function(data){
            let update = data.update;
            console.log(update);
            let found = false;
            for (let i in this.children){
                if (this.children.hasOwnProperty(i)){
                    let item = this.children[i];
                    console.log(item);
                    if (item.id === update.id){
                        this.children.splice(i, 1, update);
                        found = true;
                        break;
                    }
                }
            }
            if (!found){
                this.children.push(update);
            }
            if (update) {
                if (update.parent == null) {
                    this.goals.push(update);
                } else {
                    this.updateTree(this.tree, update)
                }
            }
        },
        updateTree(parent, item){
            if (parent.id === item.parent.id){
                let found = false;
                for (let i in parent.children){
                    if (parent.children.hasOwnProperty(i)){
                        let child = parent.children[i];
                        if (child.id === item.id){
                            parent.children.splice(i, 1, item);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found){
                    parent.children.push(item);
                }

            } else {
                for (let i in parent.children){
                    if (parent.children.hasOwnProperty(i)){
                        this.updateTree(parent.children[i], item);
                    }
                }
            }
        },
        newTask:function(){
            let params = {};
            if (this.item){
                params.parent = this.item.id;
            }
            this.edit(-1, params);
        },
        getChildren:function(itemId){
            if (itemId) {
                const self = this;
                PostApi(this.api.getChildren, {parent: itemId}, function (a) {
                    self.item = a.item;
                    self.children = a.children;
                    let root = self.root;
                    // self.root = a.root;
                    if (root !== self.root) {
                        self.buildTree();
                    }
                })
            }
        },
        setRoot(root){
            this.root = root.category;
            this.changeRoot();
        },
        changeRoot:function(){
            this.getChildren(this.root);
            this.buildTree();
        },
        buildTree:function(){
            const self = this;
            PostApi(this.api.treeBuilder, {id:this.root}, function (a) {
                console.log(a);
                self.tree = a;
            })
        },
        childrenByStatus:function(status){
            let items = this.children.filter(function (item) {
                return item.status === status;
            });
            items.sort(function (a, b) {
                return a.title.localeCompare(b.title);
            });
            return items;
        },
        timer:function(id){
            loadModal(this.api.timer, {id:id})
        }
    }
});