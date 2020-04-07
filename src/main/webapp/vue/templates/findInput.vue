var findInput = {
    props:{
        props:Object,
        object:Object,
    },
    mounted:function(){

    },
    data:function(){
        return {
            input:'',
            items:[],
            timer:-1,
            timeout:500
        }
    },
    methods:{
        find:function(){
            clearTimeout(this.timer);
            if (this.input) {
                const self = this;
                let findReq = function () {
                    self.findReq();
                };
                this.timer = setTimeout(findReq, this.timeout);
            }
        },
        findReq:function(){
            const self = this;
            PostApi(this.props.find, {key:this.input}, function (ans) {
                self.items = ans.result;
            })
        },
        put:function(item){
            this.input = item.name;
            if (this.props.onPut){
                this.props.onPut(item);
            }
            this.items = []
        }
    },
    template:'<div style="display: inline-block; position: relative">' +
            '<input v-model="input" v-on:keyup="find()">' +
            '<div class="custom-data-list" v-if="items.length > 0">' +
                '<div class="custom-data-list-item" v-for="item in items" v-on:click="put(item)">' +
                    '{{item.name}}' +
                '</div>' +
            '</div>' +
        '</div>'
};