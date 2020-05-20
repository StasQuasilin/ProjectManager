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
            type:'project',
            cost:0
        },
        withPeriod:true,
        withCost:false,
        useEndDate:true,
        years:{},
        months:{}
    },
    computed:{
        projectLength:function(){
            let res = {};
            let a = new Date(this.project.begin);
            let b = new Date(this.project.end);

            res.years = b.getFullYear() - a.getFullYear();
            res.month = (res.years * 12 + b.getMonth() - a.getMonth()) % 12;

            return res;
        }
    },
    methods:{
        addMonth:function(count){
            let end = new Date(this.project.end);
            end.setMonth(end.getMonth() + count);
            this.project.end = end.toISOString().substring(0, 10);
        },
        save:function(){
            console.log(this.project);
            let data = Object.assign({}, this.project);
            if (!data.id){
                data.id = -1;
            }
            if (!this.withPeriod){
                delete data.begin;
                delete data.end;
            } else if (!this.useEndDate){
                delete data.end;
            }
            PostApi(this.api.save, data, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});