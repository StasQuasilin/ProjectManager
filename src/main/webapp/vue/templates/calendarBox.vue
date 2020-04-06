var calendarBox = {
  props:{
      item:Object
  },
    data:function(){
        return {
            clicked:false
        }
    },
    methods:{
        onDrag:function(e){
            console.log(this.clicked);
            if (this.clicked) {
                e.preventDefault();
            }
        },
        mouseDown:function(){
            this.clicked = true;
        },
        mouseUp:function(){
            console.log('up');
            this.clicked = false;
        }
    },
    template:'<div class="calendar-item" @dragstart="onDrag($event)">' +
        '<div>' +
            '<div class="calendar-item-path">' +
                '<span v-for="(p, pIdx) in item.path">' +
                    '{{p.title}}' +
                    '<span v-if="pIdx < item.path.length - 1"> - </span>' +
                '</span>' +
            '</div>' +
            '<div class="calendar-item-title">' +
                '<span v-if="item.time" style="margin-right: 4pt">{{item.time}}</span>' +
                '<span class="calendar-item-title">' +
                    '{{item.title}}' +
                '</span>' +
            '</div>' +
        '</div>' +
        '<div style="font-size: 10pt" v-if="item.index">' +
            '{{item.index}}:00' +
            ' - ' +
            '{{item.length}}' +
        '</div>' +
        '<div v-if="item.index" class="calendar-item-line">' +
        '</div>' +
        '<div class="calendar-item-menu">' +
            '<div class="calendar-item-menu-button calendar-item-menu-button-edit"></div>' +
            '<div class="calendar-item-menu-button calendar-item-menu-button-add"></div>' +
            '<div class="calendar-item-menu-button calendar-item-menu-button-remove"></div>' +
        '</div>' +
    '</div>'
};