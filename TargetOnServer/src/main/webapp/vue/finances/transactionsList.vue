transactionsList = new Vue({
    el:'#transactions',
    mixins:[list],
    data:function(){
        return {
            useFilter:false
        }
    },
    methods:{
        sort:function(a, b){
            return new Date(b.date) - new Date(a.date);
        }
    }
});