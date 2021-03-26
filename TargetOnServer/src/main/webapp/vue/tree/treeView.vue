treeView = new Vue({
    el:'#treeView',
    data:{
        api:{},
        tree:{},
        isSelected:false,
        title:null,
        props:{},
        currentItem:-1
    },
    methods:{
        handler:function(a){
            // console.log(a);
            if(a.update){
                let u = a.update;
                if(!this.update(this.tree, u)){
                    for (let i in this.tree.children){
                        if (this.tree.children.hasOwnProperty(i)){
                            let item = this.tree.children[i];
                            if(this.update(item, u)){
                                break;
                            }
                        }
                    }
                }
            }
            // this.openTree(this.currentItem);
        },
        update:function(item, u){
            if(item.id === u.id){
                Object.assign(item, u);
                this.$forceUpdate();
                return true;
            } else {
                if(item.children) {
                    for (let i in item.children) {
                        if (item.children.hasOwnProperty(i)){
                            let child = item.children[i];
                            if(this.update(child, u)){
                                break;
                            }
                        }
                    }
                }
            }
        },
        onClick:function(itemId){
            loadModal(this.api.taskEdit, {id:itemId});
        },
        openTree:function (itemId) {
            this.currentItem = itemId;
            this.isSelected = true;
            const self = this;
            PostApi(this.api.getItems, {id:itemId}, function (a) {
                self.tree = a;
                self.title = a.title
            })
        }
    }
});