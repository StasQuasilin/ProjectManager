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
            this.checkActivity();
        },
        checkActivity:function(){
            let items = this.getItems();
            for(let i in items){
                if(items.hasOwnProperty(i)){
                    let item = items[i];
                    item.active = item.titleId === this.openNow;
                }
            }
        },
        afterHandle:function () {
            if (this.openNow === -1) {
                let items = this.getItems();
                let open = true;
                for (let i in items) {
                    if (items.hasOwnProperty(i)) {
                        let item = items[i];
                        if(item.active){
                            this.openTree(item.titleId);
                            open = false;
                            break;
                        }
                    }
                }
                if(open){
                    this.openTree(items[0].titleId);
                }
            } else {
                this.checkActivity();
            }
        }
    }
});