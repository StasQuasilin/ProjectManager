goalEdit = new Vue({
   el:'#goalEdit',
    components:{
        'date-picker':datePicker,
        'find-input':inputSearch
    },
    data:{
        api:{},
        useBeginDate:false,
        useEndDate:false,
        currency:[],
        buyList:{},
        useBuyList:false,
        separatedBuyList:true,
        goal:{
            id:-1,
            title:'',
            begin:null,
            end:null,
            budget:0,
            currency:''
        },
        members:[],
        errors:{
            title:false,
            buyList:false
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
        },
        buyListProps:{
            put:function (list) {
                goalEdit.goal.buyList = list;
                goalEdit.buyList = list;
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
        memberList:function(){
            this.saveData(function (a) {
                console.log(a);
                if (a.status === 'success'){
                    loadModal(this.api.goalMembers, {id:this.goal.id}, function (a) {
                        console.log(a)
                    })
                }
            });
        },
        saveData:function(onSuccess){
            if (this.isValid()) {
                let goal = Object.assign({}, this.goal);
                if (!this.useBeginDate) {
                    delete goal.begin;
                    delete goal.end;
                } else if (!this.useEndDate) {
                    delete goal.end;
                }
                if (this.useBuyList){
                    if (!goal.buyList){
                        goal.buyList = {}
                    }
                    goal.buyList.separated = this.separatedBuyList;
                } else {
                    delete goal.buyList;
                }
                const self = this;
                PostApi(this.api.save, goal, function (a) {
                    if (a.status === 'success') {
                        self.goal.id = a.id;
                    }
                    onSuccess(a);
                })
            }
        },
        save:function(){
            this.saveData(function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        },
        isValid:function(){
            let goal = this.goal;
            let errors = this.errors;
            let valid = true;
            if (goal.title === ''){
                errors.title = true;
                valid = false;
            }

            return valid;
        }
    }
});