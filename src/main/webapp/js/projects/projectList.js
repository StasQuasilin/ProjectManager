var projectList = new Vue({
    el: '#list',
    data:{
        api:{
            update:''
        },
        items:[]
    },
    methods:{
        update:function(){
            const self = this;
            var parameters = {};
            for (var i in this.items){
                if(this.items.hasOwnProperty(i)){
                    var item = this.items[i];
                    parameters[item.id] = item.hash;
                }
            }
            PostApi(this.api.update, parameters, function(a){
                if (true){
                    self.items=a;
                }
            })
        }
    }
})