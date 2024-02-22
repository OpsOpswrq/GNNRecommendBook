<template>
  <div>
    <el-tabs type="border-card" v-model="BClass1" @tab-click="()=>{loadData();}">
      <el-tab-pane v-for="(item,index) in Sequence1" :key="index" :label="BClass1_Icon[item - 1]" :name="(item).toString()">
        <span slot="label" style="font-size: 24px">{{BClass1_Icon[item - 1]}}</span>
        <el-tabs v-model="BClass2" @tab-click="()=>{loadData();}">
          <el-tab-pane v-for="(item1,index1) in Sequence2" :label="BClass2_Icon[item - 1][item1]" :name="(item1).toString()" :key="index1">
            <span slot="label" style="font-size: 18px">{{BClass2_Icon[item - 1][item1 - 1]}}</span>
            <el-card class="box-card" v-for="(book,index2) in Books" style="margin:12px" :key="index2">
              <div slot="header" class="clearfix">
                <span style="font-size: 18px;position: relative;left: 1%">{{book.name}}</span>
              </div>
              <div @click="openDetail(book)">
                <div style="display: flex;flex-direction: row">
                  <div style="position: relative;left: 3%;">
                    <el-image
                        style="width: 160px; height: 250px;"
                        :src="book.picture_URL"
                        :preview-src-list="[book.picture_URL]">
                    </el-image>
                  </div>
                  <div style="position: relative;left: 8%;width: 80%">
                    <el-descriptions class="margin-top" :column="1" :size="size" id="form_size">
                      <span slot="title" style="font-size: 22px;font-family: 'Microsoft YaHei UI';font-weight: bolder">{{book.name}}</span>
                      <el-descriptions-item label="作者/出版社/出版时间"><span class="span_desc">{{book.author}}</span></el-descriptions-item>
                      <el-descriptions-item label="售价"><span style="color: #ff9900;font-size: 20px">¥&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="span_desc">{{book.price}}</span></el-descriptions-item>
                      <el-descriptions-item label="库存"><span style="color: #ff9900;font-size: 20px"></span><span class="span_desc">{{book.left}}本</span></el-descriptions-item>
                      <el-descriptions-item label="评分">
                        <el-rate
                            v-model="book.rate - 5"
                            disabled
                            show-score
                            text-color="#ff9900"
                            score-template="{value}">
                        </el-rate>
                      </el-descriptions-item>
                      <el-descriptions-item label="梗概">
                      <span class="span_desc">
                        {{book.detail}}
                      </span>
                      </el-descriptions-item>
                    </el-descriptions>
                  </div>
                </div>
              </div>
            </el-card>
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
    </el-tabs>
    <el-pagination
        @size-change="(val)=>{pageSize = val;loadData()}"
        @current-change="(val)=>{currentPage = val;loadData()}"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100,200,300]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <detail ref="detail"></detail>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return{
      Sequence1:[1,0,3,2,5,4], // 推荐算法 图书类型排序 第一类
      Sequence2:[1,0,3,2,4], // 推荐算法 图书类型排序 第二类
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
      BClass1:"1",
      BClass2:"1",
      Books:[{}],
      currentPage:1,
      total:100,
      pageSize:10,
      size:"mini",
      ID:0,
    }
  },
  mounted(){
    self = this
    self.BClass1 = self.Sequence1[0].toString()
    self.loadData()
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
    openDetail(book){
      self.$refs.detail.show(book)
      self.$refs.detail.drawer = true
    },
    loadData(){
      if(self.LoginJudge()){
        // console.log("adawaw")
        $.post("/F2000/ajax8",{userId:self.ID,BClass1:parseInt(self.BClass1),BClass2:parseInt(self.BClass2),pageSize:self.pageSize,currentPage:self.currentPage},r=>{
          let res = JSON.parse(r)
          self.Sequence1 = res.data.Sequence1.map((x)=>parseInt(x))
          // console.log(self.Sequence1)
          self.Sequence2 = res.data.Sequence2.map((x)=>parseInt(x))
          self.Books = res.data.books
          self.total = res.data.total
        })
      }else{
        $.post("/F2000/ajax1",{BClass1:parseInt(self.BClass1),BClass2:parseInt(self.BClass2),pageSize:self.pageSize,currentPage:self.currentPage},r=>{
          let res = JSON.parse(r)
          self.Sequence1 = res.data.Sequence1
          self.Sequence2 = res.data.Sequence2
          self.Books = res.data.books
          self.total = res.data.total
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
  components:{
    "detail":"url:/T/F20/F2000/CommentDetail.vue"
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
  font-size: 18px;
  font-weight: bold;
}
.span_desc{
  font-size: 18px;
}
</style>

