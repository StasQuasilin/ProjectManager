treeView = new Vue({
    el:'#treeView',
    data:{
        api:{},
        tree:{}
    },
    methods:{
        handler:function(a){
            console.log(a);
        },
        openTree:function (itemId) {
            const self = this;
            PostApi(this.api.getItems, {id:itemId}, function (a) {
                self.tree = a;
            })
        }
    }
});