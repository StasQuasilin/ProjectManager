goalEdit = new Vue({
   el:'#goalEdit',
    data:{
        api:{},
        useBeginDate:false,
        useEndDate:false,
        currency:[],
        goal:{
            id:-1,
            title:'',
            begin:new Date().toISOString().substring(0, 10),
            end:new Date().toISOString().substring(0, 10),
            budget:0,
            currency:''
        }
    },
    methods:{
        save:function(){
            let goal = Object.assign({}, this.goal);
            if (!this.useBeginDate){
                delete goal.begin;
                delete goal.end;
            } else if (!this.useEndDate){
                delete goal.end;
            }

            console.log(goal);

            PostApi(this.api.save, goal, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});