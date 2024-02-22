<template>
  <div>
    <div>
      <el-form ref="form" :model="form" label-width="80px" style="display: flex;flex-direction: row;">
        <el-form-item label="选择类型">
          <el-cascader :options="options" v-model="form.chooseNode" clearable :props="{ checkStrictly: true }">
            <template slot-scope="{ node, data }">
              <span>{{ data.label }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
        <el-form-item label="图书名称">
          <el-input v-model="form.name" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="()=>{loadData()}"><i class="el-icon-thumb"></i>&nbsp;&nbsp;搜索</el-button>
          <el-button @click="()=>{form.name = '';form.chooseNode = [0]}">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-card class="box-card" v-for="(book,index) in books" :key="index" style="margin-bottom: 12px">
        <div slot="header" class="clearfix">
          <span>{{book.name}}</span>
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
                <el-descriptions-item label="类别"><span class="span_desc">{{BClass1_Icon[book.bclass1 - 1]}}&nbsp;&nbsp;/&nbsp;&nbsp;{{BClass2_Icon[book.bclass1 - 1][book.bclass2 - 1]}}</span></el-descriptions-item>
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
    </div>
    <el-pagination
        @size-change="(val)=>{pageSize = val;loadData()}"
        @current-change="(val)=>{currentPage = val;loadData()}"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100, 200]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <detail ref="detail" @ok="sonLoadData"></detail>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return {
      ID:0,
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
      size:"mini",
      form:{
        chooseNode:[0,0],
        name:""
      },
      options: [{
        value: '1',
        label: '文字',
        children: [{
          value: '1',
          label: '小说',
        }, {
          value: '2',
          label: '文学',
        }, {
          value: '3',
          label: '外国文学',
        }, {
          value: '4',
          label: '经典',
        }, {
          value: '5',
          label: '中国文学',
        }]
      }, {
        value: '2',
        label: '流行',
        children: [{
          value: '1',
          label: '漫画',
        }, {
          value: '2',
          label: '推理',
        }, {
          value: '3',
          label: '绘本',
        }, {
          value: '4',
          label: '悬疑',
        }, {
          value: '5',
          label: '科幻',
        }]
      }, {
        value: '3',
        label: '文化',
        children: [{
          value: '1',
          label: '历史'
        }, {
          value: '2',
          label: '心理学'
        }, {
          value: '3',
          label: '哲学'
        },{
          value: '4',
          label: '社会学'
        },{
          value: '5',
          label: '传记'
        }]
      },{
        value: '4',
        label: '生活',
        children: [{
          value: '1',
          label: '生活'
        }, {
          value: '2',
          label: '爱情'
        }, {
          value: '3',
          label: '成长'
        },{
          value: '4',
          label: '心理'
        },{
          value: '5',
          label: '旅行'
        }]
      },{
        value: '5',
        label: '经管',
        children: [{
          value: '1',
          label: '经济学'
        }, {
          value: '2',
          label: '管理'
        }, {
          value: '3',
          label: '商业'
        },{
          value: '4',
          label: '金融'
        },{
          value: '5',
          label: '投资'
        }]
      },{
        value: '6',
        label: '科技',
        children: [{
          value: '1',
          label: '科普'
        }, {
          value: '2',
          label: '互联网'
        }, {
          value: '3',
          label: '科学'
        },{
          value: '4',
          label: '编程'
        },{
          value: '5',
          label: '算法'
        }]
      }],
      pageSize:10,
      currentPage:1,
      total:100,
      books:[{}]
    }
  },
  mounted(){
    self = this
    this.loadData()
  },
  watch: { // 事件监听
    'form.name':{
      immediate:true,
      handler(){
        self.loadData()
      }
    }
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
    sonLoadData(){

    },
    openDetail(book){
      self.$refs.detail.show(book)
      self.$refs.detail.drawer = true
    },
    loadData(){
      if(!self.LoginJudge()){
        if(self.form.chooseNode[0] === 0){
          $.post("/F2014/ajax1",{search:self.form.name,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
            res = JSON.parse(r)
            self.total = res.data.total
            self.books = res.data.books
          })
        }else{
          if(self.form.chooseNode.length === 1){
            $.post("/F2014/ajax2",{BClass1:self.form.chooseNode[0],search:self.form.name,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
              res = JSON.parse(r)
              self.total = res.data.total
              self.books = res.data.books
            })
          }else{
            $.post("/F2014/ajax3",{BClass1:self.form.chooseNode[0],BClass2:self.form.chooseNode[1],search:self.form.name,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
              res = JSON.parse(r)
              self.total = res.data.total
              self.books = res.data.books
            })
          }
        }
      }else{
        if(self.form.chooseNode[0] === 0){ // 没有分类效果
          $.post("/F2014/ajax1",{search:self.form.name,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
            res = JSON.parse(r)
            self.total = res.data.total
            self.books = res.data.books
          })
        }else {
          if (self.form.chooseNode.length === 1) {
            $.post("/F2014/ajax2", {
              BClass1: self.form.chooseNode[0],
              search: self.form.name,
              currentPage: self.currentPage,
              pageSize: self.pageSize
            }, r => {
              res = JSON.parse(r)
              self.total = res.data.total
              self.books = res.data.books
            })
          } else {
            $.post("/F2014/ajax4", {
              userId:self.ID,
              BClass1: self.form.chooseNode[0],
              BClass2: self.form.chooseNode[1],
              search: self.form.name,
              currentPage: self.currentPage,
              pageSize: self.pageSize
            }, r => {
              res = JSON.parse(r)
              self.total = res.data.total
              self.books = res.data.books
            })
          }
        }
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
    "detail":"url:/T/F20/F2014/CommentDetail.vue"
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

