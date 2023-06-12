<template>
  <div class="registration-form">
    <div class="register-box">
      <h2>注册</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="username" style="color: #fff; position: relative;">
            用户名            
            <input type="username" id="username" v-model="username" required placeholder="请输入用户名">
          </label>
        </div>
        <div class="form-group">
          <label for="phoneNumber" style="color: #fff; position: relative;">
            手机号
            <input type="phoneNumber" id="phoneNumber" v-model="phoneNumber"  required placeholder="请输入手机号">
          </label>
        </div>
        <div class="form-group">
          <label for="verifycode" style="color: #fff; position: relative;">
            验证码
            <input type="verifycode" id="verifycode" v-model="verifycode" required placeholder="请输入验证码">
          </label>
        </div>
        <button @click="getVertifycode()">获取验证码</button>
        <div class="form-group">
          <label for="password" style="color: #fff; position: relative;">
            新密码
            <input type="password" id="password" v-model="password" required placeholder="请输入密码">
          </label>
        </div>
        <div class="form-group">
          <label for="confirmPassword" style="color: #fff; position: relative;">
            确认新密码
            <input type="password" id="confirmPassword" v-model="confirmPassword" required placeholder="请再次输入密码">
          </label>
        </div>
        <button v-if="canFind" type="submit" @click="submitForm()">确定</button>
      </form>
    </div>
  </div>
</template>

<script>
import {passwordfind} from '@/api/requests/rq-manage.js'

export default {
  data() {
    return {
      passwordfind_form_data: {},
      username: '',
      phoneNumber: '',
      verifycode: '',
      password: '', 
      confirmPassword: '',
      code: '',
      canFind: true,
    }
  },
  methods: {
    submitForm() {
      this.canFind = false
      if(this.username == ''){
        this.$error({
          title: '注册失败',
          content: '请输入用户名',
        });
        this.canFind = true
        return
      }
      if(this.phoneNumber == ''){
        this.$error({
          title: '注册失败',
          content: '请输入手机号',
        });
        this.canFind = true
        return
      }
      if(this.phoneNumber.length != 11){
        this.$error({
          title: '注册失败',
          content: '请输入正确的手机号',
        });
        this.phoneNumber=''
        this.canFind = true
        return
      }
      if(this.verifycode == ''){
        this.$error({
          title: '注册失败',
          content: '请输入验证码',
        });
        this.canFind = true
        return
      }
      if(this.verifycode != this.code){
        this.$error({
          title: '注册失败',
          content: '验证码错误',
        });
        this.canFind = true
        return
      }
      if(this.password == ''){
        this.$error({
          title: '注册失败',
          content: '请输入密码',
        });
        this.canFind = true
        return
      }
      if(this.confirmPassword == ''){
        this.$error({
          title: '注册失败',
          content: '请再次输入密码',
        });
        this.canFind = true
        return
      }
      if(this.password != this.confirmPassword){
        this.$error({
          title: '注册失败',
          content: '两次输入的密码不一致',
        });
        this.confirmPassword = ''
        this.canFind = true
        return
      }
      this.passwordfind_form_data = {
        userName: this.username,
        newPassWord: this.password,
      }
      passwordfind(this.passwordfind_form_data).then(res => {
        if (res.code == 200) {
          this.canFind = true
          this.$success({
            title: '找回密码成功',
            content: (
              <div>
                <p>接下来即将跳转到登录界面...</p>
              </div>
            ),
          });
          setTimeout(() => {
            this.$router.push('/home')
          }, 1000);
        } else {
          this.$error({
            title: '找回密码失败',
            content: res.msg,
          });
          this.canFind = true
          this.username = ''
          this.phoneNumber = ''
          this.verifycode = ''
          this.password = ''
          this.confirmPassword = ''
        }
      })
    },
    getVertifycode(){
      if(this.phoneNumber == ''){
        this.$error({
          title: '获取验证码失败',
          content: '请输入手机号',
        });
        return 
      }
      if(this.phoneNumber.length != 11){
        this.$error({
          title: '获取验证码失败',
          content: '请输入正确的手机号',
        });
        this.phoneNumber=''
        return
      }
      this.code = 123456
      this.$success({
        title: '获取验证码成功',
        content: (
          <div>
            <p>验证码为：123456</p>
          </div>
        ),
      });
    }
  }
}
</script>

<style>
.registration-form {
    background-image: url('https://th.bing.com/th/id/R.1cd5fa3132f027689dea42a856ca0c26?rik=zVEltYscc4K1vQ&riu=http%3a%2f%2fimg.zcool.cn%2fcommunity%2f011dfb55495fca0000019ae913f1f2.jpg%401280w_1l_2o_100sh.jpg&ehk=TPyxtDcW4gUmeBTqTUTQqUU5aUeZj55bRM9bc3z6B%2fw%3d&risl=&pid=ImgRaw&r=0');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    height: 100vh;
    padding-top: 70px;
}
.register-box {
    width: 400px;
    padding: 40px;
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 10px;
    margin: 0 auto;
}
h2 {
    text-align: center;
    color: white;
    text-shadow: 2px 2px 4px #000;
}
.placeholder {
    position: absolute;
    left: 10px;
    top: 65%;
    transform: translateY(-50%);
    font-size: 14px;
    color: #000;
    pointer-events: none;
    transition: all 0.2s ease-in-out;
}
input:focus + .placeholder,
input:not(:placeholder-shown) + .placeholder {
    display: none;
}
.form-group {
    margin-bottom: 20px;
}
label {
    display: block;
    margin-bottom: 5px;
}
input[type="username"],
input[type="phoneNumber"],
input[type="password"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    color: #000;
}
input[type="verifycode"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    color: #000;
}
button[type="submit"] {
    display: block;
    margin: auto;
    padding: 10px 20px;
    background-color: #4caf50;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
}

</style>
