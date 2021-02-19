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
    methods:{
        openTree:function(itemId){
            treeView.openTree(itemId);
        }
    }
});