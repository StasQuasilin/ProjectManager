var fastTransactions = new Vue({
    el:'#fast',
    mixins:[list],
    data:function(){
        return{

        }
    },
    methods:{
        insert:function(item){
            PostApi(this.api.insertFast, {id:item.id})
        },
        sort:function (a, b) {
            return b.counter - a.counter;
        }
    }
})
