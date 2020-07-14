goalList = new Vue({
    el:'#goalList',
    mixins:[list],
    data:function(){
        return {
            test:'TEST'
        }
    },
    methods:{
        openTree:function(itemId){
            loadPage(this.api.tree, {item:itemId});
        }
    }
})