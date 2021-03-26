goalList = new Vue({
    el:'#goalList',
    components:{
        'goal-tile':goalTile
    },
    mixins:[list],
    data:function(){
        return {
            openNow:-1,
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

    },
    methods:{
        openTree:function(itemId){
            this.openNow = itemId;
            treeView.openTree(itemId);
        },
        afterHandle:function () {
            if (this.openNow === -1) {
                let items = this.getItems();
                for (let i in items) {
                    if (items.hasOwnProperty(i)) {
                        let item = items[i];
                        this.openTree(item.titleId);
                        break;
                    }
                }
            }
        }
    }
});