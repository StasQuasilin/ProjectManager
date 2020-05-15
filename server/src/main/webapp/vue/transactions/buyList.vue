var buyList = new Vue({
    el:'#buyList',
    mixins:[list],
    data:function(){
        return{
            items:[],
            editableItem:-1,
            editableName:''
        }
    },
    methods:{
        editList:function (key) {

            this.editableItem = 0;
            this.items.unshift({
                id:-1
            })
        },
        saveList:function(){
            if (this.editableName){
                let item = this.items[this.editableItem];
                item.title = this.editableName;
                const self = this;
                PostApi(this.api.edit, item, function(ans){
                    if (ans.status === 'success'){
                        // self.items.splice(self.editableItem, 1);
                    }
                })
            }
        },
        cancelListEdit:function(){
            this.items.splice(this.editableItem, 1);
            this.editableItem = -1;
            this.editableName = '';
        },
        openItem:function(item){
            let open = true;
            if (item.open){
                open = !item.open;
            }
            Vue.set(item, 'open', open);
        }
    }
});