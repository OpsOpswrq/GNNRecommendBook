<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>地址管理</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="openDetail">新增地址</el-button>
      </div>
      <div v-for="(item,index) in addresses" :key="index" class="text item">
        <el-descriptions :title="item.description" id="form_size" :column="2">
          <el-descriptions-item label="地址"><span style="font-size: 20px">{{item.addressCHU + item.addressXI}}</span></el-descriptions-item>
          <el-descriptions-item label="是否删除">
            <el-button @click="deleteAddress(item.id)">删除</el-button>
          </el-descriptions-item>
        </el-descriptions>
        <el-divider></el-divider>
      </div>
    </el-card>
    <detail ref="detail" @ok="loadData"></detail>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return {
      ID:0,
      user:{},
      addresses:[]
    }
  },
  mounted(){
    self = this
    this.loadData()
  },
  methods: {
    LoginJudge(){
      let arrStr = document.cookie.split("; ");
      for (let i = 0; i < arrStr.length; i++) {
        let temp = arrStr[i].split("=");
        if (temp[0] === "ID"){
          self.ID = temp[1]
        }
      }
      if((self.ID - 985) / 380 < 0){
        return false;
      }else{
        return true;
      }
    },
    deleteAddress(id){
      self.addresses = self.addresses.filter((item)=>{
        return item.id !== id
      })
      $.post("/F2015/ajax2",{address:JSON.stringify(self.addresses),ID:self.ID},r=>{
        res = JSON.parse(r)
        self.loadData()
      })
    },
    loadData(){
      if(!self.LoginJudge()){
        self.MtshowError("请先登录")
      }else{
        $.post("/F2015/ajax1",{ID:self.ID},r=>{
          res = JSON.parse(r)
          self.user = res.data
          self.addresses = JSON.parse(self.user.address)
          for(let i = 0;i < self.addresses.length;i++){
            self.addresses[i].id = i
          }
        })
      }
    },
    openDetail(){
      self.$refs.detail.drawer = true
      self.$refs.detail.show(JSON.parse(self.user.address))
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
    "detail":"url:/T/F20/F2015/Address.vue"
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
#form_size .el-descriptions-item__label{
  font-size: 20px;
  font-weight: bold;
}
</style>

