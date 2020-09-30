goalMembers = new Vue({
    el:'#memberList',
    data:{
        api:{},
        goal:-1,
        owner:{},
        friends:[],
        members:[]
    },
    methods:{
        save:function () {
            let members = [];
            for  (let i = 0; i < this.friends.length ;i++){
                let friend = this.friends[i];
                if (friend.member){
                    members.push(friend.id);
                }
            }
            PostApi(this.api.save, {goal: this.goal, members:members}, function (a) {
                console.log(a)
            })
        }
    }
});