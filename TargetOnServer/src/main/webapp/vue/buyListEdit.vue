buyListEdit = new Vue({
    el:'#buyListEdit',
    data:{
        api:{},
        edit:false,
        editableIndex:-1,
        newItem:{},
        list:{
            id:-1,
            title:'',
            items:[]
        }
    },
    methods:{
        editItem:function(index){
            if (index){
                this.newItem = Object.assign({}, this.list.items[index]);
                this.editableIndex = index;
            } else {
                this.newItem = {
                    id:-1,
                    title:'',
                    count:1,
                    price:0
                };
                this.editableIndex = -1;
            }
            this.edit= true;
        },
        addItem:function(){
            let item = Object.assign({}, this.newItem);
            if (this.editableIndex === -1){
                this.list.items.push(item);
            } else {
                this.list.items.splice(this.editableIndex, 1, item);
            }

            this.edit = false;
        },
        removeItem:function(idx){
            this.list.items.splice(idx, 1);
        },
        save:function(){
            if (this.edit){
                this.addItem();
            }
            PostApi(this.api.save, this.list, function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});