categoryList = new Vue({
    el:'#categoryList',
    data:{
        api:{},
        categories:[]

    },
    methods:{
        getCategories:function (parent) {
            let parameters = {
                owner:user
            };
            if (parent){
                parameters.parent = parent.id
            }
            const self = this;
            PostApi(this.api.getCategories, parameters, function (a) {
                if (a.status === 'success'){
                    if (parent){
                        if (a.details){
                            Vue.set(parent, 'details', a.details);
                        }
                        Vue.set(parent, 'categories', a.result);
                    } else {
                        self.categories = a.result;
                    }
                }
            })
        }
    }
});