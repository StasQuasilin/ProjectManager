var edit = new Vue({
    components:{
        'find':findInput
    },
    el:'#editor',
    data:{
        api:{},
        repeats:[],
        budgets:[],
        currency:[],
        transaction:{

        }
    }
});