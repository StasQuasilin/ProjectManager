buyList = new Vue({
    el:'#buyList',
    mixins:[list, pathBuilder],
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
