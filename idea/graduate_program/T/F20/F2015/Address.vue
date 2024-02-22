<template>
  <div>
    <el-drawer
        title="增加地址"
        :visible.sync="drawer"
        :direction="direction"
        size="40%"
        :before-close="handleClose">
      <div style="margin-left: 50px">
        <el-form ref="form" :rules="rules" :model="adNow" label-width="80px">
          <el-form-item label="地址描述" prop="description">
            <el-input v-model="adNow.description" clearable style="width: 500px;"></el-input>
          </el-form-item>
          <el-form-item label="地址选择" prop="address">
            <el-cascader
                size="large"
                :options="options"
                v-model="value"
                clearable
            @change="()=>{adNow.addressCHU = value[0] + value[1] + value[2]}">
            </el-cascader>
          </el-form-item>
          <el-form-item label="具体地址" prop="addressXI">
            <el-input v-model="adNow.addressXI" type="textarea" :rows="3" resize="none" clearable style="width: 500px;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button-group>
              <el-button type="primary" @click="submit" style="margin-right: 20px"><i class="el-icon-add-location"></i>新增地址</el-button>
              <el-button @click="cancel"><i class="el-icon-delete"></i>取消</el-button>
            </el-button-group>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "Address",
  data(){
    return{
      ID:0,
      direction:"rtl",
      drawer:false,
      options:elementChinaAreaData.pcaTextArr,
      value:"",
      adNow:{
        description:"",
        addressCHU:"",
        addressXI:"",
      },
      addresses:[],
      rules: {
        description: [
          { required: true, message: '请输入地址描述', trigger: 'blur' },
        ],
      }
    }
  },
  mounted(){
    self = this
    self.LoginJudge()
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
    show(addresses){
      self.addresses = JSON.parse(JSON.stringify(addresses))
    },
    submit(){
      this.$refs["form"].validate((valid) => {
        if(valid){
          self.addresses.push(self.adNow)
          $.post("/F2015/ajax2",{address:JSON.stringify(self.addresses),ID:self.ID},r=>{
            res = JSON.parse(r)
            self.drawer = false
            self.adNow = {
              description:"",
              addressCHU:"",
              addressXI:"",
            }
            self.$emit("ok")
          })
        }
      })
    },
    cancel(){
      self.drawer = false
      self.adNow = {
        description:"",
        addressCHU:"",
        addressXI:"",
      }
      self.$emit("ok")
      this.$refs["form"].resetFields();
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            self.$emit("ok")
            done();
          })
          .catch(_ => {});
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
</style>

