tree = new Vue({
    el:'#tree',
    mixins:[list],
    data:function(){
        return {
            currentItem:-1
        }
    },
    methods:{
        getChildren:function(itemId){
            PostApi(this.api.getChildren, {parent: itemId}, function (a) {
                console.log(a);
            })
        },
        getPath:function(){
            return ['Root', 'item 1', 'Item 2', 'Item 3', 'Item 4'];
        }
    }
})