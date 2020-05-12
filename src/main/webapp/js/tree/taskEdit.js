var edit = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        task:{}
    },
    methods:{
        save:function(){
            PostApi(this.api.save, this.task, function(a){
                if(a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});