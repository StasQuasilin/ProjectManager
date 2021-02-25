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
        handler:function(){
            console.log('-->');
            this.openTree(this.currentItem);
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