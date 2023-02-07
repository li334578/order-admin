<template>
  <div class="hello">
    <div class="baseInfoForm">
      <el-divider content-position="left">
        <div class="selectionType">
          <span>客户</span>
          <el-select
            v-model="selectCustomerId"
            @change="fillCustomerInfo()"
            filterable
            placeholder="请选择"
          >
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
      </el-divider>

      <el-row :gutter="60" class="baseInfo">
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>客户</span>
            <el-input label="" v-model="orderInfo.customerName"></el-input>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>联系电话</span>
            <el-input v-model="orderInfo.customerPhone"></el-input>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>地址</span>
            <el-input v-model="orderInfo.customerAddress"></el-input>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>制单人</span>
            <el-input v-model="orderInfo.createUserName"></el-input>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content bg-purple">
            <span>订单日期</span>
            <el-date-picker
              v-model="orderInfo.createTime"
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
            <el-input v-model="orderInfo.money"></el-input>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>已收款</span>
            <el-input v-model="orderInfo.receiveMoney"></el-input>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>总面积</span>

            <el-input v-model="orderInfo.totalArea"></el-input>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>备注</span>
            <el-input v-model="orderInfo.remark"></el-input>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="grid-content bg-purple">
            <span>是否已付款</span>
            <el-select v-model="orderInfo.payStatus" placeholder="请选择">
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
      </el-row>
      <el-row :gutter="60" class="baseInfo">
        <el-col :span="5">
          <div class="grid-content bg-purple">
            <span>发货状态</span>
            <el-select v-model="orderInfo.sendStatus" placeholder="请选择">
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
    </div>

    <div class="submissionList">
      <el-divider content-position="left">
        <div class="selectionType">
          <span>货物清单</span>
        </div>
      </el-divider>
      <el-table
        :data="orderInfo.goodsList"
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

        <el-table-column prop="goodsWidth" sortable label="宽度 mm" width="160">
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
        <el-table-column prop="processingExpenses" label="加工费用" width="160">
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
        <el-table-column prop="productionProcess" label="生产流程" width="220">
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
              size="small"
              type="danger"
              @click="handleDelete(scope.$index)"
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
        <el-button  @click="addOrder()">提交订单</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderEntry",
  data() {
    return {
      customerList: [],
      selectCustomerId: null,
      orderInfo: {
        customerId: null,
        customerName: null,
        customerPhone: null,
        customerAddress: null,
        createUserName: null,
        createTime: null,
        money: null,
        receiveMoney: null,
        totalArea: null,
        remark: null,
        payStatus: null,
        num: 0,
        sendStatus: null,
        goodsList: [],
      },
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
    };
  },
  props: {
    msg: String,
  },
  created() {
    this.getCustomerList();
    for (let i = 0; i < 4; i++) {
      this.orderInfo.goodsList.push({
        fid: i,
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
  methods: {
    getCustomerList() {
      // 写get请求
      this.$get("/customer/getCustomerList").then((res) => {
        this.customerList = res.obj;
      });
    },
    fillCustomerInfo() {
      const selectCustomerArray = this.customerList.filter(
        (item) => item.id === this.selectCustomerId
      );
      const customer = selectCustomerArray[0];
      this.orderInfo.customerId = customer.id;
      this.orderInfo.customerName = customer.customerName;
      this.orderInfo.customerPhone = customer.customerPhone;
      this.orderInfo.customerAddress = customer.customerAddress;
    },
    addOrder() {
      // 过滤掉产品名为空的数据
      this.orderInfo.goodsList = this.orderInfo.goodsList.filter(
        (item) => item.goodsName !== null
      );
      this.$post("/order/add", this.orderInfo);
    },
    saveGoods(e, rowData, properties) {
      this.$forceUpdate();
      this.$nextTick(() => {
        this.orderInfo.goodsList.find((item) => item.fid === rowData.fid)[
          properties
        ] = e;
      });
    },
    addRow(num) {
      let maxFid = Math.max.apply(
        Math,
        this.orderInfo.goodsList.map((item) => item.fid)
      );
      for (let i = 1; i <= num; i++) {
        maxFid++;
        // 拿到最大的fid
        this.orderInfo.goodsList.push({
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
    handleDelete(index) {
      this.orderInfo.goodsList.splice(index, 1);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="less" scoped>
.hello {
  background-color: #fff;
  padding: 20px;

  .el-divider {
    margin-bottom: 40px;

    .selectionType {
      span {
        // margin: 0 10px;
        margin-right: 10px;
        font-size: 22px;
      }
    }
  }

  .baseInfoForm {
    .el-divider {
      .el-select {
        width: 400px;
      }
    }

    .el-col {
      padding: 0 !important;
    }
    .baseInfo {
      margin: 20px 30px !important;
      .grid-content {
        display: flex;
        align-items: center;
        > span {
          text-align: right;
          width: 30%;
          margin-right: 15px;
          font-size: 14px;
        }
        .el-input,
        .el-select {
          width: 58%;
        }
      }

      .el-col-4 {
        .el-input,
        .el-select {
          width: 66%;
        }
      }
    }
  }

  .submissionList {
    margin-top: 80px;

    /deep/ table {
      thead th {
        background-color: #F0F0F0;
        border-color: #DFDFDF;
        padding: 0 !important;
        text-align: center;
      }

      tbody td {
        padding: 10px 0 !important;
      }

      tbody td:nth-of-type(1) {
        background-color: #F0F0F0;
        border-color: #DFDFDF;
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
          background-color: #009688 ;
          color: #fff;
          border-radius: 2px;
          border-color: #009688;
          border-right-color: #fff ;
        }
      }

      .el-button {
          margin-right: 20px;
          background-color: #FFB800 ;
          color: #fff;
          border-radius: 2px;
          border-color: #FFB800;
        }
    }
  }
}
</style>
