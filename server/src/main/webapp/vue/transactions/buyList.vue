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
<<<<<<< HEAD
            if (key){
                this.editableItem = key;
                let item = this.items[key];
                this.editableName = item.title;
            } else {
                this.editableItem = 0;
                this.items.unshift({
                    id:-1
                })
            }
            const self = this;
            setTimeout(function () {
                // self.$refs.listName.select;
            }, 50)
=======

            this.editableItem = 0;
            this.items.unshift({
                id:-1
            })
>>>>>>> 52c5a1d19c9129afc96e83fc384e8c2354a91ca9
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
<<<<<<< HEAD
        addElement:function(item){
            let add = true;
            if (item.add){
                add = !item.add;
            }
            Vue.set(item, 'add', add);
        },
        cancelListEdit:function(){
            if (this.editableItem === 0) {
                this.items.splice(this.editableItem, 1);
            }
=======
        cancelListEdit:function(){
            this.items.splice(this.editableItem, 1);
>>>>>>> 52c5a1d19c9129afc96e83fc384e8c2354a91ca9
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