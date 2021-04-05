buyListEdit = new Vue({
    el:'#buyListEdit',
    components:{
        'date-picker':datePicker
    },
    data:{
        api:{},
        edit:false,
        editableIndex:-1,
        newItem:{},
        list:{
            id:-1,
            title:'',
            items:[]
        },
        detailMode:false,
        dateProps:{
            put:function (date) {
                buyListEdit.newItem.date = date;
                buyListEdit.$forceUpdate();
            }
        }
    },
    methods:{
        editItem:function(index){
            this.newItem = Object.assign({}, this.list.items[index]);
            this.editableIndex = index;
            this.edit= true;
        },
        addDate:function(){
            this.newItem.date = new Date().toISOString().substring(0, 10);
            this.$forceUpdate();
        },
        removeDate:function(){
            delete this.newItem.date;
            this.$forceUpdate();
        },
        addItem:function(){
            let item = Object.assign({}, this.newItem);
            if (item.title.length > 0) {
                if (this.editableIndex === -1) {
                    this.list.items.push(item);
                } else {
                    this.list.items.splice(this.editableIndex, 1, item);
                }

                this.clearAdd();
            }
        },
        clearAdd:function(){
            this.newItem.title='';
            this.edit=false;
            this.editableIndex=-1;
        },
        removeItem:function(idx){
            this.list.items.splice(idx, 1);
        },
        selectDateFor:function(){
            datePicker.shwo
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