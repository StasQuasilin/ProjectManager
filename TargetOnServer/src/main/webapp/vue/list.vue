let list = {
    data:function () {
        return {
            items:[]
        }
    },
    methods:{
        handler:function(items){
            console.log(items);
        },
        getItems:function () {
            return this.items;
        }
    }
}