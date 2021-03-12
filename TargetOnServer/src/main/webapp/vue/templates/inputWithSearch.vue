inputSearch = {
    props:{
        object:Object,
        props:Object,
        width:String,
        enable:Boolean,
        show:'title',
        create:true
    },
    data:function (){
        return{
            items:[],
            timer:-1,
            oldObject:{},
            canAdd:false
        }
    },
    mounted:function(){
        if (!this.object){
            console.warn('Object is ' + typeof this.object);
        }
        this.oldObject = this.object;
    },
    methods:{
        isEnabled:function(){
            return this.enable == null || this.enable;
        },
        putValue:function(item){
            if (this.props.put){
                this.props.put(item);
                this.items = [];
            }
            this.clear();
        },
        putNew:function(){
            this.putValue(Object.assign({}, this.object));
        },
        find:function(){
            if (event.keyCode !== 27 ) {
                this.items = [];
                this.canAdd = false;
                clearTimeout(this.timer);
                let input = this.object.title;
                if (input && input.length >= 1) {
                    this.object.id = -1;
                    const self = this;
                    this.timer = setTimeout(function () {
                        PostApi(self.props.findCategory, {key: input}, function (a) {
                            console.log(a);
                            if (a.status === 'success'){
                                self.items = a.result;
                                setTimeout(function () {
                                    self.canAdd = true;
                                }, 3000);
                            }
                        })
                    }, 500);
                }
            }
        },
        clear:function () {
            this.object = this.oldObject;
            this.items = [];
        },
        inputStyle:function () {
            let style = {};
            if (this.width){
                style.width = this.width;
            }
            return style;
        }
    },
    template: '<div v-if="object" style="position: relative" :style="inputStyle()" v-on:blur="clear">' +
            '<input v-model="object.title" :disabled="isEnabled()" ' +
                'v-on:keyup.esc.prevent="clear()" v-on:keyup.enter.prevent="putNew()" ' +
                    'v-on:keyup="find()" onfocus="this.select()">' +
            '<div class="custom-data-list" v-if="items.length > 0 || canAdd">' +
                '<div class="custom-data-list-item" v-for="item in items" v-on:click="putValue(item)">' +
                    '<span v-if="show != null">' +
                        '{{item.title}}' +
                    '</span>' +
                    '<span v-else>' +
                        '{{item}}' +
                    '</span>' +
                '</div>' +
                '<div v-if="(items.length == 0 || canAdd) && create" v-on:click="putNew()">' +
                    '<span v-if="show != null">' +
                        '+ Add {{object[show]}}' +
                    '</span>' +
                '</div>' +
            '</div>' +
        '</div>'
};