randomTask = new Vue({
    el:'#editor',
    data:{
        api:{},
        goals:[],
        data:{
            goal:-1,
            count:10
        },
        result:[]
    },
    methods:{
        generate:function () {
            const self = this;
            PostApi(this.api.getTask, this.data, function (a) {
                if(a.status === 'success'){
                    self.result = a.result;
                }
            })
        },
        removeItem:function(idx){
            this.result.splice(idx, 1);
        },
        save:function () {
            let data = [];
            for(let i in this.result){
                if (this.result.hasOwnProperty(i)){
                    let r = this.result[i];
                    data.push(r.id);
                }
            }
            PostApi(this.api.save, {result:data}, function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});