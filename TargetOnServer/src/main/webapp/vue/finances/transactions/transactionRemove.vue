remover = new Vue({
    el:'#remover',
    data:{
        api:{},
        object:-1
    },
    methods:{
        cancel:function(){
            closeModal();
            if (this.api.onCancel){
                loadModal(this.api.onCancel, {id:this.object})
            }
        },
        remove:function(){
            PostApi(this.api.remove, {id:this.object}, function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});