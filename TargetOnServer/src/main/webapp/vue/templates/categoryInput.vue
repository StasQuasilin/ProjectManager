categoryInput = {
    mixins:[inputSearch],
    components:{
        'input-search':inputSearch
    },
    data:function(){
        return{
            path:[],
            parent:{}
        }
    },
    mounted:function(){
        // const self = this;
        // console.log(self);
        //
        // self.categorySearch.put = function (a) {
        //     if(self.path.length > 0){
        //         a.path = self.path;
        //         self.categoryProps.put(a);
        //     }
        // }
    },
    methods:{
        reqData:function(input){
            let data = {
                key:input
            };
            if (this.parent){
                data.parent = this.parent.id
            }
            return data;
        },
        putValue:function (item) {
            if(item.children && item.children.length > 0){
                this.addPath(item);
            } else {
                this.props.put(Object.assign({path:this.path}, item));
                this.path = [];
                this.parent = {};
                this.clear();
            }
            console.log(item);
        },
        addPath:function(item){
            this.parent = Object.assign({}, item);
            let p = Object.assign({}, item);
            delete p.children;
            this.path.push(p);
            this.clear();
        },
        onKeyUp:function(){
            let e = event;
            if (e.keyCode === 27){
                //escape
                this.clear();
            } else if (e.keyCode === 111) {
                //slash
                this.object.title = this.object.title.replaceAll(e.key, '');
                this.addPath(this.object)
            } else if(e.keyCode === 8){
                //backspace
                if(this.object.title.length === 0 && this.path.length > 0){

                }
            } else {
                this.find();
            }
        },
        prefix:function () {
            if(this.path.length > 0) {
                let values = [];
                for (let i in this.path) {
                    if (this.path.hasOwnProperty(i)) {
                        values.push(this.path[i].title);
                    }
                }
                return values.join('/') + '/';
            }
        }
    }
};