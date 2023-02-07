<template>
  <div class="orderInquiry">
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="24" class="searchModel">
        <el-col :xs="24" :sm="24" :md="24" :lg="14" class="searchLet">
          <div class="changeType">
            <span>客户</span>
            <el-select v-model="queryInfo.customerId" clearable placeholder="">
              <el-option
                v-for="customer in customerList"
                :key="customer.id"
                :label="
                  customer.customerName + '【' + customer.customerPhone + '】'
                "
                :value="customer.id"
              >
              </el-option>
            </el-select>
          </div>

          <div class="timer">
            <span>订单日期</span>
            <el-date-picker
              v-model="timeRangeCondition"
              @change="buildTimeCondition"
              type="daterange"
              range-separator="至"
              start-placeholder=""
              end-placeholder=""
            >
            </el-date-picker>
          </div>
          <div class="isPay">
            <span>是否已付款</span>
            <el-select v-model="queryInfo.payStatus" clearable placeholder="">
              <el-option
                v-for="item in options1"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>

          <div class="searchRit_let">
            <span>发货状态</span>
            <el-select v-model="queryInfo.sendStatus" clearable placeholder="">
              <el-option
                v-for="item in options2"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="10" class="searchRit">
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
          <div>金额:{{ totalMoney }},总面积:{{ totalArea }}</div>
        </el-col>
      </el-row>
      <!-- table表格区域 -->
      <el-table :data="orderList" border stripe class="tableColumn">
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column label="序号" type="index" width="55"></el-table-column>
        <el-table-column label="客户" prop="customerName"></el-table-column>
        <el-table-column label="制单人" prop="customerName"></el-table-column>
        <el-table-column
          label="订单日期"
          prop="createTime"
          width="100"
        ></el-table-column>
        <el-table-column label="金额" prop="money"></el-table-column>
        <el-table-column label="已收款" prop="receiveMoney"></el-table-column>
        <el-table-column label="总面积" prop="totalArea"></el-table-column>
        <el-table-column
          label="备注"
          prop="remark"
          width="150"
        ></el-table-column>
        <el-table-column
          label="订单号"
          prop="orderNumber"
          width="150"
        ></el-table-column>
        <el-table-column width="100px" label="是否已付款" prop="payStatus">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.payStatus"
              :width="80"
              active-text="已付款"
              inactive-text="未付款"
              @change="updateOrder(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column width="100px" label="发货状态" prop="sendStatus">
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.sendStatus"
              :width="80"
              active-text="已发货"
              inactive-text="未发货"
              @change="updateOrder(scope.row)"
            >
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
            <el-button
              type="primary"
              size="mini"
              @click="showDetailInfo(scope.row)"
              >查看/修改</el-button
            >
            <el-button size="mini" @click="showPrintInfo(scope.row)"
              >打印发布单</el-button
            >
            <el-button
              type="danger"
              size="mini"
              @click="removeOrderById(scope.row.id)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
        class="pagination"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.current"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
      >
      </el-pagination>
      <!-- Table -->
      <el-dialog
        title="订单信息"
        :visible.sync="showOrderInfoDialog"
        width="98%"
        class="orderDetail"
      >
        <div class="order_tit">基础信息</div>
        <el-row :gutter="60" class="baseInfo">
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>客户</span>
              <el-input label="" v-model="orderDetails.customerName"></el-input>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>联系电话</span>
              <el-input v-model="orderDetails.customerPhone"></el-input>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>地址</span>
              <el-input v-model="orderDetails.customerAddress"></el-input>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>制单人</span>
              <el-input v-model="orderDetails.createUserName"></el-input>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">
              <span>订单日期</span>
              <el-date-picker
                v-model="orderDetails.createTime"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="60" class="baseInfo">
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>金额</span>
              <el-input v-model="orderDetails.money"></el-input>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>已收款</span>
              <el-input v-model="orderDetails.receiveMoney"></el-input>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>总面积</span>
              <el-input v-model="orderDetails.totalArea"></el-input>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>备注</span>
              <el-input v-model="orderDetails.remark"></el-input>
            </div>
          </el-col>
          <el-col :span="4">
          <div class="grid-content bg-purple">
            <span>订单号</span>
            <el-input disabled v-model="orderDetails.orderNumber"></el-input>
          </div>
        </el-col>
        </el-row>
        <el-row :gutter="60" class="baseInfo">
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>是否已付款</span>
              <el-select v-model="orderDetails.payStatus" placeholder="请选择">
                <el-option
                  v-for="item in options1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="grid-content bg-purple">
              <span>发货状态</span>
              <el-select v-model="orderDetails.sendStatus" placeholder="请选择">
                <el-option
                  v-for="item in options2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
          </el-col>
        </el-row>
        <div class="order_tit">货物信息</div>
        <el-table
          :data="orderDetails.goodsList"
          border
          stripe
          style="width: 100%"
          row-key="fid"
        >
          <el-table-column fixed type="index"> </el-table-column>

          <el-table-column prop="goodsName" label="产品名称" fixed width="200">
            <template v-slot="scope">
              <el-input
                size="small"
                :value="scope.row.goodsName"
                @input="saveGoods($event, scope.row, 'goodsName')"
              />
            </template>
          </el-table-column>

          <el-table-column
            prop="goodsWidth"
            sortable
            label="宽度 mm"
            width="160"
          >
            <template v-slot="scope">
              <el-input-number
                size="small"
                v-model="scope.row.goodsWidth"
                @change="saveGoods($event, scope.row, 'goodsWidth')"
              />
            </template>
          </el-table-column>
          <el-table-column prop="goodsLength" label="长度 mm" width="160">
            <template v-slot="scope">
              <el-input-number
                size="small"
                v-model="scope.row.goodsLength"
                @change="saveGoods($event, scope.row, 'goodsLength')"
              />
            </template>
          </el-table-column>
          <el-table-column prop="total" label="数量" width="140">
            <template v-slot="scope">
              <el-input
                size="small"
                :value="scope.row.total"
                @input="saveGoods($event, scope.row, 'total')"
              />
            </template>
          </el-table-column>
          <el-table-column prop="area" sortable label="面积(m²)" width="160">
            <template v-slot="scope">
              <el-input-number
                size="small"
                v-model="scope.row.area"
                @change="saveGoods($event, scope.row, 'area')"
              />
            </template>
          </el-table-column>
          <el-table-column prop="goodsPrice" label="单价" width="160">
            <template v-slot="scope">
              <el-input-number
                size="small"
                v-model="scope.row.goodsPrice"
                @change="saveGoods($event, scope.row, 'goodsPrice')"
              />
            </template>
          </el-table-column>
          <el-table-column
            prop="processingRequirements"
            label="加工需求"
            width="210"
          >
            <template v-slot="scope">
              <el-input
                size="small"
                v-model="scope.row.processingRequirements"
                @input="saveGoods($event, scope.row, 'processingRequirements')"
              />
            </template>
          </el-table-column>
          <el-table-column
            prop="processingExpenses"
            label="加工费用"
            width="160"
          >
            <template v-slot="scope">
              <el-input-number
                size="small"
                v-model="scope.row.processingExpenses"
                @change="saveGoods($event, scope.row, 'processingExpenses')"
              />
            </template>
          </el-table-column>
          <el-table-column prop="totalMoney" label="总金额" width="160">
            <template v-slot="scope">
              <el-input-number
                size="small"
                v-model="scope.row.totalMoney"
                @change="saveGoods($event, scope.row, 'totalMoney')"
              />
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" width="220">
            <template v-slot="scope">
              <el-input
                size="small"
                :value="scope.row.remark"
                @input="saveGoods($event, scope.row, 'remark')"
              />
            </template>
          </el-table-column>
          <el-table-column
            prop="productionProcess"
            label="生产流程"
            width="220"
          >
            <template v-slot="scope">
              <el-input
                size="small"
                :value="scope.row.productionProcess"
                @input="saveGoods($event, scope.row, 'productionProcess')"
              />
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="80">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="submit">
          <div>
            <el-button @click="addRow(1)">加一行</el-button>
            <el-button @click="addRow(3)">加三行</el-button>
            <el-button @click="addRow(10)">加十行</el-button>
            <el-button @click="addRow(50)">加五十行</el-button>
          </div>
          <el-button type="primary" @click="updateOrder(orderDetails)"
            >保存订单</el-button
          >
        </div>
      </el-dialog>

      <el-dialog
        title="发货单"
        ref="print"
        :visible.sync="showPrintInfoDialog"
        width="54.9%"
        class="printInfo"
      >
        <el-button class="no-print" @click="printWindow()">打印</el-button>
        <div class="order_tit">发货单</div>
        <div class="thead">
          <el-row :gutter="20">
            <el-col :span="8">
              <div class="grid-content bg-purple">
                客户：{{ printDetails.customerName }}
              </div>
            </el-col>
            <el-col :span="8">
              <div class="grid-content bg-purple">
                电话：{{ printDetails.customerPhone }}
              </div>
            </el-col>
            <el-col :span="8">
              <div class="grid-content bg-purple">
                备注：{{ printDetails.remark }}
              </div>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <div class="grid-content bg-purple">
                日期：{{ printDetails.createTime }}
              </div>
            </el-col>
            <el-col :span="8">
              <div class="grid-content bg-purple">
                地址：{{ printDetails.customerAddress }}
              </div>
            </el-col>
            <el-col :span="8">
              <div class="grid-content bg-purple">
                订单号：{{ printDetails.orderNumber }}
              </div>
            </el-col>
          </el-row>
        </div>
        <el-table
          :data="printDetails.goodsList"
          border
          style="width: 100%"
          class="invoiceTable"
          :span-method="arraySpanMethod"
        >
          <el-table-column
            fixed
            type="index"
            :index="processingIndex"
            label="序号"
            width="50%"
          ></el-table-column>

          <el-table-column prop="goodsName" label="产品名称" fixed width="160%">
            <template v-slot="scope">
              {{ scope.row.goodsName }}
            </template>
          </el-table-column>

          <el-table-column prop="goodsWidth" label="宽度 mm" width="50%">
            <template v-slot="scope">
              {{ scope.row.goodsWidth }}
            </template>
          </el-table-column>
          <el-table-column prop="goodsLength" label="长度 mm" width="50%">
            <template v-slot="scope">
              {{ scope.row.goodsLength }}
            </template>
          </el-table-column>
          <el-table-column prop="total" label="数量" width="50%">
            <template v-slot="scope">
              {{ scope.row.total }}
            </template>
          </el-table-column>
          <el-table-column prop="area" label="面积(m²)" width="50%">
            <template v-slot="scope">
              {{ scope.row.area }}
            </template>
          </el-table-column>
          <el-table-column prop="goodsPrice" label="单价" width="50%">
            <template v-slot="scope">
              {{ scope.row.goodsPrice }}
            </template>
          </el-table-column>
          <el-table-column
            prop="processingRequirements"
            label="加工需求"
            width="100"
          >
            <template v-slot="scope">
              {{ scope.row.processingRequirements }}
            </template>
          </el-table-column>
          <el-table-column
            prop="processingExpenses"
            label="加工费用"
            width="66%"
          >
            <template v-slot="scope">
              {{ scope.row.processingExpenses }}
            </template>
          </el-table-column>
          <el-table-column prop="totalMoney" label="总金额" width="66%">
            <template v-slot="scope">
              {{ scope.row.totalMoney }}
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" width="160%">
            <template v-slot="scope">
              {{ scope.row.remark }}
            </template>
          </el-table-column>
          <el-table-column
            prop="productionProcess"
            label="生产流程"
            width="160%"
          >
            <template v-slot="scope">
              {{ scope.row.productionProcess }}
            </template>
          </el-table-column>
        </el-table>
        <el-row :gutter="20" class="sign"> 
        <el-col :span="16">
          <div class="grid-content bg-purple">制单人:{{this.printDetails.createUserName}}</div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content bg-purple">客户签字:</div>
        </el-col>
      </el-row>
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
        customerId: null,
      },
      customerList: [],
      options1: [
        {
          value: true,
          label: "已付款",
        },
        {
          value: false,
          label: "未付款",
        },
      ],
      options2: [
        {
          value: true,
          label: "已发货",
        },
        {
          value: false,
          label: "未发货",
        },
      ],
      value: "",
      timeRangeCondition: "",
      // 商品列表
      orderList: [],
      // 总数据条数
      total: 0,
      showOrderInfoDialog: false,
      showPrintInfoDialog: false,
      orderDetails: {
        goodsList: [],
      },
      printDetails:{
        goodsList: [],
      }
    };
  },
  created() {
    this.getOrderList();
    this.getCustomerList();
  },
  computed: {
    totalMoney() {
      if (this.orderList) {
        let result = 0;
        this.orderList.forEach((item) => {
          result += item.money ? item.money : 0;
        });
        return result;
      }
      return 0;
    },
    goodsListTotalMoney() {
      if (this.printDetails.goodsList) {
        let result = 0;
        this.printDetails.goodsList.forEach((item) => {
          result += item.totalMoney ? item.totalMoney : 0;
        });
        return result;
      }
      return 0;
    },
    totalArea() {
      if (this.orderList) {
        let result = 0;
        this.orderList.forEach((item) => {
          result += item.totalArea ? item.totalArea : 0;
        });
        return result;
      }
      return 0;
    },
    goodsListTotalArea() {
      if (this.printDetails.goodsList) {
        let result = 0;
        this.printDetails.goodsList.forEach((item) => {
          result += item.area ? item.area : 0;
        });
        return result;
      }
      return 0;
    },
    goodsListTotalNumber() {
      if (this.printDetails.goodsList) {
        let result = 0;
        this.printDetails.goodsList.forEach((item) => {
          result += item.total ? item.total : 0;
        });
        return result;
      }
      return 0;
    },
    goodsListProcessingExpenses(){
      if (this.printDetails.goodsList) {
        let result = 0;
        this.printDetails.goodsList.forEach((item) => {
          result += item.processingExpenses ? item.processingExpenses : 0;
        });
        return result;
      }
      return 0;
    },
  },
  mounted() {},
  methods: {
    getCustomerList() {
      // 写get请求
      this.$get("/customer/getCustomerList").then((res) => {
        this.customerList = res.obj;
      });
    },
    // 合并单元格
    arraySpanMethod({ rowIndex, columnIndex }) {
      const goodListlength = this.printDetails.goodsList.length;
          if (rowIndex  === goodListlength - 1) {
            if (columnIndex === 0) {
              return [1, 2];
            } else if (columnIndex === 1) {
              return [0, 0];
            }
          }
        },
    // 根据分页获取对应的商品列表
    async getOrderList() {
      await this.$post("/order/list", this.queryInfo).then((res) => {
        this.orderList = res.obj.data;
        let arr = [];
        for (let i = 0; i < this.orderList.length; i++) {
          let order = this.orderList[i];
          order.index = i;
          arr.push(order);
        }
        this.orderList = arr;
        this.total = res.obj.total;
      });
      this.$message.success("获取订单列表成功！");
    },
    selectData() {
      this.getOrderList();
    },
    updateOrder(data) {
      this.$post("/order/updateOrder", data).then(() => {
        this.$message.success("修改成功！");
        this.showOrderInfoDialog = false;
        this.getOrderList();
      });
    },
    buildTimeCondition() {
      this.queryInfo.startCreateTime = this.timeRangeCondition[0];
      this.queryInfo.endCreateTime = this.timeRangeCondition[1];
    },
    handleSizeChange(newSize) {
      this.queryInfo.size = newSize;
      this.getOrderList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.current = newPage;
      this.getOrderList();
    },
    async removeOrderById(id) {
      const confirmResult = await this.$confirm(
        "此操作将永久删除该订单, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);

      if (confirmResult !== "confirm") {
        return this.$message.info("已经取消删除！");
      }

      const { data: res } = await this.$delete(`/order/del/${id}`);

      if (res.meta.status !== 1) {
        return this.$message.error("删除失败！");
      }

      this.$message.success("删除成功！");
      this.getOrderList();
    },
    showDetailInfo(data) {
      this.$get("/order/detail", {
        params: {
          orderId: data.id,
        },
      }).then((res) => {
        // 弹框
        this.showOrderInfoDialog = true;
        this.orderDetails = res.obj;
      });
    },
    showPrintInfo(data) {
      this.$get("/order/detail", {
        params: {
          orderId: data.id,
        },
      }).then((res) => {
        // 弹框
        this.showPrintInfoDialog = true;
        this.printDetails = res.obj;
        if (this.printDetails.goodsList){
          this.printDetails.goodsList.push({
            goodsName: null,
            goodsWidth: null,
            goodsLength: null,
            total: this.goodsListTotalNumber,
            area: this.goodsListTotalArea,
            goodsPrice: null,
            processingRequirements: null,
            processingExpenses: this.goodsListProcessingExpenses,
            totalMoney: this.goodsListTotalMoney,
            remark: null,
            productionProcess: null,
          })
        }
      });
    },
    saveGoods(e, rowData, properties) {
      this.$forceUpdate();
      this.$nextTick(() => {
        this.orderDetails.goodsList.find((item) => item.fid === rowData.fid)[
          properties
        ] = e;
      });
    },
    handleDelete(index, data) {
      this.orderDetails.goodsList.splice(index, 1);
      let arr = this.orderDetails.delGoodsIdList
        ? this.orderDetails.delGoodsIdList
        : [];
      arr.push(data.id);
      this.orderDetails.delGoodsIdList = arr;
    },
    addRow(num) {
      let maxFid = Math.max.apply(
        Math,
        this.orderInfo.goodsList.map((item) => item.fid)
      );
      for (let i = 1; i <= num; i++) {
        maxFid++;
        // 拿到最大的fid
        this.orderDetails.goodsList.push({
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
        });
      }
    },
    printWindow() {
      this.$print(this.$refs.print);
    },
    processingIndex(index) {
      return index + 1 === this.printDetails.goodsList.length ? "合计:" : index + 1;
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../src/utils/print.css";
.orderInquiry {
  span {
    white-space: nowrap;
  }



  /deep/ .el-card__body {
    padding-top: 0 !important;
  }

  .searchModel {
    display: flex;
    align-items: center;
    padding-top: 20px;
    padding-bottom: 15px;
    margin-left: -20px !important;
    margin-right: -20px !important;
    font-size: 14px;
    background-color: #f2f2f2;

    .searchLet {
      display: flex;
      align-items: center;
      padding-right: 0 !important;

      .changeType {
        width: 30%;
        display: flex;
        align-items: center;

        span {
          margin-right: 2%;
        }
      }

      .timer {
        display: flex;
        align-items: center;
        width: 35%;
        margin-left: 2%;

        span {
          // width: 30%;
          margin-right: 2%;
        }

        /deep/ .el-range-separator,
        /deep/ .el-range-input {
          font-size: 12px;
        }
      }

      .isPay {
        display: flex;
        align-items: center;
        width: 22%;
        margin-left: 16px;
        margin-right: 10px;

        span {
          margin-right: 10px;
        }

        .el-select {
          width: 50%;
        }
      }
    }

    .searchRit_let {
      display: flex;
      align-items: center;
      width: 22%;
      margin-right: 16px;

      span {
        margin-right: 10px;
      }

      .el-select {
        width: 50%;
      }
    }
  }

  .searchRit {
    display: flex;
    // padding-left: 30px!important;

    .el-button {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 38px;
      padding: 1% 1%;
      font-size: 14px;
    }

    .el-button {
      background-color: #009688;
      color: #fff;
    }

    .el-button:nth-of-type(1) {
      background-color: #1e9fff;
      color: #fff;
    }
  }
}

.searchModel_Amount {
  margin-left: -20px !important;
  margin-right: -20px !important;
  padding-bottom: 15px;
  font-size: 16px;
  background-color: #f2f2f2;

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
      white-space: nowrap;
      margin-left: 15px;
      font-size: 20px;
      color: #009688;
    }
  }
}

.tableColumn {
  width: 92%;
  margin-top: 15px;

  /deep/ table {
    thead th {
      background-color: #f0f0f0;
      border-color: #dfdfdf;
      padding: 10px 0 !important;
      text-align: center;
    }

    tbody td {
      padding: 12px 0 !important;
    }

    tbody td:nth-of-type(1) {
      background-color: #f0f0f0;
      border-color: #dfdfdf;
    }

    .cell {
      text-align: center;
    }
  }

  /deep/ tbody {
    .el-table__row {
      .el-table__cell:nth-of-type(11),
      .el-table__cell:nth-of-type(12) {
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
          width: 80px !important;
          text-align: center;
          // background: red;
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
            background-color: #1e9fff;
          }

          .el-button:nth-of-type(2) {
            background-color: #009688;
          }

          .el-button:nth-of-type(3) {
            background-color: #ff5722;
          }
        }
      }
    }
  }
}

