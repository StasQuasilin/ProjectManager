var filter = new Vue({
    el:'#filter',
    data:{
        projects:[]
    },
    mounted:{

    },
    methods:{
        handle:function (data) {
            for (let u in data.update){
                if (data.update.hasOwnProperty(u)){
                    let update = data.update[u];
                    this.projects.push(update);
                }
            }
        }
    }
})