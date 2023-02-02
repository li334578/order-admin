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
    <br/>
    客户
    <el-input v-model="orderInfo.customerName"></el-input>
    联系电话
    <el-input v-model="orderInfo.customerPhone"></el-input>
    地址
    <el-input v-model="orderInfo.customerAddress"></el-input>
    制单人
    <el-input v-model="orderInfo.createUserName"></el-input>
    订单日期
    <el-date-picker
        v-model="orderInfo.createTime"
        type="date"
        placeholder="选择日期">
    </el-date-picker>
    <br/>
    金额
    <el-input v-model="orderInfo.money"></el-input>
    已收款
    <el-input v-model="orderInfo.receiveMoney"></el-input>
    总面积
    <el-input v-model="orderInfo.totalArea"></el-input>
    备注
    <el-input v-model="orderInfo.remark"></el-input>
    是否已付款
    <el-select v-model="orderInfo.payStatus" placeholder="请选择">
      <el-option
          v-for="item in options1"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    发货状态
    <el-select v-model="orderInfo.sendStatus" placeholder="请选择">
      <el-option
          v-for="item in options2"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <br/>
    <el-button primary @click="addOrder()">新增</el-button>
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
      ]
    };
  },
  props: {
    msg: String
  },
<<<<<<< HEAD
  mounted () {
    console.log(this,"ksfldsfdsfds");
=======
  created() {
    this.getCustomerList()
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
      console.log(this.orderInfo)
      // this.$post('/order/add',this.orderInfo)
    }
>>>>>>> 21ee903bac056a61ea2770df1505e9800e5e89f5
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
