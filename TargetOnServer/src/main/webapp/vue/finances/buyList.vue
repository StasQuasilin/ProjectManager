buyList = new Vue({
    el:'#buyList',
    mixins:[list],
    data:function(){
        return {
            unitNames:{}
        }
    },
    methods:{
        changeStatus:function(item){
            PostApi(this.api.changeStatus, {id:item.id, status:item.done});
        }
    }
});
