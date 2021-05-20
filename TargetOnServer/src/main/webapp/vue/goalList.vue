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
            },
            statusNames:{},
            status:'done'
        }
    },
    computed:{
        statusList:function () {
            let list = [
                'all'
            ];
            let items = this.getItems();
            let activeStatus;
            for(let i in items){
                if (items.hasOwnProperty(i)){
                    let item = items[i];
                    let status = item.status;
                    if (!list.includes(status)){
                        list.push(status);
                    }
                    if (item.active){
                        activeStatus = item.status;
                    }
                }
            }
            if (activeStatus){
                this.status = activeStatus;
            } else if (!list.includes(this.status)){
                this.status = list[0];
            }
            return list;
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
        filteredItems:function(){
            if (this.status === 'all'){
                return this.getItems();
            } else {
                const self = this;
                return this.getItems().filter(function (a) {
                    return a.status === self.status;
                })
            }
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