.pagination {
  border: 1px solid #ebeef5;
  margin-top: -1px;
  display: flex;
  justify-content: start;

  /deep/ .active {
    background-color: #009688 !important;
  }
}

/deep/ .el-dialog__header {
  text-align: left;
  padding: 9px 20px 10px;
  border-bottom: 1px solid #eee;
  background: #f8f8f8;

  .el-dialog__title {
    font-size: 14px;
  }
}

.orderDetail {
  left: 10.4%;
  top: -3.4%;

  /deep/ .el-dialog__body {
    padding-top: 0;

    .order_tit {
      font-size: 18px;
      margin: 20px 0;
      color: #333;
      font-family: Arial, Helvetica, sans-serif;
      font-weight: 550;
    }

    .el-col {
      padding: 0 !important;
    }

    .baseInfo {
      margin: 20px 20px !important;

      .grid-content {
        display: flex;
        align-items: center;

        > span {
          text-align: right;
          width: 30%;
          margin-right: 15px;
          font-size: 14px;
        }

        .el-input {
          width: 58%;
        }

        .el-select {
          width: 58%;

          .el-input {
            width: 100%;
          }
        }
      }

      .el-col-4 {
        .el-input,
        .el-select {
          width: 66%;
        }
      }
    }

    .el-table {
      thead th {
        background-color: #f0f0f0;
        border-color: #dfdfdf;
        padding: 0 !important;
        text-align: center;
      }

      tbody td {
        padding: 10px 0 !important;
      }

      tbody td:nth-of-type(1) {
        background-color: #f0f0f0;
        border-color: #dfdfdf;
      }

      .cell {
        text-align: center;
      }
    }

    .submit {
      display: flex;
      justify-content: space-between;
      margin-top: 10px;

      div {
        .el-button {
          margin: 0;
          background-color: #009688;
          color: #fff;
          border-radius: 2px;
          border-color: #009688;
          border-right-color: #fff;
        }
      }

      .el-button {
        margin-right: 20px;
        background-color: #ffb800;
        color: #fff;
        border-radius: 2px;
        border-color: #ffb800;
      }
    }
  }
}

