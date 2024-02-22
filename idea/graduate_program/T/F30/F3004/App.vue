<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>热门分类</span>
      </div>
      <div style="display: flex;flex-direction: column">
        <div style="display: flex;flex-direction: row;">
          <div id="EChart1" style="width: 500px;height: 450px;margin-right: 100px;"></div>
          <div id="EChart2" style="width: 500px;height: 450px;margin-right: 100px;"></div>
          <div id="EChart3" style="width: 500px;height: 450px;margin-right: 100px;"></div>
        </div>
        <div style="display: flex;flex-direction: row;">
          <div id="EChart4" style="width: 500px;height: 450px;margin-right: 100px;"></div>
          <div id="EChart5" style="width: 500px;height: 450px;margin-right: 100px;"></div>
          <div id="EChart6" style="width: 500px;height: 450px;margin-right: 100px;"></div>
          <!--        <div id="EChart8" style="width: 450px;height: 450px"></div>-->
        </div>
        <div>
          <div id="EChart7" style="width: 500px;height: 450px"></div>
        </div>
      </div>
    </el-card>
    <el-card class="box-card" style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>搜索人数</span>
      </div>
      <div>
        <div id="EChart8" style="width: 99%;height: 600px"></div>
      </div>
    </el-card>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return {
      all:[],
      hot1:[],
      hot2:[],
      hot3:[],
      hot4:[],
      hot5:[],
      hot6:[],
      day:[],
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
    }
  },
  mounted(){
    self = this
    self.loadData()
  },
  methods: {
    loadData(){
      $.post("/F3004/ajax1",{},r=>{
        res = JSON.parse(r)
        self.hot1 = res.data.hot1
        self.hot2 = res.data.hot2
        self.hot3 = res.data.hot3
        self.hot4 = res.data.hot4
        self.hot5 = res.data.hot5
        self.hot6 = res.data.hot6
        self.all = res.data.all
        self.day = res.data.day
        self.getRenderer1()
        self.getRenderer2()
        self.getRenderer3()
        self.getRenderer4()
        self.getRenderer5()
        self.getRenderer6()
        self.getRenderer7()
        self.getRenderer8()
        // console.log(res)
      })
    },
    getRenderer1(){
      let EChart = echarts.init(document.getElementById("EChart1"))
      let option = {
        title: {
          text: '图书热门分类',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#000000'
          }
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: 'Nightingale Chart',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: self.all[0].cnt, name: self.BClass1_Icon[self.all[0].bclass1 - 1] + "\n" + self.all[0].cnt },
              { value: self.all[1].cnt, name: self.BClass1_Icon[self.all[1].bclass1 - 1] + "\n" + self.all[1].cnt},
              { value: self.all[2].cnt, name: self.BClass1_Icon[self.all[2].bclass1 - 1] + "\n" + self.all[2].cnt},
              { value: self.all[3].cnt, name: self.BClass1_Icon[self.all[3].bclass1 - 1] + "\n" + self.all[3].cnt},
              { value: self.all[4].cnt, name: self.BClass1_Icon[self.all[4].bclass1 - 1] + "\n" + self.all[4].cnt},
              { value: self.all[5].cnt, name: self.BClass1_Icon[self.all[5].bclass1 - 1] + "\n" + self.all[5].cnt},
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    getRenderer2(){
      let EChart = echarts.init(document.getElementById("EChart2"))
      let option = {
        title: {
          text: '文学热门分类',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#000000'
          }
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: 'Nightingale Chart',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: self.hot1[0].cnt, name: self.BClass2_Icon[0][self.hot1[0].bclass2 - 1] + "\n" + self.hot1[0].cnt},
              { value: self.hot1[1].cnt, name: self.BClass2_Icon[0][self.hot1[1].bclass2 - 1] + "\n" + self.hot1[1].cnt},
              { value: self.hot1[2].cnt, name: self.BClass2_Icon[0][self.hot1[2].bclass2 - 1] + "\n" + self.hot1[2].cnt},
              { value: self.hot1[3].cnt, name: self.BClass2_Icon[0][self.hot1[3].bclass2 - 1] + "\n" + self.hot1[3].cnt},
              { value: self.hot1[4].cnt, name: self.BClass2_Icon[0][self.hot1[4].bclass2 - 1] + "\n" + self.hot1[4].cnt},
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    getRenderer3(){
      let EChart = echarts.init(document.getElementById("EChart3"))
      let option = {
        title: {
          text: '流行热门分类',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#000000'
          }
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: 'Nightingale Chart',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: self.hot2[0].cnt, name: self.BClass2_Icon[1][self.hot2[0].bclass2 - 1] + "\n" + self.hot2[0].cnt},
              { value: self.hot2[1].cnt, name: self.BClass2_Icon[1][self.hot2[1].bclass2 - 1] + "\n" + self.hot2[1].cnt},
              { value: self.hot2[2].cnt, name: self.BClass2_Icon[1][self.hot2[2].bclass2 - 1] + "\n" + self.hot2[2].cnt},
              { value: self.hot2[3].cnt, name: self.BClass2_Icon[1][self.hot2[3].bclass2 - 1] + "\n" + self.hot2[3].cnt},
              { value: self.hot2[4].cnt, name: self.BClass2_Icon[1][self.hot2[4].bclass2 - 1] + "\n" + self.hot2[4].cnt},
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    getRenderer4(){
      let EChart = echarts.init(document.getElementById("EChart4"))
      let option = {
        title: {
          text: '文化热门分类',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#000000'
          }
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: 'Nightingale Chart',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: self.hot3[0].cnt, name: self.BClass2_Icon[2][self.hot3[0].bclass2 - 1] + "\n" + self.hot3[0].cnt},
              { value: self.hot3[1].cnt, name: self.BClass2_Icon[2][self.hot3[1].bclass2 - 1] + "\n" + self.hot3[1].cnt},
              { value: self.hot3[2].cnt, name: self.BClass2_Icon[2][self.hot3[2].bclass2 - 1] + "\n" + self.hot3[2].cnt},
              { value: self.hot3[3].cnt, name: self.BClass2_Icon[2][self.hot3[3].bclass2 - 1] + "\n" + self.hot3[3].cnt},
              { value: self.hot3[4].cnt, name: self.BClass2_Icon[2][self.hot3[4].bclass2 - 1] + "\n" + self.hot3[4].cnt},
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    getRenderer5(){
      let EChart = echarts.init(document.getElementById("EChart5"))
      let option = {
        title: {
          text: '生活热门分类',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#000000'
          }
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: 'Nightingale Chart',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: self.hot4[0].cnt, name: self.BClass2_Icon[3][self.hot4[0].bclass2 - 1] + "\n" + self.hot4[0].cnt},
              { value: self.hot4[1].cnt, name: self.BClass2_Icon[3][self.hot4[1].bclass2 - 1] + "\n" + self.hot4[1].cnt},
              { value: self.hot4[2].cnt, name: self.BClass2_Icon[3][self.hot4[2].bclass2 - 1] + "\n" + self.hot4[2].cnt},
              { value: self.hot4[3].cnt, name: self.BClass2_Icon[3][self.hot4[3].bclass2 - 1] + "\n" + self.hot4[3].cnt},
              { value: self.hot4[4].cnt, name: self.BClass2_Icon[3][self.hot4[4].bclass2 - 1] + "\n" + self.hot4[4].cnt},
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    getRenderer6(){
      let EChart = echarts.init(document.getElementById("EChart6"))
      let option = {
        title: {
          text: '经管热门分类',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#000000'
          }
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: 'Nightingale Chart',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: self.hot5[0].cnt, name: self.BClass2_Icon[4][self.hot5[0].bclass2 - 1] + "\n" + self.hot5[0].cnt},
              { value: self.hot5[1].cnt, name: self.BClass2_Icon[4][self.hot5[1].bclass2 - 1] + "\n" + self.hot5[1].cnt},
              { value: self.hot5[2].cnt, name: self.BClass2_Icon[4][self.hot5[2].bclass2 - 1] + "\n" + self.hot5[2].cnt},
              { value: self.hot5[3].cnt, name: self.BClass2_Icon[4][self.hot5[3].bclass2 - 1] + "\n" + self.hot5[3].cnt},
              { value: self.hot5[4].cnt, name: self.BClass2_Icon[4][self.hot5[4].bclass2 - 1] + "\n" + self.hot5[4].cnt},
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    getRenderer7(){
      let EChart = echarts.init(document.getElementById("EChart7"))
      let option = {
        title: {
          text: '科技热门分类',
          left: 'center',
          top: 20,
          textStyle: {
            color: '#000000'
          }
        },
        legend: {
          top: 'bottom'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: 'Nightingale Chart',
            type: 'pie',
            radius: [30, 150],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: [
              { value: self.hot6[0].cnt, name: self.BClass2_Icon[5][self.hot6[0].bclass2 - 1] + "\n" + self.hot6[0].cnt},
              { value: self.hot6[1].cnt, name: self.BClass2_Icon[5][self.hot6[1].bclass2 - 1] + "\n" + self.hot6[1].cnt},
              { value: self.hot6[2].cnt, name: self.BClass2_Icon[5][self.hot6[2].bclass2 - 1] + "\n" + self.hot6[2].cnt},
              { value: self.hot6[3].cnt, name: self.BClass2_Icon[5][self.hot6[3].bclass2 - 1] + "\n" + self.hot6[3].cnt},
              { value: self.hot6[4].cnt, name: self.BClass2_Icon[5][self.hot6[4].bclass2 - 1] + "\n" + self.hot6[4].cnt},
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    getRenderer8(){
      let EChart = echarts.init(document.getElementById("EChart8"))
      let option = {
        xAxis: {
          type: 'category',
          data: [self.day[0].day, self.day[1].day, self.day[2].day, self.day[3].day, self.day[4].day, self.day[5].day, self.day[6].day]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [self.day[0].cnt, self.day[1].cnt, self.day[2].cnt, self.day[3].cnt, self.day[4].cnt, self.day[5].cnt, self.day[6].cnt],
            type: 'line'
          }
        ]
      };
      EChart.setOption(option)
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
</style>

