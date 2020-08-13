accounts = new Vue({
    el:'#accountsList',
    mixins:[list],
    methods:{
        accountExtract:function(accountId){
            loadModal(this.api.extract, {id:accountId});
        },
        afterHandle:function(){
            if (this.getItems().length === 0){
                this.edit();
            }
        }
    }
});