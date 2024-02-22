<template>
  <div class="div_all">
    <div class="div_img">
      <div style="position: relative;left:28%;top:8%">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:7878/F2011/ajax3"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
          <el-avatar :size="350" :src="user.picture"></el-avatar>
        </el-upload>
      </div>
      <div style="position: relative;left:13%;top:16%;width: 80%">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-size: 28px;font-style: italic;font-family: 'Microsoft YaHei UI';color: #2e8ded">“Life is a challenge,meet it.”</span>
          </div>
          <div class="container flex">
            <div class="content">
              <span style="font-size: 24px;font-style: italic;">生活是一种挑战，迎接它吧。<br>——特里萨修女</span>
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <div class="div_form">
      <div class="div_form_inner">
        <el-form :model="user" label-position="top" :rules="rules" ref="user" label-width="140px" class="demo-ruleForm" style="position: relative;" id="form_size">
          <el-form-item>
            <el-form-item label="用户账号" prop="userId" class="form_item">
              <el-input v-model="user.userId" :disabled=!isModify clearable style="width: 90%;font-size: 20px"></el-input>
            </el-form-item>
            <el-form-item label="用户名称" prop="name" class="form_item">
              <el-input v-model="user.name"  :disabled=!isModify clearable style="width: 90%;font-size: 20px"></el-input>
            </el-form-item>
            <el-form-item label="用户密码" prop="password" class="form_item">
              <el-input v-model="user.password" type="password" :disabled=!isModify clearable :show-password="isShow" style="width: 90%;font-size: 20px"></el-input>
            </el-form-item>
            <el-form-item label="用户年龄" prop="age" class="form_item">
              <el-slider v-model="user.age" :disabled=!isModify style="width: 90%;"></el-slider>
            </el-form-item>
            <el-form-item label="用户性别" prop="sex" class="form_item">
              <el-radio v-model="user.sex" label="1" :disabled=!isModify><span style="font-size: 20px">男</span></el-radio>
              <el-radio v-model="user.sex" label="0" :disabled=!isModify><span style="font-size: 20px">女</span></el-radio>
            </el-form-item>
            <el-form-item label="用户邮箱" prop="email" class="form_item">
              <el-input v-model="user.email" clearable  :disabled=!isModify style="width: 90%;font-size: 20px"></el-input>
            </el-form-item>
          </el-form-item>
          <el-button-group style="position: absolute;left: 3%;">
            <el-button v-if="isModify === false" type="primary" @click="()=>{isModify = true;isShow = true}" style="font-size: 20px"><i class="el-icon-refresh"></i>修改</el-button>
            <el-button v-if="isModify === true" type="primary" @click="submit" style="font-size: 20px;margin-right: 10px"><i class="el-icon-check"></i>确认</el-button> <!--修改数据-->
            <el-button v-if="isModify === true" @click="cancel" style="font-size: 20px"><i class="el-icon-delete"></i>取消</el-button>
          </el-button-group>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "SonApp",
  data(){
    let checkEmail = (rule, value,callback) =>{//验证邮箱
      const regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      if(this.user.email != '' && !regEmail.test(this.user.email)) {
        callback(new Error('请输入有效的邮箱'));
      }
    }
    return{
      user:{
        picture:"/T/UserData/default.png"
      },
      BUser:{},
      ID:-1,
      isModify:false,
      isShow:false,
      rules:{
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
        ],
        age:[
          {required: true, message: '请选择用户年龄', trigger: 'blur'},
        ],
        sex:[
          {required: true, message: '请选择用户性别', trigger: 'blur'},
        ],
        email:[
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          {validator: checkEmail, trigger: "blur" }
        ],
      }
    }
  },
  mounted(){
    self = this
    if(!this.LoginJudge()){
      this.MtshowError("请先登录")
    }else{
      this.loadData()
    }
  },
  methods: {
    submit() {
      $.post("/F2011/ajax2", {user: JSON.stringify(this.user)}, r => {
        res = JSON.parse(r)
        if (res.code === 200) {
          this.MtshowOK("修改成功")
        }else{
          this.MtshowError("用户账号重叠")
        }
        this.loadData()
        parent.location.reload();
        this.isModify = false
        this.isShow = false
      })
    },
    cancel() {
      this.isModify = false
      this.isShow = false
      this.user = JSON.parse(JSON.stringify(this.BUser))
    },
    loadData() {
      $.post("/F2011/ajax1", {ID: this.ID}, r => {
        res = JSON.parse(r)
        if (res.code === 200) {
          this.user = res.data
          this.user.sex = this.user.sex.toString()
          this.BUser = JSON.parse(JSON.stringify(this.user))
        }
      })
    },
    LoginJudge() {
      let arrStr = document.cookie.split("; ");
      for (let i = 0; i < arrStr.length; i++) {
        let temp = arrStr[i].split("=");
        if (temp[0] === "ID") {
          this.ID = temp[1]
        }
      }
      if ((this.ID - 985) / 380 < 0) {
        return false;
      } else {
        return true;
      }
    },
    handleAvatarSuccess(){
      this.loadData()
      parent.location.reload();  // 全局刷新，弥补iframe的错误
    },
    beforeAvatarUpload(file) { // 控制文件类型的
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.MtshowError('上传头像图片只能是 JPG 格式或 PNG 格式!');
      }
      if (!isLt2M) {
        this.MtshowError('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
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
  },
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
.div_all{
  display: flex;
  flex-direction: row;
  position: absolute;
  left: 0;
  width: 99%;
  height: 92%;
}
.div_img{
  position: relative;
  width: 40%;
  height: 100%;
  margin-right: 15px;
}
.div_form{
  width: 40%;
  height: 100%;
  margin-left: 15px;
}
.div_form_inner{
  position: relative;
  top:5%;
  left: 25%;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 100%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.container {
  height: 100%;
  background-color: #fff;;
}
.content {
  animation: content-slide-in 2s steps(30) forwards;
  overflow: hidden;
  white-space: nowrap;
  position: relative;
}

.content::after {
  content: '';
  position: absolute;
  right: 0;
  height: 16px;
  animation: sprinkle-bling .5s steps(2);
}

@keyframes content-slide-in {
  from {
    width: 0;
  }
  to {
    width: 70%;
  }
}

@keyframes sprinkle-bling {
  from {
    border-right: 1px solid transparent;
  }
  to {
    border-right: 1px solid #000;
  }
}
.form_item{
  margin: 20px;
}
#form_size .el-form-item__label{
  font-size: 24px;
}
</style>

