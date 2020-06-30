let list = {
    data:function () {
        return {
            items: {}
        }
    },
    methods:{
        handler:function(items){
            console.log(items);
            for (let a in items.add){
                if (items.add.hasOwnProperty(a)){
                    let item = items.add[a];
                    this.update(item);
                }
            }
        },
        update:function(item){
            console.log(item);
            Vue.set(this.items, item.id, item);
        },
        getItems:function () {
            return Object.values(this.items);
        }
    }
}