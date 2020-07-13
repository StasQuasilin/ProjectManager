goalEdit = new Vue({
   el:'#goalEdit',
    data:{
        api:{},
        useBeginDate:false,
        useEndDate:false,
        goal:{
            id:-1,
            title:'',
            dateBegin:new Date().toISOString().substring(0, 10),
            dateEnd:new Date().toISOString().substring(0, 10)
        }
    },
    methods:{
        save:function(){
            let goal = Object.assign({}, this.goal);
            if (!this.useBeginDate){
                delete goal.dateBegin;
                delete goal.dateEnd;
            } else if (!this.useEndDate){
                delete goal.dateEnd;
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