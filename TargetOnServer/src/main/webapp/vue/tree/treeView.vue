treeView = new Vue({
    el:'#treeView',
    data:{
        api:{},
        tree:{},
        isSelected:false,
        title:null,

    },
    methods:{
        handler:function(a){
            console.log(a);
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