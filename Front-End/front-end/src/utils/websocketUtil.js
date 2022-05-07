        
function createWebsocket(wsName,wsApi){
    //const表示它的指针不会改变
    const socket = new WebSocket(wsApi);
    socket.onopen = function(){
        console.log(wsName,'linked');
        //成功建立链接后发送这个连接的唯一名称
        socket.send(wsName);
    }

    // socket.onmessage = function(evt){
    //     console.log("消息已送到");
    //     //重新更新页面捏，不是从websocket接口中直接拿数据，有点捞捏!
    //     updatePL(_this);

    // }

    socket.onclose = function(){
        console.log(wsName,'closed');
    }

    socket.error = function(){
        console.log(wsName,'error');
    }

    return socket;
}


//更新喷淋的数据捏

// function updatePL(_this){
//     _this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=&size=5',purpose:'所有'});
//     _this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=1&size=3',purpose:'进场'});
//     _this.asyncGetShowers({api:'http://localhost:8080/api/index/pl?purpose=2&size=3',purpose:'出场'});
//     //获取喷淋的数据
//     _this.asyncGetShowerNum('http://localhost:8080/api/index/pl/num');
// }

export {createWebsocket};
