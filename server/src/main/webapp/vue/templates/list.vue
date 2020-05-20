var list = {
    data:function(){
        return {
            api:{},
            items:[],
            limit:-1
        }

    },
    methods:{
        edit:function(item){
            if (this.api.edit){
                let param = {};
                if (item){
                    param.id = item.id
                }
                loadModal(this.api.edit, param);
            } else {
                console.log('Edit api not set');
            }
        },
        handle:function (data) {
            for (let u in data.update){
                if (data.update.hasOwnProperty(u)){
                    let update = data.update[u];
                    this.update(update);
                }
            }
            for (let r in data.remove){
                if (data.remove.hasOwnProperty(r)){
                    let remove = data.remove[r];
                    this.remove(remove);
                }
            }
            this.sortItems();
            if (this.limit > 0){
                this.clean();
            }
        },
        update:function(item){
            let have = false;
            for (let u in this.items){
                if (this.items.hasOwnProperty(u)){
                    let target = this.items[u];
                    if (target.id === item.id){
                        this.items.splice(u, 1, item);
                        have = true;
                        break;
                    }
                }
            }
            if (!have){
                this.items.push(item);
            }
        },
        clean:function(){
            let count = this.items.length - this.limit;
            this.items.splice(0, count);
        },
        remove:function(id){
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let item = this.items[i];
                    if (item.id === id){
                        this.items.splice(i, 1);
                        break;
                    }
                }
            }
        },
        sortItems:function(){
            this.items.sort(this.sort);
        },
        sort:function () {
            return true;
        }

    }
}