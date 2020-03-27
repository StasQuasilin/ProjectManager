var edit = new Vue({
    el:'#editor',
    data:{
        api:{},
        parents:[],
        task:{
            id:-1,
            title:'',
            date:-1,
            end:-1,
            length:1,
            parent:{
                id:-1,
                title:''
            },
            description:''
        },
        editDescription:false
    },
    methods:{
        plusLength:function(val){
            let length = this.task.length;
            length += val;
            if(length <= 0){
                length = 1;
            }
            this.task.length = length;
            this.checkEnd();
        },
        checkLength:function(){
            let val = parseInt(this.task.length);
            if(isNaN(val)){
                val = 1;
            }
            this.task.length = val;
            this.checkEnd();
        },
        checkEnd:function(){
            let date = new Date(this.task.date);
            date.setHours(date.getHours() + this.task.length);
            this.task.end = date.toISOString();
        },
        save:function(){
            let data = Object.assign({}, this.task);
            data.date = data.date.toLocaleString();
            data.end = data.end.toLocaleString();
           PostApi(this.api.save, data, function(a){
               if (a.status === 'success'){
                   closeModal();
               }
           })
        }
    }
});