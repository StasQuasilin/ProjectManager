var findInput = {
    props:{
        props:Object,
        object:Object
    },
    mounted:function(){
        this.input = this.object[this.props.field];
    },
    data:function(){
        return {
            input:'',
            items:[],
            timer:-1,
            timeout:500,
            matcher:/\w/,
            notFound:false
        }
    },
    methods:{
        find:function(){
            this.clear();
            let input = this.getInput().toString();
            if (input.match(this.matcher)) {
                const self = this;
                let findReq = function () {
                    self.findReq();
                };
                this.timer = setTimeout(findReq, this.timeout);
            }
        },
        findReq:function(){
            const self = this;
            this.object.id = -1;
            let input = this.getInput();
            PostApi(this.props.find, {key:input}, function (ans) {
                self.items = ans.result;
                if (self.items.length === 0){
                    self.notFound = true;
                }
            })
        },
        getInput:function(){
            if (this.props.field) {
                return this.object[this.props.field];
            } else {
                return this.input;
            }
        },
        put:function(item){
            this.input = item.name;
            if (this.props.onPut){
                this.props.onPut(item);
            }
            this.clear()
        },
        clear:function(){
            this.input = '';
            this.items = [];
            this.notFound = false;
            clearTimeout(this.timeout);
        }
    },
    template:'<div style="display: inline-block; position: relative">' +
            '' +
            '<input v-model="object[props.field]" v-on:keyup.escape="clear()" v-on:keyup="find()">' +
            '<div class="custom-data-list">' +
                '<div class="custom-data-list-item" v-for="item in items" v-on:click="put(item)">' +
                    '{{item.name}}' +
                '</div>' +
                '<div v-if="notFound" class="custom-data-list-item">' +
                    'Not found' +
                '</div>' +
            '</div>' +
        '</div>'
};