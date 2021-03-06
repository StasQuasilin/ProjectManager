let wait = 10;
let subscriber = {
    socket : null,
    subscribes:{},
    unsubscribes:[],
    unsubscribe:function(){
        for (let i in this.unsubscribes){
            if (this.unsubscribes.hasOwnProperty(i)){
                let unsubscribe = this.unsubscribes[i];
                let data = {
                    'action': 'unsubscribe',
                    'subscribe': unsubscribe,
                    'user': user
                };
                this.socket.send(JSON.stringify(data));
            }
        }
        this.unsubscribes = [];
    },

    subscribe:function(subscriber, handler){
        this.infiniteSubscribe(subscriber, handler);
        this.unsubscribes.push(subscriber);
    },
    infiniteSubscribe:function(subscriber, handler){
        if (this.socket.readyState === WebSocket.OPEN) {
            let data = {
                'action': 'subscribe',
                'subscribe': subscriber,
                'user': user
            };
            this.socket.send(JSON.stringify(data));
            this.subscribes[subscriber] = handler;
        } else {
            if (wait >= 0) {
                wait--;
                console.log('Wait socket connection ' + wait);
                const self = this;
                setTimeout(function () {
                    self.subscribe(subscriber, handler);
                }, 1000)
            } else {
                location.reload();
            }
        }
    },
    send:function(message){
        this.socket.send(message);
    },
    connect:function(){
        this.socket = new WebSocket('ws://' + window.location.host + context + SUBSCRIBE_API);
        this.socket.onopen = function(){
            wait = 10;
        };

        const self = this;
        this.socket.onmessage = function (env) {
            try {
                let json = JSON.parse(env.data);
                let subscribe = json.subscribe;
                let data = json.data;
                if (self.subscribes) {
                    let handler = self.subscribes[subscribe];
                    if (handler) {
                        handler(data);
                    }
                } else {
                    console.log('Subscribers is ' + typeof self.subscribes);
                }
            } catch (e) {
                console.error('Wrong json format!!!', env.data, e);
            }
        };
        this.socket.onclose = function (reason) {
            console.log('Socket is close ');
            console.log(reason);
        }
    },
    shutdown:function () {
        this.socket.close();
    }
};