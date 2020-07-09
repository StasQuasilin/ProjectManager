accountEdit = new Vue({
    el:'#accountEdit',
    data:{
        api:{},
        types:[],
        currency:[],
        account:{
            id:-1,
            title:'',
            type:null,
            sum:0,
            currency:null,
            limit:0
        }
    },
    methods:{
        save:function(){
            PostApi(this.api.save, this.account, function(a){
                if(a.status === 'success'){
                    closeModal();
                }
            })
        },
        sort:function(a, b){
            return a.title.localeCompare(b.title);
        }
    }
});