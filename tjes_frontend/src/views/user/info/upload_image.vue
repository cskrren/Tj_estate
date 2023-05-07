<template>
  <div class="avatar-wrapper">
    <a-avatar :src="avatarUrl" size="large" />
    <a-upload
      class="avatar-upload"
      :show-upload-list="false"
      :before-upload="beforeUpload"
    >
      <a-button>更换头像</a-button>
    </a-upload>
  </div>
</template>

<script>
import { message } from 'ant-design-vue' 
import { getUserAvatar, saveUserAvatar } from '@/api/requests/rq-manage.js'


export default {
  name: 'upload_image',
  data () {
    return {
      avatarUrl: '', // 头像地址
    }
  },
  created () {
    this.Get_UserAvatar();
  },
  methods: {
    Get_UserAvatar(){
      getUserAvatar().then(res=>{
        this.avatarUrl = res.msg;
        console.log(this.avatarUrl)
      })
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      const isGIF = file.type === 'image/gif'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG && !isPNG && !isGIF) {
        message.error('上传头像图片只能是 JPG/PNG/GIF 格式!')
        return false
      }
      if (!isLt2M) {
        message.error('上传头像图片大小不能超过 2MB!')
        return false
      }
      const formData = new FormData()
      formData.append('file', file)
      this.save_UserAvatar(formData)
      this.Get_UserAvatar();
      return false // 阻止上传操作
    },
    save_UserAvatar (formData) {
      console.log(formData)
      saveUserAvatar(formData).then(res => {
        if(res.code === 200) {
          this.$message.success('保存成功')
        } else {
          this.$message.error('保存失败')
        }
      })
    }
  }
}
</script>

<style scoped>
.avatar-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 24px;
}
.avatar-upload {
    margin-top: 12px;
}
</style>