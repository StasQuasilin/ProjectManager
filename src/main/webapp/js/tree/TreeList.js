var list = new Vue({
    el: '#tree',
    data:{
        api:{
            edit:''
        },
        items:[],
        selected:null
    },
    methods:{
        handle:function(items){
            console.log(items);
            for (let u in items.update){
                if (items.update.hasOwnProperty(u)){
                    this.update(items.update[u]);
                }
            }
        },
        update:function(item){
            let found = false;
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    if (this.items[i].id === item.id){
                        this.items.splice(i, 1, item);
                    }
                }
            }
            if (!found){
                this.items.push(item);
            }
        },
        select:function(item){
            this.selected = item;
            const self = this;
            PostApi(this.api.getSubs, {parent:item.id}, function(a){
                if (a.status === 'success'){
                    self.items = a.result;
                    self.items.sort(function(a, b){
                        return b.isGroup-(a.isGroup);
                    })
                }
            })
        },
        newTask:function(){
            loadModal(this.api.edit, {parent: this.selected.id});
        }

    }
});
