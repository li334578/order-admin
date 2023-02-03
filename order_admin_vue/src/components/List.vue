<template>
  <div class="orderInquiry">
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="24" class="searchModel">
        <el-col :span="10" class="searchLet">
          <span>客户</span>
          <el-select v-model="queryInfo.customerId" clearable placeholder="">
            <el-option
                v-for="customer in customerList"
                :key="customer.id"
                :label="customer.customerName +'【'+ customer.customerPhone+'】'"
                :value="customer.id">
            </el-option>
          </el-select>

          <div class="timer">
            <span>订单日期</span>
            <el-date-picker
                v-model="timeRangeCondition"
                @change="buildTimeCondition"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
            </el-date-picker>
          </div>
          <div class="isPay">
            <span>是否已付款</span>
              <el-select v-model="queryInfo.payStatus" clearable placeholder="">
                <el-option
                    v-for="item in options1"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
          </div>

        </el-col>
        <el-col :span="14" class="searchRit">
          <div class="searchRit_let">
            <span>发货状态</span>
            <el-select v-model="queryInfo.sendStatus" clearable placeholder="">
              <el-option
                  v-for="item in options2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>
          <el-button @click="selectData">查询</el-button>
          <el-button>批量导入</el-button>
          <el-button>生成对账单</el-button>
          <el-button>发货录入</el-button>
          <el-button>发货管理</el-button>
          <el-button>产品查询</el-button>
          <el-button>清单列表</el-button>
        </el-col>
      </el-row>

      <el-row :gutter="24" class="searchModel_Amount">
        <el-col :span="10" class="searchLet">
          <el-button>未发货产品</el-button>
          <el-button>未生产产品</el-button>
          <div>金额:{{totalMoney}},总面积:{{totalArea}}</div>

        </el-col>
      </el-row>
      <!-- table表格区域 -->
      <el-table :data="orderList" border stripe class="tableColumn">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="客户" prop="customerName"></el-table-column>
        <el-table-column label="制单人" prop="customerName"></el-table-column>
        <el-table-column label="订单日期" prop="createTime"></el-table-column>
        <el-table-column label="金额" prop="money"></el-table-column>
        <el-table-column label="已收款" prop="receiveMoney"></el-table-column>
        <el-table-column label="总面积" prop="totalArea"></el-table-column>
        <el-table-column label="备注" prop="remark"></el-table-column>
        <el-table-column label="订单号" prop="orderNumber"></el-table-column>
        <el-table-column label="是否已付款" prop="payStatus">
          <template v-slot="scope">
            <el-switch
                v-model="scope.row.payStatus"
                :width="80"
                active-text="已付款"
                inactive-text="未付款"
                @change="updateOrder(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column width="200px" label="发货状态" prop="sendStatus">
          <template v-slot="scope">
            <el-switch
                v-model="scope.row.sendStatus"
                :width="80"
                active-text="已发货"
                inactive-text="未发货"
                @change="updateOrder(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <!-- <el-table-column label="创建时间" prop="add_time" width="140px">
          <template slot-scope="scope">
            {{scope.row.add_time | dateFormat}}
          </template>
        </el-table-column> -->
        <el-table-column label="操作" width="340px" class="scopeBtn">
          <template v-slot="scope">
            <el-button type="primary" size="mini" @click="showDetailInfo(scope.row)">查看/修改</el-button>
            <el-button size="mini">打印发布单</el-button>
            <el-button type="danger" size="mini" @click="removeById(scope.row.goods_id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="queryInfo.current" :page-sizes="[5, 10, 15, 20]" :page-size="queryInfo.size"
                     layout="total, sizes, prev, pager, next, jumper" :total="total" background>
      </el-pagination>
      <!-- Table -->
      <el-dialog title="订单信息" :visible.sync="showDialog" width="80%">
        <h2>基础信息</h2>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="grid-content bg-purple">客户
              <el-input label="" v-model="orderDetails.customerName"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">联系电话
              <el-input v-model="orderDetails.customerPhone"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">地址
              <el-input v-model="orderDetails.customerAddress"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">制单人
              <el-input v-model="orderDetails.createUserName"></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="grid-content bg-purple">
              订单日期
              <el-date-picker
                  v-model="orderDetails.createTime"
                  type="date"
                  placeholder="选择日期">
              </el-date-picker>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">
              金额
              <el-input v-model="orderDetails.money"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">已收款
              <el-input v-model="orderDetails.receiveMoney"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">
              总面积
              <el-input v-model="orderDetails.totalArea"></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="grid-content bg-purple">
              备注
              <el-input v-model="orderDetails.remark"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">
              是否已付款
              <el-select v-model="orderDetails.payStatus" placeholder="请选择">
                <el-option
                    v-for="item in options1"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="grid-content bg-purple">
              发货状态
              <el-select v-model="orderDetails.sendStatus" placeholder="请选择">
                <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-col>
        </el-row>
        <h2>货物信息</h2>
        <el-table
            :data="orderDetails.goodsList"
            border stripe
            style="width: 100%"
            row-key="fid">
          <el-table-column
              fixed
              type="index">
          </el-table-column>

          <el-table-column
              prop="goodsName"
              label="产品名称"
              fixed
              width="220">
            <template v-slot="scope">
              <el-input :value="scope.row.goodsName" @input="saveGoods($event,scope.row,'goodsName')"/>
            </template>
          </el-table-column>

          <el-table-column
              prop="goodsWidth"
              sortable
              label="宽度 mm"
              width="201">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.goodsWidth" @change="saveGoods($event,scope.row,'goodsWidth')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="goodsLength"
              label="长度 mm"
              width="201">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.goodsLength" @change="saveGoods($event,scope.row,'goodsLength')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="total"
              label="数量"
              width="150">
            <template v-slot="scope">
              <el-input :value="scope.row.total" @input="saveGoods($event,scope.row,'total')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="area"
              sortable
              label="面积(m²)"
              width="201">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.area" @change="saveGoods($event,scope.row,'area')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="goodsPrice"
              label="单价"
              width="201">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.goodsPrice" @change="saveGoods($event,scope.row,'goodsPrice')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="processingRequirements"
              label="加工需求"
              width="220">
            <template v-slot="scope">
              <el-input v-model="scope.row.processingRequirements"
                        @input="saveGoods($event,scope.row,'processingRequirements')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="processingExpenses"
              label="加工费用"
              width="201">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.processingExpenses"
                               @change="saveGoods($event,scope.row,'processingExpenses')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="totalMoney"
              label="总金额"
              width="201">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.totalMoney" @change="saveGoods($event,scope.row,'totalMoney')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="remark"
              label="备注"
              width="220">
            <template v-slot="scope">
              <el-input :value="scope.row.remark" @input="saveGoods($event,scope.row,'remark')"/>
            </template>
          </el-table-column>
          <el-table-column
              prop="productionProcess"
              label="生产流程"
              width="220">
            <template v-slot="scope">
              <el-input :value="scope.row.productionProcess" @input="saveGoods($event,scope.row,'productionProcess')"/>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作列"
              width="80">
            <template v-slot="scope">
              <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button @click="addRow(1)">加一行</el-button>
        <el-button @click="addRow(3)">加三行</el-button>
        <el-button @click="addRow(10)">加十行</el-button>
        <el-button @click="addRow(50)">加五十行</el-button>
        <el-button type="primary" @click="updateOrder(orderDetails)">保存订单</el-button>
      </el-dialog>

    </el-card>

  </div>
