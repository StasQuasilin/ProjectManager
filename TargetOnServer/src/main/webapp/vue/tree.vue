tree = new Vue({
    el:'#tree',
    mixins:[list],
    data:function(){
        return {
            currentItem:-1,
            root:null,
            item:null,
            children:[]
        }
    },
    methods:{
        handler:function(data){
            let update = data.update;
            for (let i in this.children){
                if (this.children.hasOwnProperty(i)){
                    let item = this.children[i];
                    if (item.id === update.id){
                        this.children.splice(i, 1, update);
                        break;
                    }
                }
            }
            console.log(data);
        },
        newTask:function(){
            let params = {};
            if (this.item){
                params.parent = this.item.id;
            }
            this.edit(-1, params);
        },
        getChildren:function(itemId){
            const self = this;
            PostApi(this.api.getChildren, {parent: itemId}, function (a) {
                self.item = a.item;
                self.children = a.children;
            })
        },
        childrenByStatus:function(status){
            let items = this.children.filter(function (item) {
                return item.status === status;
            });
            items.sort(function (a, b) {
                return a.title.localeCompare(b.title);
            });
            return items;
        },
        getPath:function(){
            let path = [];
            if (this.item){
                path.push(this.item.title);
            }
            return path;
        }
    }
});