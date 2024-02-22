<template>
  <div>
    <el-card class="box-card" style="width: 1000px;height: 500px;border-radius: 20px;left: 22%;position:fixed;top:45%;
                transform:translateY(-50%);" v-loading.fullscreen.lock="loading">
      <div slot="header" class="clearfix" align="center">
        <el-tooltip class="item" effect="light" content="主页" placement="top">
          <span style="letter-spacing: 10px;font-size: 25px;font-style:italic;" @click="main">图书推荐管理系统</span>
        </el-tooltip>
      </div>
      <div>
        <div style="float: left;">
          <el-image
              style="width: 100%;height: 100%;object-fit:cover;display: block;"
              :src="url"
              :fit="fits"></el-image>
        </div>
        <el-divider direction="vertical"></el-divider>
        <div style="float:right;width: 300px;margin-top: 10px">
          <el-button style="position: relative;top:-30px;left:70%" type="text" @click="login"><i class="el-icon-caret-right"><span style="font-size: 17px;letter-spacing: 8px">去登陆</span></i></el-button>
          <el-form :model="user" :rules="rules" ref="user" style="position:relative;top:-20px;margin:-15px;width: 300px">
            <el-form-item label="用户账号" prop="userId">
              <el-input v-model="user.userId" clearable></el-input>
            </el-form-item>
            <el-form-item label="用户名称" prop="name">
              <el-input v-model="user.name" clearable></el-input>
            </el-form-item>
            <el-form-item label="用户密码" prop="password">
              <el-input v-model="user.password" type="password" clearable show-password></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" style="letter-spacing: 5px" @click="onSubmit" @keyup.enter="onSubmit">注册</el-button>
              <el-button @click="cancel">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-card>
  </div>
</template>
<script>
module.exports = {
  name: "App",
  data() {
    return {
      url: "/T/UserData/cover.png",
      fits: "cover",
      user: {
        userId: "",
        name: "",
        password: ""
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名称', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},
          {min: 6, max: 8, message: '长度在 6 到 8 个字符', trigger: 'blur'}
        ],
        userId: [
          {required: true, message: '请输入用户账号', trigger: 'blur'},
          {min: 3, max: 9, message: '长度在 3 到 9 个字符', trigger: 'blur'}
        ]
      },
      loading: false
    }
  },
  methods: {
    main(){
      window.location.href = "http://localhost:7878/"
    },
    login() {
      window.location.href = "http://localhost:7878/F1010"
    },
    onSubmit() {
      this.loading = true
      this.$refs["user"].validate((valid) => {
        if (valid){
          $.post("/F1011/ajax1", {
            userId: this.user.userId.trim(" "),
            name: this.user.name.trim(" "),
            password: this.user.password.trim(" ")
          }, r => {
            res = JSON.parse(r)
            if(res.code === 200){
              this.MtshowOK("注册成功,5秒后跳转到登录界面")
              setTimeout(()=>{
                window.location.href = "http://localhost:7878/F1010"
              },5000)
            }else{
              this.MtshowError("注册失败，用户ID重叠，请重新输入用户ID")
              this.user.userId = ""
            }
          })
        }else{
          return false;
        }
      })
      this.loading = false
    },
    cancel() {
      this.loading = true
      this.$refs["user"].resetFields();
      this.loading = false
    },
    MtshowOK(msg) {
      this.$message({
        message: msg,
        type: 'success',
        duration: 1000,
      });
    },
    MtshowError(msg) {
      this.$message({
        message: msg,
        type: 'error',
        duration: 1000,
      });
    }
  }
}
</script>
<style scoped>
.el-divider--vertical{
  display:inline-block;
  width:3px;
  height:400px;  //更改竖向分割线长度
  margin:0 8px;
  vertical-align:middle;
  position:relative;
}
#form_size .el-form-item__label{
  font-size: 24px;
}
</style>

