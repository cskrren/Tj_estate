<template>
  <div class="container">
    <noscript>
      <h2>Sorry! Your browser doesn't support Javascript</h2>
    </noscript>

    <div v-if="!islogin">
      <div class="username-page-container">
        <h1 class="title">Type your name</h1>
        <form @submit.prevent="startChatting">
          <div class="form-group">
            <input type="text" v-model="name" placeholder="Username" autocomplete="off" class="form-control" readonly />
          </div>
          <div class="form-group">
            <button type="submit" class="accent username-submit" @click="startChatting">Start Chatting</button>
          </div>
        </form>
      </div>
    </div>

    <div v-else>
      <div class="chat-box">
        <JwChat-index
          v-model="inputMsg"
          :taleList="taleList"
          :toolConfig="tool"
          :scrollType="scrollType"
          :placeholder="placeholder"
          :config="config"
          :winBarConfig="winBarConfig"
          :showRightBox="showRightBox"
          @enter="bindEnter"
          @clickTalk="talkEvent"
        >
          <JwChat-rightbox
            class="rightSlot"
            :config="rightConfig"
            @click="rightClick"
          /> 
        </JwChat-index>

      </div> 
    </div>
  </div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { getUserAvatar, getHistory, getUserInfoList } from '@/api/requests/rq-manage';

export default {
  data() {
    return {
      name: '',
      username: '',
      islogin: false,
      connected: false,
      stompClient: null,
      currentTime: '',
      scrollType: 'scrol', // scroll  noroll 俩种类型
      placeholder: "请输入...",
      inputMsg: '',
      curGroup: 1, 
      tool:{
        callback: this.toolEvent,
      },
      taleList: [],
      config: {
        img: "",
        name: "",
        dept: "同济大学任柯睿",
        callback: this.bindCover,
        historyConfig: {
          show: true,
          tip: "加载历史记录中...",
          callback: this.bindLoadHistory,
        },
      },
      rightConfig: {
        listTip: "当前在线",
        notice:
          "【公告】这是一个小区业主和管理员的聊天室，欢迎大家加入。 ",
        filterTip: "群组成员",
        list: [],
      },
      showRightBox: true,
      winBarConfig: {
        active: "win01",
        width: "180px",
        listHeight: "60px",
        list: [
          {
            id: "0",
            img: process.env.VUE_APP_API_ROOT+'/images/4.jpg',
            name: "智慧小区群聊",
            dept: "共建美好家园",
            readNum: 0,
          },
        ],
        callback: this.bindWinBar,
      },
    }
  },
  mounted () {
    getUserInfoList().then(res => {
      this.name = res.data[0].userName;
      this.$nextTick(() => {
      })
    })
  },
  methods: {
    /**
     * @description: 点击加载更多的回调函数
     * @param {*}
     * @return {*}
     */
     async bindLoadHistory() {
      //  加载历史记录
      let form = new FormData();
      form.append("groupId", this.curGroup);
      form.append("currentSize", this.taleList.length);
      getHistory(form).then((res) => {
        const history = res.data;
        let list = history.concat(this.taleList);
        this.taleList = list;
        this.config.historyConfig.tip = "加载完成";
        this.$nextTick(() => {
          this.$refs.jwChat.finishPullDown();
        });
      })
    },
    bindEnter (e) {
      console.log(e)
      const msg = this.inputMsg
      if (!msg) return;
      
      this.sendMessage();
    },
    toolEvent (type, obj) {
      console.log('tools', type, obj)
    },
    talkEvent (play) {
      console.log(play)
    },
    rightClick(type) {
      console.log("rigth", type);
    },
    bindTalk(play) {
      console.log("talk", play);
      const { key, value } = play;
      if (key === "navIndex")
        this.talk = [1, 1, 1, 1, 1, 1, 1, 1].reduce((p) => {
          p.push("随机修改颜色 #" + Math.random().toString(16).substr(-6));
          return p;
        }, []);
      if (key === "select") {
        this.inputMsg = value;
        this.bindEnter();
      }
      if (key === "delIndex") {
        this.talk.splice(value, 1);
      }
    },
    connect() {
      this.username = this.name.trim();
      if (this.username) {
        const socket = new SockJS(process.env.VUE_APP_API_ROOT + '/ws');
        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, () => {
          this.connected = true;
          this.stompClient.subscribe("/topic/public", this.onMessageReceived);

          const date = new Date();
          const hours = date.getHours().toString().padStart(2, '0');
          const minutes = date.getMinutes().toString().padStart(2, '0');
          const seconds = date.getSeconds().toString().padStart(2, '0');
          this.currentTime = `${hours}:${minutes}:${seconds}`;
          this.stompClient.send("/app/chat.addUser",
            {},
            JSON.stringify(
              { 
                groupId: this.curGroup,
                sender: this.config.name,
                image: this.config.img,
                content: this.config.name + '加入了聊天室',
                currentTime: this.currentTime,
                type: 'JOIN' 
              }
            )
          )
        }, () => {
          console.log('连接失败')
        });
        this.islogin = true;
        this.config.name = this.username
        getUserAvatar().then(res=>{
          this.config.img = res.msg;
        })
      }
    },
    startChatting() {
      this.connect();
    },
    sendMessage() {
      const messageContent = this.inputMsg.trim();
      if (messageContent && this.stompClient) {
        const date = new Date();
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const seconds = date.getSeconds().toString().padStart(2, '0');
        this.currentTime = `${hours}:${minutes}:${seconds}`;
        const chatMessage = {
          groupId: this.curGroup,
          sender: this.config.name,
          image : this.config.img,
          content: messageContent,
          currentTime: this.currentTime,
          type: 'CHAT'
        };
        this.stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
      }
    },
    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);
      if(message.type === 'JOIN' || message.type === 'LEAVE') {
        this.rightConfig.list = [];
        for(let i = 0; i < message.onlineUsers.length; i++) {
          this.rightConfig.list.push(
            {
              id: i + 1,
              img: message.onlineUsers[i].userAvatar,
              name: message.onlineUsers[i].userName,
            }
          )
        }
        if(message.type === 'JOIN') {
          alert(message.sender + '加入了聊天室')
        } else {
          alert(message.sender + '离开了聊天室')
        }
      } else {
        const msgObj = {
          "date": message.currentTime,
          "text": { "text": message.content },
          "mine": message.sender === this.config.name,
          "name": message.sender,
          "img": message.image,
        }
        this.taleList.push(msgObj);
      }
    },
  },
};
</script>

