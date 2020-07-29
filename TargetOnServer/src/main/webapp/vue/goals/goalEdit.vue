goalEdit = new Vue({
   el:'#goalEdit',
    components:{
        'date-picker':datePicker
    },
    data:{
        api:{},
        useBeginDate:false,
        useEndDate:false,
        currency:[],
        goal:{
            id:-1,
            title:'',
            begin:null,
            end:null,
            budget:0,
            currency:''
        },
        beginDateProps:{
            put:function(date){
                goalEdit.goal.begin = date;
            }
        },
        endDateProps:{
            put:function (date) {
                goalEdit.goal.end = date;
            }
        }
    },
    computed:{
        goalDuration:function(){
            console.log('Calculate duration...');
            let res = {};
            let a = new Date(this.goal.begin);
            let b = new Date(this.goal.end);
            let year = b.getFullYear() - a.getFullYear();
            let month = (year * 12 + b.getMonth() - a.getMonth());
            year = Math.floor(month / 12);
            month -= year * 12;
            res.year = year;
            res.month = month;

            return res;
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