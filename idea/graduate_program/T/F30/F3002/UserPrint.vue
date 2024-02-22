<template>
  <div>
    <el-drawer
        title="用户信息"
        :visible.sync="drawer"
        :direction="direction"
        size="40%">
      <div style="position:absolute;left: 14%;top:10px;">
        <el-date-picker
            v-model="value"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions">
        </el-date-picker>
        <el-button type="primary" @click="loadData">搜索</el-button>
      </div>
      <div>
        <el-card class="box-card" v-for="(item,index) in users" :key="index" style="margin-bottom: 10px">
          <span>描述信息：{{item.description}}</span>
          <span style="margin-left: 10px">时间：{{new Date(item.date).toLocaleDateString().replace(/\//g, "-") + " " + new Date(item.date).toTimeString().substr(0, 8)}}</span>
          <el-button-group style="float: right;">
            <el-button type="text" @click="print(item.id)" style="margin-right: 20px"><i class="el-icon-s-platform"></i>预览打印</el-button>
            <el-button type="text" @click="exportExcel(index)"><i class="el-icon-s-promotion"></i>导出</el-button>
          </el-button-group>

        </el-card>
      </div>
    </el-drawer>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "UserPrint",
  data(){
    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      drawer:false,
      direction:"rtl",
      users:[],
      value:"1",
    }
  },
  mounted(){
    self = this
    self.loadData()
  },
  methods: {
    exportExcel(index){ // 导出excel
      let options = {};
      options.fileName = self.users[index].description
      let users_b = JSON.parse(self.users[index].users)
      let users_af = []
      for(let i = 0;i < users_b.length;i++){
        users_af.push({
          id:i + 1,
          userId:users_b[i].userId,
          name:users_b[i].name,
          age:users_b[i].age,
          sex:users_b[i].sex ===  1? "男" : "女",
          email:users_b[i].email
        })
      }
      options.datas = [
        {
          sheetData:users_af,
          sheetHeader:['序号','用户名','名称','年龄','性别','邮箱']
        }
      ]
      var excel = new ExportJsonExcel(options);
      excel.saveExcel()
    },
    print(id){
      window.open("/F3002/print/"+id)
    },
    show(){
      self.loadData()
    },
    loadData(){
      if(self.value === "1"){
        $.post("/F3002/ajax3",{date:"1"},r=>{
          res = JSON.parse(r)
          self.users = res.data
          // console.log(res)
        })
      }else{
        self.value[0] = dayjs(self.value[0]).format("YYYY-MM-DD")
        self.value[1] = dayjs(self.value[1]).format("YYYY-MM-DD")
        $.post("/F3002/ajax3",{date:JSON.stringify(self.value)},r=>{
          res = JSON.parse(r)
          self.users = res.data
        })
      }
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

