buyListEdit = new Vue({
    el:'#buyListEdit',
    data:{
        api:{},
        editItem:false,
        editableItem:-1,
        newItem:{
            id:-1,
            title:'',
            count:1,
            price:0
        },
        list:{
            id:-1,
            title:'',
            items:[]
        }
    },
    methods:{
        addItem:function(){
            this.list.items.push(this.newItem);
            this.editItem = false;
        },
        removeItem:function(idx){
            this.list.items.splice(idx, 1);
        },
        save:function(){
            if (this.editItem){
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