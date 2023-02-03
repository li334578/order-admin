<template>
  <div class="hello">

    客户:
    <el-select v-model="selectCustomerId" @change="fillCustomerInfo()" filterable placeholder="请选择">
      <el-option
          v-for="customer in customerList"
          :key="customer.id"
          :label="customer.customerName +'【'+ customer.customerPhone+'】'"
          :value="customer.id">
      </el-option>
    </el-select>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">客户
          <el-input label="" v-model="orderInfo.customerName"></el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">联系电话
          <el-input v-model="orderInfo.customerPhone"></el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">地址
          <el-input v-model="orderInfo.customerAddress"></el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">制单人
          <el-input v-model="orderInfo.createUserName"></el-input>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          订单日期
          <el-date-picker
              v-model="orderInfo.createTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          金额
          <el-input v-model="orderInfo.money"></el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">已收款
          <el-input v-model="orderInfo.receiveMoney"></el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          总面积
          <el-input v-model="orderInfo.totalArea"></el-input>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          备注
          <el-input v-model="orderInfo.remark"></el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple">
          是否已付款
          <el-select v-model="orderInfo.payStatus" placeholder="请选择">
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
          <el-select v-model="orderInfo.sendStatus" placeholder="请选择">
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

    <br/>
    <el-table
        :data="orderInfo.goodsList"
        border stripe
        style="width: 100%"
        row-key="fid"
        >
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
          <el-input-number :value="scope.row.goodsWidth" @input="saveGoods($event,scope.row,'goodsWidth')"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="goodsLength"
          label="长度 mm"
          width="201">
        <template v-slot="scope">
          <el-input-number :value="scope.row.goodsLength" @input="saveGoods($event,scope.row,'goodsLength')"/>
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
          <el-input-number :value="scope.row.area" @input="saveGoods($event,scope.row,'area')"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="goodsPrice"
          label="单价"
          width="201">
        <template v-slot="scope">
          <el-input-number :value="scope.row.goodsPrice" @input="saveGoods($event,scope.row,'goodsPrice')"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="processingRequirements"
          label="加工需求"
          width="220">
        <template v-slot="scope">
          <el-input :value="scope.row.processingRequirements"
                    @input="saveGoods($event,scope.row,'processingRequirements')"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="processingExpenses"
          label="加工费用"
          width="201">
        <template v-slot="scope">
          <el-input-number :value="scope.row.processingExpenses"
                           @input="saveGoods($event,scope.row,'processingExpenses')"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="totalMoney"
          label="总金额"
          width="201">
        <template v-slot="scope">
          <el-input-number :value="scope.row.totalMoney" @input="saveGoods($event,scope.row,'totalMoney')"/>
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
    <el-button primary @click="addRow(1)">加一行</el-button>
    <el-button primary @click="addRow(3)">加三行</el-button>
    <el-button primary @click="addRow(10)">加十行</el-button>
    <el-button primary @click="addRow(50)">加五十行</el-button>
    <el-button primary @click="addOrder()">提交订单</el-button>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
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
        sendStatus: null,
        goodsList: []
      },
      options1: [
        {
          value: 1,
          label: '已付款'
        },
        {
          value: 0,
          label: '未付款'
        }
      ],
      options2: [
        {
          value: 1,
          label: '已发货'
        },
        {
          value: 0,
          label: '未发货'
        }
      ],
    };
  },
  props: {
    msg: String
  },
  created() {
    this.getCustomerList()
    for (let i = 0; i < 4; i++) {
      this.orderInfo.goodsList.push(
          {
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
          })
    }
  },
  methods: {
    getCustomerList() {
      // 写get请求
      this.$get('/customer/getCustomerList').then((res) => {
        this.customerList = res.obj
      })
    },
    fillCustomerInfo() {
      const selectCustomerArray = this.customerList.filter(item => item.id === this.selectCustomerId);
      const customer = selectCustomerArray[0];
      this.orderInfo.customerId = customer.id;
      this.orderInfo.customerName = customer.customerName;
      this.orderInfo.customerPhone = customer.customerPhone;
      this.orderInfo.customerAddress = customer.customerAddress;
    },
    addOrder() {
      // 过滤掉产品名为空的数据
      this.orderInfo.goodsList = this.orderInfo.goodsList.filter(item => item.goodsName !== null)
      console.log(this.orderInfo)
      this.$post('/order/add', this.orderInfo)
    },
    saveGoods(e, rowData, properties) {
      this.orderInfo.goodsList[rowData.fid][properties] = e
      this.$forceUpdate()
    },
    addRow(num) {
      let maxFid = Math.max.apply(Math, this.orderInfo.goodsList.map(item => item.fid))
      for (let i = 1; i <= num; i++) {
        maxFid++
        // 拿到最大的fid
        this.orderInfo.goodsList.push(
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
    handleDelete(index) {
      this.orderInfo.goodsList.splice(index, 1)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
