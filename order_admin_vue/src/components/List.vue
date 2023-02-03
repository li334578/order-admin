<template>
  <div class="orderInquiry">
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="24" class="searchModel">
        <el-col :xs="24" :sm="24" :md="24" :lg="14" class="searchLet">
          <div class="changeType">
            <el-select v-model="value" placeholder="">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>

          <div class="timer">
            <span>订单日期</span>
            <el-date-picker
              v-model="value1"
              type="daterange"
              range-separator="至"
              start-placeholder=""
              end-placeholder=""
            >
            </el-date-picker>
          </div>

          <div class="isPay">
            <span>是否已付款</span>
            <el-select v-model="value" placeholder="">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="searchRit_let">
            <span>发货状态</span>
            <el-select v-model="value" placeholder="">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="10" class="searchRit">
          <el-button>查询</el-button>
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
          <el-button>品未生产产品</el-button>
          <div>金额:5251.278,总面积:48.613</div>
        </el-col>
      </el-row>
      <!-- table表格区域 -->
      <el-table :data="goodslist" border stripe class="tableColumn">
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column label="客户" prop="goods_name"></el-table-column>
        <el-table-column label="制单人" prop="goods_name"></el-table-column>
        <el-table-column label="订单日期" prop="goods_name"></el-table-column>
        <el-table-column label="金额" prop="goods_price"></el-table-column>
        <el-table-column label="已收款" prop="goods_price"></el-table-column>
        <el-table-column label="总面积" prop="goods_price"></el-table-column>
        <el-table-column label="备注" prop="goods_price"></el-table-column>
        <el-table-column label="订单号" prop="goods_price"></el-table-column>
        <el-table-column  width="100px" label="是否已付款" prop="goods_weight">
          <template slot-scope="scope">
            <el-switch
              v-model="value"
              width="80px"
              active-text="已付款"
              inactive-text="未付款"
              @click="removeById(scope.row.goods_id)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column width="100px" label="发货状态" prop="goods_weight">
          <template slot-scope="scope">
            <el-switch
              v-model="value"
              width="80px"
              active-text="已发货"
              inactive-text="未发货"
              @click="removeById(scope.row.goods_id)"
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
          <template slot-scope="scope">
            <el-button type="primary" size="mini">查看/修改</el-button>
            <el-button size="mini">打印发布单</el-button>
            <el-button
              type="danger"
              size="mini"
              @click="removeById(scope.row.goods_id)"
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
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 查询参数对象
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 10,
      },
      options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      value: "",
      value1: "",
      // 商品列表
      goodslist: [],
      // 总数据条数
      total: 0,
    };
  },
  created() {
    this.getGoodsList();
  },
  mounted() {},
  methods: {
    // 根据分页获取对应的商品列表
    async getGoodsList() {
      const { data: res } = await this.$get("goods", {
        params: this.queryInfo,
      });

      // if (res?.meta.status !== 200) {
      //   return this.$message.error('获取商品列表失败！')
      // }

      this.$message.success("获取商品列表成功！");
      this.goodslist = res?.data.goods;
      let array1 = [];
      for (let index = 0; index < 6; index++) {
        array1.push({
          index: index,
        });
      }
      console.log(array1, "kmsfdsfds");
      this.goodslist = array1;
      this.total = res.data.total;
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getGoodsList();
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getGoodsList();
    },
    async removeById(id) {
      const confirmResult = await this.$confirm(
        "此操作将永久删除该商品, 是否继续?",
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

      const { data: res } = await this.$getete(`goods/${id}`);

      if (res.meta.status !== 200) {
        return this.$message.error("删除失败！");
      }

      this.$message.success("删除成功！");
      this.getGoodsList();
    },
    goAddpage() {
      this.$router.push("/goods/add");
    },
  },
};
</script>

<style lang="less" scoped>
.orderInquiry {
  span {
    white-space: nowrap;
  }
  /deep/ .el-card__body {
    padding-top: 0 !important;
  }
  .searchModel {
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

    /deep/ tbody {
       .el-table__row {
        .el-table__cell:nth-of-type(11),
        .el-table__cell:nth-of-type(12){
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
}
</style>
