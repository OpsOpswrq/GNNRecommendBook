<template>
  <div>
    <div>
      <el-form :inline="true" class="demo-form-inline" v-model="form">
        <el-form-item label="书籍信息">
          <el-input v-model="form.bookDetail" placeholder="书籍信息" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="选择类型">
          <el-cascader :options="options" v-model="form.chooseNode" clearable :props="{ checkStrictly: true }">
            <template slot-scope="{ node, data }">
              <span>{{ data.label }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
        </el-form-item>
        <el-badge :value="cnt" class="item">
          <el-button @click="openBook">已选书籍</el-button>
        </el-badge>
        <el-button-group style="float:right;">
          <el-button @click="showBook" type="primary"><i class="el-icon-s-fold"></i>书籍名单</el-button>
        </el-button-group>
      </el-form>
    </div>
    <div>
      <el-card class="box-card" v-for="(book,index) in books" :key="index" style="margin-bottom: 10px">
        <div slot="header" class="clearfix">
          <span style="font-size: 18px;position: relative;left: 1%">{{book.name}}</span>
          <el-button-group style="float: right;">
            <el-button type="text" style="margin-right: 20px" v-if="book.isChoosed === false" @click="addBook(book)">选择</el-button>
            <el-button type="text" @click="deleteBook(book.Id)">删除</el-button>
          </el-button-group>
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
                <el-descriptions-item label="分类"><span class="span_desc">{{BClass1_Icon[book.bclass1 - 1]}}/{{BClass2_Icon[book.bclass1 - 1][book.bclass2 - 1]}}</span></el-descriptions-item>
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
        @size-change="(val)=>{pageSize = val}"
        @current-change="(val)=>{currentPage = val}"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100, 200, 300]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <book-detail ref="detail" @ok="loadData"></book-detail>
    <book-info ref="info" @ok="reload"></book-info>
    <book-print ref="print" @ok=""></book-print>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return{
      cnt:0,
      chooseBooks:[],
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
      total:100,
      currentPage:1,
      pageSize:10,
      form:{
        bookDetail:"",
        chooseNode:[],
      },
      books:[],
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
    }
  },
  mounted(){
    self = this
    self.loadData()
  },
  methods: {
    deleteBook(id){
      this.$confirm('确认删除？')
          .then(_ => {
            $.post("/F3003/ajax8",{ID:id},r=>{
              res = JSON.parse(r)
              self.loadData()
            })
            done();
          })
          .catch(_ => {});
    },
    showBook(){
      self.$refs.print.drawer = true
      self.$refs.print.show()
    },
    reload(books_son){
      self.chooseBooks = JSON.parse(JSON.stringify(books_son))
      block1:for(let i = 0;i < self.books.length;i++){
        if(books_son.length === 0){
          self.books[i].isChoosed = false
        }
        block2:for(let j = 0;j < books_son.length;j++){
          if(self.books[i].Id === books_son[j].Id){
            self.books[i].isChoosed = true
            break block1 // 标签跳转
          }else{
            self.books[i].isChoosed = false
          }
        }
      }
      self.cnt = self.chooseBooks.length
    },
    openBook(){
      self.$refs.info.drawer = true
      self.$refs.info.show(self.chooseBooks)
    },
    addBook(book){
      book.isChoosed = true
      self.chooseBooks.push(book)
      self.cnt = self.chooseBooks.length
    },
    openDetail(book){
      self.$refs.detail.drawer = true
      self.$refs.detail.show(book)
    },
    loadData(){
      if(self.form.chooseNode.length === 0){
        $.post("/F3003/ajax1",{bookDetail:self.form.bookDetail,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
          res = JSON.parse(r)
          self.total = res.data.total
          self.books = res.data.books
          for(let i = 0;i < self.books.length;i++){
            self.books[i].isChoosed = false
          }
        })
      }else{
        if(self.form.chooseNode.length === 1){
          $.post("/F3003/ajax2",{BClass1:self.form.chooseNode[0],search:self.form.bookDetail,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
            res = JSON.parse(r)
            self.total = res.data.total
            self.books = res.data.books
            for(let i = 0;i < self.books.length;i++){
              self.books[i].isChoosed = false
            }
          })
        }else{
          $.post("/F3003/ajax3",{BClass1:self.form.chooseNode[0],BClass2:self.form.chooseNode[1],search:self.form.bookDetail,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
            res = JSON.parse(r)
            self.total = res.data.total
            self.books = res.data.books
            for(let i = 0;i < self.books.length;i++){
              self.books[i].isChoosed = false
            }
          })
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
    "bookDetail":"url:/T/F30/F3003/BookDetail.vue",
    "bookInfo":"url:/T/F30/F3003/BookInfo.vue",
    "bookPrint":"url:/T/F30/F3003/BookPrint.vue",
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

