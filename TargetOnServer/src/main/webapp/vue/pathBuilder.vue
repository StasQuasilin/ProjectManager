pathBuilder = {
    methods:{
        getPath:function(item){
            let path = [];
            if (item){
                this.buildPath(path, item);
            }
            return path;
        },
        buildPath:function (path, item) {
            let parent = item.parent;
            if (parent){
                path.unshift({
                    id:parent.id,
                    title:parent.title
                });
                this.buildPath(path, parent);
            }
        }
    }
};