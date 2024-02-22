<template>
  <div style="width: 100%;height: 100%">
    <div>
      <el-menu
          class="el-menu-demo"
          mode="horizontal"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
        <div style="float: left">
          <el-menu-item><span class="logo" @click="()=>{window.location.href='http://localhost:7878/admin'}">Admin</span></el-menu-item>
        </div>
        <div style="float: right;position: relative;left: -40px">
          <el-menu-item>
            <el-dropdown>
              <template v-if="isLogin === false">
                <el-avatar :size="40" :src="defaultAvatar"></el-avatar>
              </template>
              <template v-else-if="isLogin === true">
                <el-avatar :size="40" :src="this.user.picture"></el-avatar>
              </template>
              <el-dropdown-menu slot="dropdown">
                <div>
                  <template v-if="isLogin === false">
                    <el-dropdown-item><i class="el-icon-s-promotion"></i><el-button type="text" @click="login">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</el-button></el-dropdown-item>
                  </template>
                  <template v-else-if="isLogin === true">
                    <el-dropdown-item style="font-size: 16px;font-family: 'Arial Black';color: #999933">用户名：{{this.user.name}}</el-dropdown-item>
                    <el-dropdown-item><i class="el-icon-user-solid"></i><el-button type="text" @click="openDetail">用户信息</el-button></el-dropdown-item>
                    <el-dropdown-item><i class="el-icon-switch-button"></i><el-button type="text" @click="">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销</el-button></el-dropdown-item>
                  </template>
                </div>
              </el-dropdown-menu>
            </el-dropdown>
          </el-menu-item>
        </div>
      </el-menu>
    </div>
    <div class="div_all">
      <div @mouseover="()=>{isCollapse = false}" @mouseleave="()=>{isCollapse = true}" class="div_menu">
        <el-menu default-active="1-1" class="el-menu-vertical-demo"  :collapse="isCollapse">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title" style="font-size: 22px;font-weight: bold">管理</span>
            </template>
            <el-menu-item-group>
              <span slot="title">数据图表</span>
              <el-menu-item index="1-1" @click="()=>{navSrc=navSrcArray[2]}"><span style="font-size: 16px;">数据图表</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">用户管理</span>
              <el-menu-item index="1-2" @click="()=>{navSrc=navSrcArray[0]}"><span style="font-size: 16px;">用户管理</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">图书管理</span>
              <el-menu-item index="1-3" @click="()=>{navSrc=navSrcArray[1]}"><span style="font-size: 16px;">图书管理</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">评论管理</span>
              <el-menu-item index="1-4" @click="()=>{navSrc=navSrcArray[3]}"><span style="font-size: 16px;">评论管理</span></el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </div>
      <div class="div_display">
        <iframe :src="navSrc" style="width: 100.5%;height: 100%;border: none"></iframe>
      </div>
    </div>
    <user-info ref="userInfo" @ok="loadData"></user-info>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return{
      navSrc:"http://localhost:7878/F3004",
      navSrcArray:[
          "http://localhost:7878/F3002",
          "http://localhost:7878/F3003",
          "http://localhost:7878/F3004",
          "http://localhost:7878/F3005",
      ],
      user:{},
      defaultAvatar:"/T/UserData/default.png",
      isCollapse:true,
      isLogin:false,
      loading:false,
      ID:-1
    }
  },
  mounted(){
    self = this
    self.loadData()
  },
  methods: {
    openDetail(){
      self.$refs.userInfo.drawer = true
      self.$refs.userInfo.show(self.user)
    },
    loadData(){
      if(!self.LoginJudge()){
        self.MtshowError("请先登录")
      }else{
        $.post("/F3001/ajax1",{ID:self.ID},r=>{
          res = JSON.parse(r)
          self.user = JSON.parse(JSON.stringify(res.data))
          self.user.sex = self.user.sex.toString()
        })
      }
    },
    LoginJudge(){
      let arrStr = document.cookie.split("; ");
      for (let i = 0; i < arrStr.length; i++) {
        let temp = arrStr[i].split("=");
        if (temp[0] === "ID"){
          this.ID = temp[1]
        }
      }
      if((this.ID - 985) / 380 < 0){
        this.isLogin = false;
        return false;
      }else{
        this.isLogin = true;
        return true;
      }
    },
    login(){
      window.location.href="http://localhost:7878/F1012"
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
  components:{
    "userInfo":"url:/T/F30/F3001/UserInfo.vue"
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
.logo{
  font-size: 28px;
  font-family:"Microsoft YaHei";
  font-style:italic;
  color: #01AAED;
}
.div_menu{
  width: 3.4%;
  margin-right: 5px;
  height: 100%;
}
.div_menu:hover{
  width: 14%;
  margin-right: 5px;
  height: 100%;
}
.div_all{
  display: flex;
  flex-direction: row;
  position: absolute;
  left: 0;
  width: 99%;
  height: 92%;
}
.div_display{
  position: relative;
  width: 100%;
  height: 100%;
}
</style>

