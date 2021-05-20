inputSearch = {
    props:{
        object:Object,
        props:Object,
        width:String,
        enable:Boolean,
        show:Array,
        create:true
    },
    components: {
        'item-view': itemView
    },
    data:function (){
        return{
            items:[],
            timer:-1,
            addTimer:-1,
            oldObject:{},
            canAdd:false
        }
    },
    computed:{
        placeholder:function () {
            if(this.props.categoryTitle){
                return this.props.categoryTitle;
            }
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
            if(this.items.length === 1){
                this.putValue(this.items[0]);
            } else if (this.items.length === 0 && this.create) {
                this.putValue(Object.assign({}, this.object));
            }
        },
        buildData:function(){

        },
        onKeyUp:function(){
            if (event.keyCode !== 27 ) {
                this.find();
            }
        },
        reqData:function(input){
            return {
                data:input
            }
        },
        find:function(){
            this.items = [];
            this.canAdd = false;
            clearTimeout(this.timer);
            let input = this.object.title;
            if (input && input.length >= 1) {
                this.object.id = -1;
                const self = this;
                this.timer = setTimeout(function () {
                    let data = self.reqData(input);
                    PostApi(self.props.findCategory, data, function (a) {
                        // console.log(a);
                        if (a.status === 'success'){
                            self.items = a.result;
                            if (self.create) {
                                self.addTimer = setTimeout(function () {
                                    self.canAdd = true;
                                }, 3000);
                            }
                        }
                    })
                }, 500);
            }
        },
        clear:function () {
            this.object.id=-1;
            this.object.title='';

            this.items = [];
            this.canAdd = false;
            clearTimeout(this.timer);
            clearTimeout(this.addTimer);
        },
        inputStyle:function () {
            let style = {};
            if (this.width){
                style.width = this.width;
            }
            return style;
        },
        hasPrefix:function () {
            return typeof this.prefix !== "undefined";
        }
    },
    template: '<div v-if="object" style="position: relative;" :style="inputStyle()" v-on:blur="clear">' +
            '<div style="display: flex">' +
                '<span v-if="hasPrefix()">' +
                    '{{prefix()}}' +
                '</span>' +
                '<input v-model="object.title" :placeholder="placeholder" :disabled="isEnabled()" ' +
                    'v-on:keyup.esc.prevent="clear()" v-on:keyup.enter.prevent="putNew()" ' +
                        'v-on:keyup="onKeyUp()" onfocus="this.select()">' +
            '</div>' +
            '<div class="custom-data-list" v-if="items.length > 0 || canAdd">' +
                '<item-view :item="item" :show="show" v-for="item in items" :put="putValue"></item-view>' +
                '<div v-if="(items.length == 0 || canAdd) && create" v-on:click="putNew()">' +
                    '<span v-if="show != null">' +
                        '+ {{object[show]}}' +
                    '</span>' +
                '</div>' +
            '</div>' +
        '</div>'
};