<style scoped>
.container {
    background-image: url('https://p4.itc.cn/q_70/images03/20210718/4ca76a4e9324460bae863e9a3a18900d.png');
    border-radius: 5px;
    background-size: cover;
    background-position: center;
    box-shadow: 0 0 10px #ccc;
    height: 600px;
    overflow: hidden; /* Hide the scrollbar */
}
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
    opacity: 0;
}
.username-page-container {
    margin-top: 100px;
    text-align: center;
    background: #fff;
    box-shadow: 0 1px 11px rgba(0, 0, 0, 0.27);
    border-radius: 2px;
    width: 100%;
    max-width: 500px;
    display: inline-block;
    vertical-align: middle;
    position: relative;
    padding: 35px 55px 35px;
    min-height: 250px;
    top: 50%;
    left: 32%;
}
.title {
    font-size: 40px;
    margin-bottom: 30px;
}
.form-group {
    margin-bottom: 15px;
}
.form-control {
    width: 300px;
    height: 50px;
    font-size: 18px;
    padding: 10px;
    border: none;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}
.username-submit {
    display: block;
    margin: 0 auto;
    width: 200px;
    height: 50px;
    font-size: 20px;
    font-weight: bold;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}
.chat-container {
    margin-top: 50px;
}
.chat-header {
    background-color: #4caf50;
    color: white;
    padding: 10px;
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    border-radius: 5px 5px 0 0;
}
.chat-box {
    position: absolute;

    /* z-index: 9999; */
    width: auto;
    height: auto;
    padding: 10px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
    top: 50px;
    padding-top: 20px;
    left: 0;
    right: 0;
}
.connecting {
    padding: 10px;
    text-align: center;
    font-size: 18px;
    color: #4caf50;
}
#messageArea {
    list-style: none;
    margin: 0;
    padding: 0;
    max-height: 400px;
    overflow-y: auto;
    border: 1px solid #ccc;
    border-radius: 5px;
}
#messageArea li {
    margin-bottom: 10px;
    padding: 10px;
    background-color: #f1f1f1;
    border-radius: 5px;
}
.input-group {
    margin-top: 20px;
}
.input-group input {
    width: 70%;
    font-size: 18px;
    padding: 10px;
    border: none;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}
.input-group button {
    width: 28%;
    height: 50px;
    font-size: 20px;
    font-weight: bold;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}
</style>