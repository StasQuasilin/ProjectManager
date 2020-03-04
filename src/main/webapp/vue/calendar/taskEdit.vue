var edit = new Vue({
    el:'#editor',
    data:{
        api:{},
        parents:[],
        task:{
            id:-1,
            title:'',
            date:-1,
            deadline:-1,
            parent:-1,
            description:''
        },
        editDescription:false
    },
    methods:{
        save:function(){
           PostApi(this.api.save, this.task, function(a){
               if (a.status === 'success'){
                   closeModal();
               }
           })
        }
    }
});