</template>

<script>
export default {
  data() {
    return {
      // 查询参数对象
      queryInfo: {
        current: 1,
        size: 10,
        startCreateTime: null,
        endCreateTime: null,
        payStatus: null,
        sendStatus: null,
        customerId: null
      },
      customerList: [],
      options1: [
          {
        value: 1,
        label: '已付款'
      },
        {
          value: 0,
          label: '未付款'
        }],
      options2: [
        {
          value: 1,
          label: '已发货'
        },
        {
          value: 0,
          label: '未发货'
        }],
      value: '',
      timeRangeCondition: '',
      // 商品列表
      orderList: [],
      // 总数据条数
      total: 0,
      showDialog:false,
      orderDetails: {
        goodsList:[]
      }
    }
  },
  created() {
    this.getOrderList()
    this.getCustomerList()
  },
  computed:{
    totalMoney() {
      if (this.orderList) {
        let result = 0;
        this.orderList.forEach(item => {
          result += (item.money ? item.money : 0)
        })
        return result;
      }
      return 0
    },
    totalArea() {
      if (this.orderList) {
        let result = 0;
        this.orderList.forEach(item => {
          result += (item.area ? item.area : 0)
        })
        return result;
      }
      return 0
    }
  },
  methods: {
    getCustomerList() {
      // 写get请求
      this.$get('/customer/getCustomerList').then((res) => {
        this.customerList = res.obj
      })
    },
    // 根据分页获取对应的商品列表
    async getOrderList() {
      await this.$post('/order/list', this.queryInfo).then((res) => {
        this.orderList = res.obj.data
        let arr = [];
        for (let i = 0; i < this.orderList.length; i++) {
          let order = this.orderList[i];
          order.index = i;
          arr.push(order)
        }
        this.orderList = arr
        this.total = res.obj.total;
      })
      this.$message.success('获取订单列表成功！')
    },
    selectData() {
      this.getOrderList()
    },
    updateOrder(data) {
      this.$post('/order/updateOrder', data).then(() => {
        this.$message.success('修改成功！')
        this.showDialog=false
      })
    },
    buildTimeCondition() {
      this.queryInfo.startCreateTime = this.timeRangeCondition[0]
      this.queryInfo.endCreateTime = this.timeRangeCondition[1]
    },
    handleSizeChange(newSize) {
      this.queryInfo.size = newSize
      this.getOrderList()
    },
    handleCurrentChange(newPage) {
      this.queryInfo.current = newPage
      this.getOrderList()
    },
    async removeById(id) {
      const confirmResult = await this.$confirm(
          '此操作将永久删除该商品, 是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).catch(err => err)

      if (confirmResult !== 'confirm') {
        return this.$message.info('已经取消删除！')
      }

      const {data: res} = await this.$getete(`goods/${id}`)

      if (res.meta.status !== 200) {
        return this.$message.error('删除失败！')
      }

      this.$message.success('删除成功！')
      this.getOrderList()
    },
    showDetailInfo(data) {
      this.$get('/order/detail', {
        params: {
          orderId:data.id
        }
      }).then((res) => {
        // 弹框
        this.showDialog = true
        this.orderDetails = res.obj
      })
    },
    saveGoods(e, rowData, properties) {
      this.$forceUpdate()
      this.$nextTick(() => {
        this.orderDetails.goodsList.find(item => item.fid === rowData.fid)[properties] = e
      });
    },
    handleDelete(index) {
      this.orderDetails.goodsList.splice(index, 1)
    },
    addRow(num) {
      let maxFid = Math.max.apply(Math, this.orderInfo.goodsList.map(item => item.fid))
      for (let i = 1; i <= num; i++) {
        maxFid++
        // 拿到最大的fid
        this.orderDetails.goodsList.push(
            {
              fid: maxFid,
              goodsName: null,
              goodsWidth: null,
              goodsLength: null,
              total: null,
              area: null,
              goodsPrice: null,
              processingRequirements: null,
              processingExpenses: null,
              totalMoney: null,
              remark: null,
              productionProcess: null,
            }
        )
      }
    },
  }
}
</script>

<style lang="less" scoped>
.orderInquiry {

  /deep/ .el-card__body {
    padding-top: 0 !important;
  }

  .searchModel {
    padding-top: 20px;
    padding-bottom: 15px;
    margin-left: -20px !important;
    margin-right: -20px !important;
    font-size: 12px;
    background-color: #F2F2F2;

    .searchLet {
      display: flex;
      align-items: center;
      padding-right: 0 !important;

      .el-select {
        width: 53%;
      }

      .timer {
        display: flex;
        align-items: center;
        width: 42%;
        margin-left: 14px;

        span {
          width: 30%;
          margin-right: 10px;
        }

        // .el-date-editor {
        //   width: 50%;
        // }
      }

      .isPay {
        display: flex;
        align-items: center;
        margin-left: 14px;

        span {
          width: 50%;
        }

      }
    }

    .searchRit {
      display: flex;
      padding-left: 30px !important;

      .searchRit_let {
        // display: flex;
        // align-items: center;
        width: 16%;
        margin-right: 14px;

        span {
          margin-right: 10px;
        }

        .el-select {
          width: 54%;
        }
      }

      .el-button {
        display: flex;
        align-items: center;
        height: 38px;
        padding: 10px 20px;
      }

      .el-button {
        background-color: #009688;
        color: #fff;

      }

      .el-button:nth-of-type(1) {
        background-color: #1E9FFF;
        color: #fff;

      }
    }
  }

  .searchModel_Amount {
    margin-left: -20px !important;
    margin-right: -20px !important;
    padding-bottom: 15px;
    font-size: 12px;
    background-color: #F2F2F2;

    .searchLet {
      display: flex;
      align-items: center;

      .el-button {
        display: flex;
        align-items: center;
        height: 38px;
        padding: 10px 20px;
        background-color: #009688;
        color: #fff;

      }

      div {
        margin-left: 15px;
        font-size: 20px;
        color: #009688;
      }
    }

  }

  .tableColumn {
    width: 92%;

    /deep/ tbody {
      .el-table__row {
        .el-table_1_column_11, .el-table_1_column_12 {
          .cell {
            display: flex;
            justify-content: center;
          }

          .el-switch {
            // width: 64px;
          }

          .el-switch__label {
            position: absolute;
            display: none;
            color: #fff;
          }

          /*打开时文字位置设置*/

          .el-switch__label--right {
            z-index: 1;
            right: 10px;
          }

          /*关闭时文字位置设置*/

          .el-switch__label--left {
            z-index: 1;
            // left: 20px;
          }

          /*显示文字*/

          .el-switch__label.is-active {
            display: block;
          }

          .el-switch .el-switch__core,
          .el-switch .el-switch__label {
            width: 80px;
            text-align: center;
          }
        }


        .el-table__cell:last-of-type {
          .cell {
            display: flex;
            justify-content: center;

            .el-button {
              color: #fff;
            }

            .el-button:nth-of-type(1) {
              background-color: #1E9FFF;
            }

            .el-button:nth-of-type(2) {
              background-color: #009688;
            }

            .el-button:nth-of-type(3) {
              background-color: #FF5722;
            }
          }

        }

      }
    }


  }


  .pagination {
    border: 1px solid #EBEEF5;
    margin-top: -1px;
    display: flex;
    justify-content: start;

    /deep/ .active {
      background-color: #009688 !important;
    }


  }

}
</style>
