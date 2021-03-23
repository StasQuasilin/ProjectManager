goalList = new Vue({
    el:'#goalList',
    components:{
        'goal-tile':goalTile
    },
    mixins:[list],
    data:function(){
        return {
            treeView:null,
            props:{
                edit:function (id) {
                    goalList.edit(id)
                },
                tree:function (id) {
                    goalList.openTree(id);
                },
                remove:function(id){
                    goalList.remove(id);
                }
            }
        }
    },
    mounted:function(){
        let items = this.getItems();
        for (let i in items){
            if (items.hasOwnProperty(i)){
                let item = items[i];
                this.openTree(item.titleId);
                break;
            }
        }
    },
    methods:{
        openTree:function(itemId){
            treeView.openTree(itemId);
        }
    }
});