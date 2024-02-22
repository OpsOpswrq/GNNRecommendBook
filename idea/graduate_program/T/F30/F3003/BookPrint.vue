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
        <el-card class="box-card" v-for="(item,index) in books" :key="index" style="margin-bottom: 10px">
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
  name: "BookPrint",
  data(){
    return {
      BClass1_Icon:[
        "文学",
        "流行",
        "文化",
        "生活",
        "经管",
        "科技"
      ],
      BClass2_Icon:[
        ["小说","文学","外国文学","经典","中国文学"],
        ["漫画","推理","绘本","悬疑","科幻"],
        ["历史","心理学","哲学","社会学","传记"],
        ["生活","爱情","成长","心理","旅行"],
        ["经济学","管理","商业","金融","投资"],
        ["科普","互联网","科学","编程","算法"]
      ],
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
      books:[],
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
      options.fileName = self.books[index].description
      let books_b = JSON.parse(self.books[index].books)
      let books_af = []
      for(let i = 0;i < books_b.length;i++){
        books_af.push({
          id:i + 1,
          name:books_b[i].name,
          author:books_b[i].author,
          class:self.BClass1_Icon[books_b[i].bclass1 -1] + "/" + self.BClass2_Icon[books_b[i].bclass1 -1][books_b[i].bclass2 -1],
          price:books_b[i].price,
          left:books_b[i].left.toString()+" 本",
          rate:books_b[i].rate - 5,
          detail:books_b[i].detail
        })
      }
      options.datas = [
        {
          sheetData:books_af,
          sheetHeader:['序号','书名','作者/出版社/出版时间','分类','售价','库存','评分','梗概']
        }
      ]
      var excel = new ExportJsonExcel(options);
      excel.saveExcel()
    },
    print(id){
      window.open("/F3003/print/"+id)
    },
    show(){
      self.loadData()
    },
    loadData(){
      if(self.value === "1"){
        $.post("/F3003/ajax7",{date:"1"},r=>{
          res = JSON.parse(r)
          self.books = res.data
          // console.log(res)
        })
      }else{
        self.value[0] = dayjs(self.value[0]).format("YYYY-MM-DD")
        self.value[1] = dayjs(self.value[1]).format("YYYY-MM-DD")
        $.post("/F3003/ajax7",{date:JSON.stringify(self.value)},r=>{
          res = JSON.parse(r)
          self.books = res.data
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

