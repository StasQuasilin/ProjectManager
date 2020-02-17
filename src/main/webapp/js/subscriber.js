const subscriber = {
    socket:{},
    subscribers:{},
    init:function(protocol, server, address){
        console.log(protocol + server + address);
        this.socket = new WebSocket(protocol + server + address);
        this.socket.onclose = function(cause){
            console.log(cause);
        };
        const self = this;
        this.socket.onmessage = function(env){
            var json = JSON.parse(env.data);
            console.log(json);
            var type = json['type'];
            var data = json['data'];
            if (type && self.subscribers[type]) {
                var func = self.subscribers[type];
                if (typeof func === 'function') {
                    func(data, type);
                }
            }
        };
    },
    subscribe:function(subscriber, handler){
        this.subscribers[subscriber] = handler;
        this.send(JSON.stringify({
            action:'subscribe',
            subscriber:subscriber,
            user:UserID
        }))
    },
    unsubscribe:function(subscriber){
        delete this.subscribers[subscriber];
        this.send(JSON.stringify({
            action:'unsubscribe',
            subscriber:subscriber
        }))
    },
    send:function(msg){
        console.log(msg);
        if(this.socket.readyState == WebSocket.OPEN){
            this.socket.send(msg);
        } else if (this.socket.readyState == WebSocket.CONNECTING){
            const self = this;
            setTimeout(function(){
                self.send(msg)
            }, 800)
        }
    },
    close:function(){
        this.socket.close();
    }
};