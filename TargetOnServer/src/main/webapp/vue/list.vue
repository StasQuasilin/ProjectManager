let list = {
    data:function () {
        return {
            api:{},
            items: {}
        }
    },
    methods:{
        remove:function(id){
            if (this.api.remove && id){
                loadModal(this.api.remove, {id:id})
            }
        },
        edit:function(id, params){
            if (this.api.edit){
                let data = Object.assign({}, params);
                if (id){
                    data.id = id;
                }
                loadModal(this.api.edit, data)
            }
        },
        handler:function(items){
            if(items) {
                for (let a in items.add) {
                    if (items.add.hasOwnProperty(a)) {
                        let item = items.add[a];
                        this.update(item);
                    }
                }
                if (items.update){
                    this.update(items.update);
                }
                if (items.remove){
                    Vue.delete(this.items, items.remove.id);
                }
            }
        },
        update:function(item){
            Vue.set(this.items, item.id, item);
        },

        getItems:function () {
            let values = Object.values(this.items);
            values.sort(this.sort);
            return values;
        },
        sort:function(){
            return false;
        }
    }
};