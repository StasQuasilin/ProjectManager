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
            console.log(item);
            loadModal(this.api.changeStatus, {category:item.category});
        }
    }
});
