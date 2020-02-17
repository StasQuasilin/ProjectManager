var projectList = new Vue({
    el: '#list',
    data:{
        api:{},
        items:{}
    },
    methods:{
        update:function(items){
            for (var u in items.update){
                if (items.update.hasOwnProperty(u)){
                    let item = items.update[u];
                    Vue.set(this.items, item.id, item);
                }
            }
            for (var r in items.remove){
                if (items.remove.hasOwnProperty(r)){
                    let item = items.remove(r);
                    Vue.delete(this.items, item);
                }
            }
        },
        byRole:function(role){
            return Object.values(this.items).filter(function(item){
                return item.role === role;
            })
        }
    }
});