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
          <el-button style="position: relative;top:-30px;left:60%" type="text" @click=""><span style="font-size: 17px;letter-spacing: 8px">管理员登录</span></el-button>
          <el-form :model="user" :rules="rules" ref="user" style="position:relative;top:-20px;margin:-15px;width: 300px">
            <el-form-item label="用户账号" prop="userId">
              <el-input v-model="user.userId" clearable>{{user.userId.trim()}}</el-input>
            </el-form-item>
            <el-form-item label="用户密码" prop="password">
              <el-input v-model="user.password" type="password" clearable show-password>{{user.password.trim()}}</el-input>
            </el-form-item>
            <el-form-item label="进行验证" prop="verifyCode"><br>
              <el-input v-model="user.verifyCode" clearable style="width:180px">{{user.verifyCode.trim()}}</el-input>
              <el-tooltip class="item" effect="dark" content="点击换码" placement="top">
                <el-button plain @click="createCode" v-model="BVerifyCode" class="code">{{BVerifyCode.trim()}}</el-button>
              </el-tooltip>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" style="letter-spacing: 5px" @click="onSubmit" @keyup.enter="onSubmit">登录</el-button>
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
        password: "",
        verifyCode: "",
      },
      BVerifyCode: "",
      rules: {
        password: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},
          {min: 6, max: 8, message: '长度在 6 到 8 个字符', trigger: 'blur'}
        ],
        userId: [
          {required: true, message: '请输入用户账号', trigger: 'blur'},
          {min: 3, max: 9, message: '长度在 3 到 9 个字符', trigger: 'blur'}
        ],
        verifyCode: [
          {required: true, message: '请进行验证码确认', trigger: 'blur'},
          {min: 4, max: 4, message: '请进行验证码确认', trigger: 'blur'}
        ]
      },
      loading: false
    }
  },
  mounted() {
    this.createCode()
  },
  methods: {
    main() {
      window.location.href = "http://localhost:7878/"
    },
    createCode() {
      let code = new Array();
      let codeLength = 4; //验证码的长度
      this.BVerifyCode = ""
      let selectChar = new Array(2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
      for (let i = 0; i < codeLength; i++) {
        let charIndex = Math.floor(Math.random() * 32);
        code += selectChar[charIndex];
      }
      if (code.length != codeLength) {
        createCode();
      }
      this.BVerifyCode = code;
    },
    setCookie(name, value) {
      let Days = 7;
      let exp = new Date();
      exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
      document.cookie = name + "=" + encodeURI(value) + ";expires=" + exp.toGMTString() + ";path=/";
    },
    ComeToVerify() {
      let inputCode = this.user.verifyCode.toUpperCase();
      if (inputCode !== this.BVerifyCode) {
        this.MtshowError("验证码错误")
        this.createCode();
        this.user.verifyCode = ""
        return false;
      } else {
        // this.MtshowOK("验证成功")
        return true;
      }
    },
    onSubmit() {
      this.loading = true;
      this.$refs["user"].validate((valid) => {
        if (valid) {
          $.post("/F1012/ajax1", {userId: this.user.userId.trim(" "), password: this.user.password.trim(" ")}, r => {
            res = JSON.parse(r)
            let token = res.data
            if (res.code === 200) {
              if (this.ComeToVerify()) {
                this.MtshowOK("登录成功")
                this.setCookie(token.tokenName, token.tokenValue); // 设置token数据判断是否已经登录，进入主页时的处理
                window.location.href = "http://localhost:7878/admin"
              }
            } else {
              if (res.msg === "403-1") {
                this.MtshowError("用户不存在")
              }
              if (res.msg === "403-2") {
                this.MtshowError("密码不正确")
              }
            }
          })
        } else {
          return false;
        }
      })
      setTimeout(() => {
      }, 1000)
      this.loading = false
    },
    cancel() {
      this.loading = true;
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
.el-divider--vertical {
  display: inline-block;
  width: 3px;
  height: 400px; //更改竖向分割线长度
  margin: 0 8px;
  vertical-align: middle;
  position: relative;
}

.code {
  //background-image:url(/T/UserData/verify.png);
  font-family: "Microsoft YaHei";
  font-style: italic;
  color: green;
  border: 0;
  height: 100%;
  //margin-top: 20px;
  padding: 2px 2px;
  letter-spacing: 5px;
  font-weight: bolder;
  font-size: 26px;
}
</style>