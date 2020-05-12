var edit = new Vue({
    el: '#editor',
    data:{
        api:{
            save:'',
            length:''
        },
        project:{
            title:'',
            begin:new Date().toISOString().substring(0, 10),
            end:new Date().toISOString().substring(0, 10),
            type:'project'

        },
        useEndDate:false,
        budgets:[],
        budgetTypes:[]
    },
    computed:{
        projectLength:function(){
            return '--';
        }
    },
    methods:{
        addMonth:function(count){
            var end = new Date(this.project.end);
            end.setMonth(end.getMonth() + count);
            this.project.end = end.toISOString().substring(0, 10);
        },
        save:function(){
            console.log(this.project);
            if (!this.project.id){
                this.project.id = -1;
            }
            PostApi(this.api.save, this.project, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});