.printInfo {
  // left: 10.4%;
  // top: -3.4%;



  /deep/ .el-dialog__body {
    .order_tit {
      font-size: 22px;
      margin: 20px 0;
      color: #333;
      font-family: Arial, Helvetica, sans-serif;
      font-weight: 550;
    }

    .no-print {
      background-color: #009688;
      color: #fff;
      border-radius: 2px;
    }

    .thead {
      margin-bottom: 25px;

      .el-row {
        margin: 0 !important;
      }

      .el-col {
        border: 1px solid #000;
        margin-left: -1px;
        text-align: left;
        height: 25px;
        line-height: 25px;
        color: #000;
      }

      .el-row:nth-of-type(2n) {
        .el-col {
          border-top: none !important;
        }
      }
    }

    .invoiceTable {
      border: 1px solid#000;

      .el-table__fixed {
        height: 100%;
      }
      thead th {
        background-color: #f0f0f0;
        border-color: #000;
        color: #000;
        padding: 2px 0 !important;
        text-align: center;
        font-size: 12px;
      }

      tbody td {
        padding: 2px 0 !important;
        font-size: 12px;
        border-color: #000;
        color: #000;
      }

      .cell {
        text-align: center;
      }
    }

    .sign {
      margin-top: 20px;
      .el-col {
        text-align: left;
        color: #000;
      }
    }
  }
}
</style>
