var findInput = {
    props:{
        props:Object,
        object:Object,
    },
    mounted:function(){

    },
    data:function(){
        return {
            input:'Input',
            items:[],
            timer:-1,
            timeout:500
        }
    },
    methods:{
        find:function(){
            clearTimeout(this.timer);
            const self = this;
            let findReq = function(){
                self.findReq();
            };
            this.timer = setTimeout(findReq, this.timeout);
        },
        findReq:function(){
            console.log('Find ' + this.input);
        }
    },
    template:'<div style="display: inline-block">' +
            '<input v-model="input" v-on:keyup="find()">' +
            '<div class="custom-data-list" v-if="items.length > 0">' +
                '<div class="custom-data-list-item" v-for="item in items">' +
                    '{{item}}' +
                '</div>' +
            '</div>' +
        '</div>'
};