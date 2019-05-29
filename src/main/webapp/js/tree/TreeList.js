var tree = new Vue({
    el: '#tree',
    data:{
        api:{
            update:''
        },
        tree:{},
        selected:-1
    },
    methods:{
        update:function(){
            const self = this;
            var parameters = {};
            parameters['selected']=this.selected;
            PostApi(this.api.update, parameters, function(a){
                console.log(a);
                self.tree = a;
            })
        },
        select:function(id){
            this.selected = id;
            this.update();
        }
    }
});
