var edit = new Vue({
    components:{
        'find':findInput
    },
    el:'#editor',
    data:{
        api:{},
        types:[],
        budgets:[],
        currencyList:[],
        transaction:{
            type:'',
            date:new Date().toISOString().substring(0, 10),
            sum:0,
            currency:'',
            category:{
                id:-1,
                name:''
            },
            budget:{
                id:-1
            },
            payer:{
                id:-1
            },
            comment:''
        }
    },
    methods:{
        save:function(){
            var data = Object.assign({}, this.transaction);
            data.budget = data.budget.id;
            if (!data.id){
                data.id = -1;
            }
            PostApi(this.api.save, data, function(a){
                console.log(a);
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});