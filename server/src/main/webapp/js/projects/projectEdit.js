var edit = new Vue({
    el: '#editor',
    data:{
        api:{
            save:'',
            length:''
        },
        project:{},
        budgets:[],
        budgetTypes:[],
        budgetDescriptions:{}
    },
    computed:{
        projectLength:function(){
            return '--';
        }
    },
    methods:{
        addMonth:function(count){
            var comp = new Date(this.project.complete);
            comp.setMonth(comp.getMonth() + count);
            this.project.complete = comp.toISOString().substring(0, 10);
        },
        save:function(){
            PostApi(this.api.save, this.project, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});