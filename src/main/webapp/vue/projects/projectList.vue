var list = new Vue({
    el: '#list',
    components:{
        'item-view':itemView
    },
    data:{
        api:{},
        items:[]
    },
    methods:{
        getItems:function(){
            return this.items;
        },
        handle:function(items){
            for (let u in items.update){
                if (items.update.hasOwnProperty(u)){
                    let item = items.update[u];
                    Vue.set(this.items, item.id, item);
                }
            }
            for (let r in items.remove){
                if (items.remove.hasOwnProperty(r)){
                    let item = items.remove[r];
                    Vue.delete(this.items, item.id);
                }
            }
        },
        removeProject:function(id){
            loadModal(this.api.remove, {id:id}, function(a){
                if(a.status === 'success'){
                    closeModal();
                }
            })
        },
        edit:function(id){
            loadModal(this.api.edit, {id:id});
        },
        byRole:function(role){
            return Object.values(this.items).filter(function(item){
                return item.role === role;
            })
        }
    }
});